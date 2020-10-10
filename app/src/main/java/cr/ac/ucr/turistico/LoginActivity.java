/**
 * Login Activity
 *Esta clase sera utilizada para el usuario pueda realizar un Login
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.turistico.utils.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * Variables
     */
    private EditText edEmail;
    private EditText edPassword;
    ArrayList<String> dbEmails;
    ArrayList<String> dbPasswords;
    int position = 1;

    /**
     * inicializacion de la base de datos
     */

    FirebaseAuth aAuth;
    FirebaseDatabase fbDatabase;
    DatabaseReference myRef;

    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("users");
        dbEmails = new ArrayList<>();
        dbPasswords = new ArrayList<>();

         edEmail = findViewById(R.id.ed_email);
         edPassword = findViewById(R.id.ed_password);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String mail = ds.child("email").getValue(String.class);
                    String password = ds.child("password").getValue(String.class);
                    dbEmails.add(mail);
                    dbPasswords.add(password);
                    Log.d("TAG ", mail +" "+ password);
                }

                Log.d("DataSnapshot: ", String.valueOf(dbEmails));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
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

    /**
     * Metodo login
     * En este metodo seran realizadas las validaciones necesarias para que el usuario pueda logear
     * Se encuentra incompleto debido a que aun no hay acceso a la base de datos
     */
    private void login() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        boolean userMailMatch = false;
        boolean userPassMatch = false;

        if(email.isEmpty()){
            edEmail.setError(getString(R.string.error_email));
            return;
        }
        if(password.isEmpty()){
            edPassword.setError(getString(R.string.error_password));
            return;
        }

        for(String mail: dbEmails){
            if(email.equals(mail)) {
                userMailMatch = true;
                position = dbEmails.indexOf(email);
                Log.i(" Nada ", " "+position);
            }
        }

        if(dbPasswords.get(position).equals(password)){
            userPassMatch = true;
        }

        if(userMailMatch && userPassMatch){
            AppPreferences.getInstance(this).put(AppPreferences.Keys.IS_LOGGED_IN, true);

            Toast.makeText(this, R.string.logged_in, Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo goToRegister
     * Se encargara de finalizar este Activity y redirigir la acplicacion al Activity Register
     */
    private void goToRegister() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}