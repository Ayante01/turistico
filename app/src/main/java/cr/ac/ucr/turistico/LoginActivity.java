package cr.ac.ucr.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.turistico.utils.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edEmail;
    private EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         edEmail = findViewById(R.id.ed_email);
         edPassword = findViewById(R.id.ed_password);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_go_to_register_two:
                goToRegister();
                break;
            case R.id.btn_forgot_password:
                break;
            default:
                break;
        }
    }

    private void login() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if(email.isEmpty()){
            edEmail.setError(getString(R.string.error_email));
            return;
        }
        if(password.isEmpty()){
            edPassword.setError(getString(R.string.error_password));
            return;
        }
        if(email.equalsIgnoreCase("test@gmail.com") && "123".equalsIgnoreCase(password)){


            AppPreferences.getInstance(this).put(AppPreferences.Keys.IS_LOGGED_IN, true);

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    private void goToRegister() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}