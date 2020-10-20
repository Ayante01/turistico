/**
 * Profile Fragment
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import cr.ac.ucr.turistico.LoginActivity;
import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.utils.AppPreferences;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private AppCompatActivity activity;
    private Button btnMedals;
    private Button btnSettings;
    private Button btnLogout;
    private ScrollView scrollViewMedals;
    private ScrollView scrollViewSettings;

    FirebaseAuth aAuth;
    /**
     * Constructor
     */
    public ProfileFragment() {
        // Required empty public constructor
    }
    /**
     *ProfileFragment newInstance
     * @return fragment
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }
    /**
     * Metodo onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnMedals = view.findViewById(R.id.btn_medals);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnLogout = view.findViewById(R.id.btn_logout);
        scrollViewMedals = view.findViewById(R.id.sv_medals);
        scrollViewSettings = view.findViewById(R.id.sv_settings);
        scrollViewSettings.setVisibility(View.GONE);
        aAuth = FirebaseAuth.getInstance();

        //Listener
        btnMedals.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        return view;
    }
    /**
     * Metodo onAttach
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }
    /**
     * Metodo onDetach
     */
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
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
            case R.id.btn_medals:
                changeToMedals();
                break;
            case R.id.btn_settings:
                changeToSettings();
                break;
            case R.id.btn_logout:
                logout();
                break;
            default:
                break;
        }
    }
    /**
     * Metodo changeToSettings
     * Cambia el color de fondo del boton medals o settings y el color de la tipografia
     */
    private void changeToSettings() {
        btnMedals.setBackground(activity.getDrawable(R.drawable.account_button_gray));
        btnMedals.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
        btnSettings.setBackground(activity.getDrawable(R.drawable.account_button_white));
        btnSettings.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.purple));
        scrollViewMedals.setVisibility(View.GONE);
        scrollViewSettings.setVisibility(View.VISIBLE);
    }
    /**
     * Metodo changeToMedals
     * Cambia el color de fondo del boton medals o settings y el color de la tipografia
     */
    private void changeToMedals() {
        btnMedals.setBackground(activity.getDrawable(R.drawable.account_button_white));
        btnMedals.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.purple));
        btnSettings.setBackground(activity.getDrawable(R.drawable.account_button_gray));
        btnSettings.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
        scrollViewMedals.setVisibility(View.VISIBLE);
        scrollViewSettings.setVisibility(View.GONE);
    }
    /**
     * Metodo Logout
     * Cierra la sesion del usuario que se encuentre activo y redirige la aplicacion al Login Activity
     */
    private void logout() {
        aAuth.signOut();
        AppPreferences.getInstance(activity).clear();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }
}