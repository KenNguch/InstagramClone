package kennguch.github.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mReference;

    ProgressDialog mDialog;

    EditText mUsername, mEmail, mPassword;
    Button mRegister;
    TextView mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        init();

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (!username.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!password.isEmpty()) {
                            if (password.length() >= 6) {
                                register(username, email, password);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,
                                        "Minimum password length should be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            mPassword.setError("Password cannot be empty");
                            mPassword.requestFocus();
                        }
                    } else {
                        mDialog.dismiss();
                        mEmail.setError("Email cannot be empty");
                        mEmail.requestFocus();
                    }
                } else {
                    mDialog.dismiss();
                    mUsername.setError("Username cannot be empty");
                    mUsername.requestFocus();
                }
            }
        });
    }

    private void register(final String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            assert mUser != null;
                            String userId = mUser.getUid();

                            mReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("userId", userId);
                            hashMap.put("username", username);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/instaclone-3a90f.appspot.com/o/account.png?alt=media&token=5daa3292-66b8-4ab7-a82d-8e414ed20459");

                            mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,
                                    Objects.requireNonNull(task.getException()).toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {
        mUsername = findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mRegister = findViewById(R.id.btnReg);
        mMember = findViewById(R.id.txt_member);
    }
}
