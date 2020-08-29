package cr.ac.ucr.turistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_go_to_login:
                goToLogin();
                break;
            case R.id.btn_go_to_register_one:
                goToRegister();
                break;
            case R.id.btn_guest:
                break;
            default:
                break;
        }
    }

    private void goToLogin() {
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegister() {
        Intent intent= new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

}