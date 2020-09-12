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
import androidx.fragment.app.Fragment;

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

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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

        //Listener
        btnMedals.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

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

    private void changeToSettings() {
        btnMedals.setBackground(activity.getDrawable(R.drawable.account_button_gray));
        btnSettings.setBackground(activity.getDrawable(R.drawable.account_button_white));
        scrollViewMedals.setVisibility(View.GONE);
        scrollViewSettings.setVisibility(View.VISIBLE);
    }

    private void changeToMedals() {
        btnMedals.setBackground(activity.getDrawable(R.drawable.account_button_white));
        btnSettings.setBackground(activity.getDrawable(R.drawable.account_button_gray));
        scrollViewMedals.setVisibility(View.VISIBLE);
        scrollViewSettings.setVisibility(View.GONE);
    }

    private void logout() {
        AppPreferences.getInstance(activity).clear();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }
}