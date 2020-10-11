/**
 * Search Fracment
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.models.Lugar;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private AppCompatActivity activity;
    private Button btnWaterfalls;
    private Button btnHills;
    private Button btnBeaches;
    private FrameLayout flHeader;
    private EditText edSearch;
    private SearchView svSearch;

    FirebaseDatabase fbDatabase;
    DatabaseReference myRef;

    ArrayList<Lugar> dbPlaces;
    ArrayList<String> dbPlaceCategory;

    /**
     * Constructor
     */
    public SearchFragment() {
        // Required empty public constructor
    }
    /**
     *Search Fragment newInstance
     * @return fragment
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
    }
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        flHeader = view.findViewById(R.id.fl_header);
        btnWaterfalls = view.findViewById(R.id.btn_waterfall);
        btnHills =  view.findViewById(R.id.btn_hill);
        btnBeaches =  view.findViewById(R.id.btn_beach);
       /* edSearch = view.findViewById(R.id.ed_search);*/
        svSearch = view.findViewById(R.id.sv_search);

        dbPlaces = new ArrayList<>();
        dbPlaceCategory = new ArrayList<>();
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");

        btnWaterfalls.setOnClickListener(this);
        btnHills.setOnClickListener(this);
        btnBeaches.setOnClickListener(this);
        int id = svSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) svSearch.findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setHintTextColor(Color.LTGRAY);

        textView.setTypeface(ResourcesCompat.getFont(activity, R.font.poppins_regular));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    boolean beach = ds.child("beach").getValue(boolean.class);
                    boolean wifi = ds.child("wifi").getValue(boolean.class);
                    boolean restaurant = ds.child("restaurant").getValue(boolean.class);
                    boolean transport = ds.child("transport").getValue(boolean.class);
                    boolean coffee = ds.child("coffeeShop").getValue(boolean.class);
                    String category = ds.child("category").getValue(String.class);
                    String image = ds.child("image").getValue(String.class);
                    String info = ds.child("info").getValue(String.class);
                    String place = ds.child("place").getValue(String.class);
                    String province = ds.child("province").getValue(String.class);
                    String ubication = ds.child("ubication").getValue(String.class);

                    Lugar lugar = new Lugar();
                    lugar.setPlace(place);
                    lugar.setInfo(info);
                    lugar.setProvince(province);
                    lugar.setUbication(ubication);
                    lugar.setImage(image);
                    lugar.setCategory(category);
                    lugar.setCoffeeShop(coffee);
                    lugar.setTransport(transport);
                    lugar.setRestaurant(restaurant);
                    lugar.setWifi(wifi);
                    lugar.setBeach(beach);

                    dbPlaces.add(lugar);
                    dbPlaceCategory.add(place);
                    Log.d("TAG ", ""+dbPlaces   );
                }

                Log.d("DataSnapshot: ", String.valueOf(dbPlaces));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });

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
            case R.id.btn_beach:
                changeToBeaches();
                flHeader.setBackgroundResource(R.drawable.manzanillo);
                break;
            case R.id.btn_hill:
                changeToHills();
                flHeader.setBackgroundResource(R.drawable.pelado);
                break;
                case R.id.btn_waterfall:
                changeToWaterFall();
                flHeader.setBackgroundResource(R.drawable.river);
                break;
            default:
                break;
        }
    }

   /* @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String value = "";//any text you are pre-filling in the EditText

        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
            if (edSearch.isFocused()) {
                edSearch.setHint("gi");
            }
            return true;
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (!edSearch.isFocused()) {
                edSearch.setHint(value);
            }
            return false;
        }
        return false;
    }*/
    /**
     * Metodo changeToWaterFall
     * Cambia el color de los botones y la tipografia
     */
    private void changeToWaterFall() {
        btnWaterfalls.setBackground(ContextCompat.getDrawable(activity, R.drawable.button));
        btnWaterfalls.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
        btnHills.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnHills.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnBeaches.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnBeaches.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
    }
    /**
     * Metodo changeToHills
     * Cambia el color de los botones y la tipografia
     */
    private void changeToHills() {
        btnWaterfalls.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnWaterfalls.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnHills.setBackground(ContextCompat.getDrawable(activity, R.drawable.button));
        btnHills.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
        btnBeaches.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnBeaches.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));

    }
    /**
     * Metodo changeToBeaches
     * Cambia el color de los botones y la tipografia
     */
    private void changeToBeaches() {
        btnWaterfalls.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnWaterfalls.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnHills.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnHills.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnBeaches.setBackground(ContextCompat.getDrawable(activity, R.drawable.button));
        btnBeaches.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
    }

/*

    public void onFocusChange(View view, boolean b) {
          if (b) {
              edSearch.setHint("");
          } else {
              edSearch.setHint("Your hint");
              edSearch.setText("hi");
          }
    }*/
}

