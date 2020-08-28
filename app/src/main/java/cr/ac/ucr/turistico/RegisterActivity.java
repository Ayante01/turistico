package cr.ac.ucr.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements OnClickListener{
    private EditText edName;
    private EditText edLastName;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.ed_name);
        edLastName = findViewById(R.id.ed_last_name);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword = findViewById(R.id.ed_confirm_pass);

    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sign_up:
                signUp();
                break;
            case R.id.btn_login:
                goToLogin();
                break;

            default:
                break;
        }
    }

    private void signUp() {
        String name = edName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();

        if(name.isEmpty()){
            edName.setError(getString(R.string.error_name));
            return;
        }

        if(lastName.isEmpty()){
            edLastName.setError(getString(R.string.error_lastname));
            return;
        }

        if(email.isEmpty()){
            edEmail.setError(getString(R.string.error_email));
            return;
        }

        if(password.isEmpty()){
            edPassword.setError(getString(R.string.error_password));
            return;
        }

        if(confirmPassword.isEmpty()){
            edPassword.setError(getString(R.string.error_confirm_pass));
            return;
        }

        //confirmar que la contraseña y confirmar contraseña son iguales//
        if(confirmPassword.equalsIgnoreCase(password)){

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLogin() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
