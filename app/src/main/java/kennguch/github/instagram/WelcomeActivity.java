package kennguch.github.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity {

    Button mLogin, mRegister;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializer();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });

    }

    private void initializer() {
        mLogin = findViewById(R.id.btn_login_welcome);
        mRegister = findViewById(R.id.btn_register_welcome);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    }
}
