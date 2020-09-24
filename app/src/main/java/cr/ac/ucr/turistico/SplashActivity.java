/**
 * Register Activity
 *Esta clase se encargara de mostrar un splash al iniciar la aplicación
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */package cr.ac.ucr.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cr.ac.ucr.turistico.utils.AppPreferences;

public class SplashActivity extends AppCompatActivity {
    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     * Ademas en este caso comprobara por medio de AppPreferences si en el dispositivo se encuentra ya un usuario logueado
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean isLoggedIn = AppPreferences.getInstance(this).getBoolean(AppPreferences.Keys.IS_LOGGED_IN,false);
        Intent intent;
        if(isLoggedIn){
            //intent = new Intent(this, MainActivity.class);
            intent = new Intent(this, MainActivity.class);
        } else {
            //intent = new Intent(this, SelectActivity.class);
            intent = new Intent(this, SelectActivity.class);
        }
        startActivity(intent);
        // se utiliza solo si no se quiere volver a esta activity
        finish();
    }
}