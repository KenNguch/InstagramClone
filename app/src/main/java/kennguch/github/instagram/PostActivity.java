package kennguch.github.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PostActivity extends AppCompatActivity {


    private static int PICTURE_RESULT = 100;
    EditText mPostDescription;
    Button mPost;
    ImageView mImage;

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
            Glide.with(this).load(mUri).into(mImage);
        }
    }

    private void initializer() {
        mPostDescription = findViewById(R.id.post_description);

        mPost = findViewById(R.id.btn_create_post);
        mImage = findViewById(R.id.post_image);


    }

}