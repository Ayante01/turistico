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
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tooltip.TooltipDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tooltip.Tooltip;

import java.util.ArrayList;

import cr.ac.ucr.turistico.EditProfile;
import cr.ac.ucr.turistico.FullImageActivity;
import cr.ac.ucr.turistico.LoginActivity;
import cr.ac.ucr.turistico.PlaceActivity;
import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.utils.AppPreferences;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private AppCompatActivity activity;
    private Button btnMedals;
    private Button btnSettings;
    private Button btnLogout;
    private Button btnEdit;
    private ScrollView scrollViewMedals;
    private ScrollView scrollViewSettings;
    private Button btnEditProfile;

    private CircleImageView profileImageView;
    private DatabaseReference databaseReference;


    private int counter = 0;

    ArrayList<String> dbUsers;
    ArrayList<String> dbName;
    ArrayList<String> dbLastName;
    ArrayList<String> dbImg;

    DatabaseReference myRef;
    FirebaseDatabase fbDatabase;
    FirebaseAuth aAuth;
    FirebaseUser user;

    TextView userName;
    String userID = "";

    private ProfileFragment context;

    private int position = 0;
    private Button btnBronzeWaterfall;
    private Button btnSilverWaterfall;
    private Button btnGoldWaterfall;
    private Button btnBronzeHills;
    private Button btnSilverHills;
    private Button btnGoldHills;
    private Button btnBronzeBeach;
    private Button btnSilverBeach;
    private Button btnGoldBeach;
    private Tooltip tooltip;
    private Button btn;

    /**
     * Constructor
     */
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * ProfileFragment newInstance
     *
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        context = this;

        dbUsers = new ArrayList<>();
        dbName = new ArrayList<>();
        dbLastName = new ArrayList<>();
        dbImg = new ArrayList<>();


    }

    /**
     * Metodo onCreateView
     *
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
        btnEdit = view.findViewById(R.id.btn_edit_profile);

        userName = view.findViewById(R.id.tv_user_name);
        profileImageView = view.findViewById(R.id.profile_image);

        btnBronzeWaterfall = view.findViewById(R.id.btn_waterfall_bronze);
        btnSilverWaterfall = view.findViewById(R.id.btn_waterfall_silver);
        btnGoldWaterfall = view.findViewById(R.id.btn_waterfall_gold);
        btnBronzeHills = view.findViewById(R.id.btn_hills_bronze);
        btnSilverHills = view.findViewById(R.id.btn_hills_silver);
        btnGoldHills = view.findViewById(R.id.btn_hills_gold);
        btnBronzeBeach = view.findViewById(R.id.btn_beach_bronze);
        btnSilverBeach = view.findViewById(R.id.btn_beach_silver);
        btnGoldBeach = view.findViewById(R.id.btn_beach_gold);

        //Listener
        btnBronzeWaterfall.setOnClickListener(this);
        btnSilverWaterfall.setOnClickListener(this);
        btnGoldWaterfall.setOnClickListener(this);
        btnBronzeHills.setOnClickListener(this);
        btnSilverHills.setOnClickListener(this);
        btnGoldHills.setOnClickListener(this);
        btnBronzeBeach.setOnClickListener(this);
        btnSilverBeach.setOnClickListener(this);
        btnGoldBeach.setOnClickListener(this);

        btnMedals.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        getUserInfo();
        setName();
        return view;

    }


    /**
     * Metodo que recibe los datos de la base, los compara con el usuario logueado para asi setear el nombre correspondiente
     **/
    private void setName() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String uid = ds.child("uid").getValue(String.class);
                    String name = ds.child("nombre").getValue(String.class);
                    String lastName = ds.child("apellido").getValue(String.class);
                    String img = ds.child("imgPerfil").getValue(String.class);
                    dbUsers.add(uid);
                    dbName.add(name);
                    dbLastName.add(lastName);
                    dbImg.add(img);
                    userID = user.getUid();

                    for (String id : dbUsers) {
                        if (userID.equals(id)) {
                            position = dbUsers.indexOf(userID);
                            Log.e(" Img ", " " + dbImg.get(position));
                        }
                    }

                    userName.setText(dbName.get(position) + " " + dbLastName.get(position));
                    Glide.with(context)
                            .load(dbImg.get(position))
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(profileImageView);

                }

                Log.d("DataSnapshot: ", String.valueOf(dbUsers));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    /**
     * Metodo onAttach
     *
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
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_medals:
                changeToMedals();
                break;
            case R.id.btn_settings:
                changeToSettings();
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_edit_profile:
                AppPreferences.getInstance(activity).clear();
                Intent intent = new Intent(activity, EditProfile.class);
                startActivity(intent);
                break;
            case R.id.btn_waterfall_bronze:
                showTooltip(view, Gravity.TOP, "1", getString(R.string.tag_wf), getString(R.string.tag_bronze));
                break;
            case R.id.btn_waterfall_silver:
                showTooltip(view, Gravity.TOP, "3", getString(R.string.tag_wf), getString(R.string.tag_silver));
                break;
            case R.id.btn_waterfall_gold:
                showTooltip(view, Gravity.TOP, "6", getString(R.string.tag_wf), getString(R.string.tag_gold));
                tooltip.dismiss();
                break;
            case R.id.btn_hills_bronze:
                showTooltip(view, Gravity.TOP, "1", getString(R.string.tag_hills), getString(R.string.tag_bronze));
                break;
            case R.id.btn_hills_silver:
                showTooltip(view, Gravity.TOP, "3", getString(R.string.tag_hills), getString(R.string.tag_silver));
                break;
            case R.id.btn_hills_gold:
                showTooltip(view, Gravity.TOP, "6", getString(R.string.tag_hills), getString(R.string.tag_gold));
                break;
            case R.id.btn_beach_bronze:
                showTooltip(view, Gravity.TOP, "1", getString(R.string.tag_beach), getString(R.string.tag_bronze));
                break;
            case R.id.btn_beach_silver:
                showTooltip(view, Gravity.TOP, "3", getString(R.string.tag_beach), getString(R.string.tag_silver));
                break;
            case R.id.btn_beach_gold:
                showTooltip(view, Gravity.TOP, "6", getString(R.string.tag_beach), getString(R.string.tag_gold));
                tooltip.dismiss();
                break;
            default:
                break;
        }
    }

    private void getUserInfo(){
        databaseReference.child(aAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    String name = snapshot.child("nombre").getValue().toString();
                    userName.setText(name);

                    if(snapshot.hasChild("imgPerfil")){
                        String image = snapshot.child("imgPerfil").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Método para mostrar los tooltips en el área de las medallas en perfil
     */
    private void showTooltip(View view, int gravity, String value, String lugar, String medalla) {
        btn = (Button) view;
        tooltip = new Tooltip.Builder(btn)
                .setText("Visita " + value + " " + lugar + " para obtener la medalla de " + medalla)
                .setTextColor(Color.WHITE)
                .setBackgroundColor(Color.LTGRAY)
                .setGravity(gravity)
                .setCornerRadius(20f)
                .setMaxWidth(590)
                .setArrowEnabled(true)
                .setTypeface(Typeface.defaultFromStyle(Typeface.SANS_SERIF.NORMAL))
                .setDismissOnClick(true)
                .setPadding(30)
                .show();
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