package kennguch.github.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    EditText mPostDescription;
    Button mPost;
    ImageView mImage;
    ProgressDialog mDialog;
    private String Url;
    FirebaseUser mUser;
    DatabaseReference mDBReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initializer();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mPostDescription.getText().toString();
                if (!description.isEmpty()) {
                    dopost(description, Url);
                } else {
                    mPostDescription.setError("Description Is Required");
                }
            }
        });

    }

    private void dopost(String description, String url) {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Posting");
        mDialog.setMessage("Loading....");
        mDialog.show();

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        String userId = mUser.getUid();
        if (mUser != null) {
            mDBReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(userId);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("UserId", userId);
            hashMap.put("Description", description);
            hashMap.put("Imageurl", url);

            mDBReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Check Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            mDialog.dismiss();
            Toast.makeText(this, "Failed To Create A Post", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Insert Image"), PICTURE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK && data != null) {

            Uri mUri = data.getData();

            assert mUri != null;

            mDialog = new ProgressDialog(this);
            mDialog.setTitle("Uploading");
            mDialog.setMessage("Please Wait ....");
            mDialog.show();

            final StorageReference reference = FirebaseStorage.getInstance().getReference().child("Post").child(Objects.requireNonNull(mUri.getLastPathSegment()));

            reference.putFile(mUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    Url = uri.toString();
                                    showImage(Url);
                                    Toast.makeText(PostActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Error : Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showImage(String url) {

        if (url != null) {
            Glide.with(this).load(url).into(mImage);
        }
    }

    private void initializer() {
        mPostDescription = findViewById(R.id.post_description);
        mPost = findViewById(R.id.btn_create_post);
        mImage = findViewById(R.id.post_image);
    }

}