/**
 * Register Activity
 *Esta clase sera utilizada para el usuario pueda realizar un Registro de usario
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements OnClickListener{
    /**
     * Variables
     */
    private EditText edName;
    private EditText edLastName;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConfirmPassword;
    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
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
    /**
     * Metodo onClick
     * Este metodo sera utilizado para escuchar a los botones creados en el XML y realizar acciones dependiendo
     * de cual boton sea seleccionado
     * @param view
     */
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sign_up:
                signUp();
                break;
            case R.id.btn_go_to_register_two:
                goToLogin();
                break;

            default:
                break;
        }
    }
    /**
     * Metodo signUp
     * Este metodo se encargara de guardar en la base de datos los usuarios que vayan siendo creados
     * Aun no encuentra incompleto debido a que no hay acceso a la base de datos
     */
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
            edLastName.setError(getString(R.string.error_last_name));
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
        if(confirmPassword.equalsIgnoreCase(password)){

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Metodo goToLogin
     * Se encargara de finalizar este Activity y redirigir la acplicacion al Activity Login
     */
    private void goToLogin() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
