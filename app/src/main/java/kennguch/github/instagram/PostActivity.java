package kennguch.github.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

    private static int PICTURE_RESULT = 100;
    ProgressDialog mDialog;

    FirebaseUser mUser;
    DatabaseReference mRef, userRef;

    EditText mDescription;
    Button mCreatePost;
    ImageView mImage;
    private String mUrl, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mDialog = new ProgressDialog(this);

        userRef = FirebaseDatabase.getInstance().getReference("users");

        username = userRef.child("username").toString();

        init();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        mCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mDescription.getText().toString();

                if (!description.isEmpty() || !description.equals("")) {
                    doPost(description, mUrl);
                } else {
                    Toast.makeText(PostActivity.this, "Description is required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doPost(String description, String url) {
        mDialog.setTitle("Creating post");
        mDialog.setMessage("Please wait");
        mDialog.show();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();


        if (mUser != null) {
            mRef = FirebaseDatabase.getInstance().getReference().child("posts").child(userId);
            String postId = mRef.push().getKey();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("postId", postId);
            hashMap.put("userId", userId);
            hashMap.put("description", description);
            hashMap.put("imageUrl", url);
            hashMap.put("username", username);

            mRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PostActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Check you internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mDialog.dismiss();
            Toast.makeText(this, "Unable to make a post", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, LoginActivity.class));
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Insert Picture"), PICTURE_RESULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK && data != null) {
            Uri mUri = data.getData();
            assert mUri != null;

            mDialog.setTitle("Uploading");
            mDialog.setMessage("Please wait...");
            mDialog.show();

            final StorageReference reference = FirebaseStorage
                    .getInstance().getReference()
                    .child("posts")
                    .child(Objects.requireNonNull(mUri.getLastPathSegment()));

            reference.putFile(mUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    mUrl = uri.toString();
                                    showImage(mUrl);
                                    Toast.makeText(PostActivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Error. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void showImage(String url) {
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(mImage);
        }
    }

    private void init() {
        mDescription = findViewById(R.id.post_description);
        mCreatePost = findViewById(R.id.btn_create_post);
        mImage = findViewById(R.id.post_image);
    }
}
