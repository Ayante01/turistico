/**
 * Register Activity
 *Esta clase sera utilizada para el usuario pueda realizar un Registro de usario
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.turistico.models.User;

public class RegisterActivity extends AppCompatActivity implements OnClickListener{

    /**
     * Variables
     */
    private EditText edName;
    private EditText edLastName;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConfirmPassword;

    //variables para agregar datos a la base de datos
    String name;
    String lastName;
    String email;
    String password;
    String confirmPassword;
    ArrayList<String> dbEmail;

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
        setContentView(R.layout.activity_register);

        aAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("users");
        dbEmail = new ArrayList<>();

        edName = findViewById(R.id.ed_name);
        edLastName = findViewById(R.id.ed_last_name);
        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword = findViewById(R.id.ed_confirm_pass);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String mail = ds.child("email").getValue(String.class);
                    dbEmail.add(mail);
                    Log.d("TAG ", mail);
                }

                Log.d("DataSnapshot: ", String.valueOf(dbEmail));
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
        name = edName.getText().toString().trim();
        lastName = edLastName.getText().toString().trim();
        email = edEmail.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        confirmPassword = edConfirmPassword.getText().toString().trim();

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
        if(!confirmPassword.equalsIgnoreCase(password)){
            edPassword.setError(getString(R.string.no_match));
            return;
        }

        if(isUsed()){
            Toast.makeText(this, R.string.no_register, Toast.LENGTH_SHORT).show();
            return;
        }
        registerUser();
    }

    private void registerUser() {
        User user = new User();
        user.setNombre(name);
        user.setApellido(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setImgPerfil("src/main/res/drawable-v24/user.png");

        myRef.child(name).setValue(user);
        Toast.makeText(this, R.string.siggned_in, Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isUsed() {
        for(String email: dbEmail)
        {
            if(this.email.equals(email)){
                return true;
            }
        }
        return false;
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
