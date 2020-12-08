/**
 * Register Activity
 *Esta clase sera utilizada para el Redirigir la aplicacion al Login o al Registro de usuarios dependiendo
 * de cual boton sea seleccioado
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

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
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
            case R.id.btn_go_to_login:
                goToLogin();
                finish();
                break;
            case R.id.btn_go_to_register_one:
                goToRegister();
                finish();
                break;
            case R.id.btn_guest:
                break;
            default:
                break;
        }
    }
    /**
     * Metodo goToLogin
     * Se encargara de finalizar este Activity y redirigir la acplicacion al Activity Login
     */
    private void goToLogin() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /**
     * Metodo goToRegister
     * Se encargara de finalizar este Activity y redirigir la acplicacion al Activity Register
     */
    private void goToRegister() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}