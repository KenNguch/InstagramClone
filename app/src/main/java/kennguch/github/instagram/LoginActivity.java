package kennguch.github.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mRef;

    ProgressDialog mDialog;

    EditText mEmail, mPassword;
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(LoginActivity.this);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mLogin = findViewById(R.id.btnLogin);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setMessage("Please wait...");
                mDialog.show();

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {
                        doLogin(email, password);
                    } else {
                        mPassword.setError("Password cannot be empty");
                        mPassword.requestFocus();
                    }
                } else {
                    mEmail.setError("Email cannot be empty");
                    mEmail.requestFocus();
                }
            }
        });
    }

    private void doLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
