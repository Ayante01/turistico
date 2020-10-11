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
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.adapters.PlaceAdapter;
import cr.ac.ucr.turistico.api.PlacesService;
import cr.ac.ucr.turistico.api.RetrofitBuilder;
import cr.ac.ucr.turistico.models.Place;
import cr.ac.ucr.turistico.models.PlaceResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private AppCompatActivity activity;
    private Button btnWaterfalls;
    private Button btnHills;
    private Button btnBeaches;
    private FrameLayout flHeader;
    private EditText edSearch;
    private SearchView svSearch;

    private final String TAG = "PlaceFragment";
    private ArrayList<Place> places;
    private PlaceAdapter placeAdapter;

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


        btnWaterfalls.setOnClickListener(this);
        btnHills.setOnClickListener(this);
        btnBeaches.setOnClickListener(this);
        int id = svSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) svSearch.findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setHintTextColor(Color.LTGRAY);

        textView.setTypeface(ResourcesCompat.getFont(activity, R.font.poppins_regular));


        //Recycler Place
        RecyclerView rvCharacters = view.findViewById(R.id.rv_places);

        placeAdapter = new PlaceAdapter(activity);

        rvCharacters.setAdapter(placeAdapter);
        rvCharacters.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);

        rvCharacters.setLayoutManager(linearLayoutManager);

        // TODO: Descomentar cuando este lista la api
        //placeAdapter.addCharacters(places);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: se hace la lógica

        // TODO: Descomentar cuando este lista la api
       // getPlacesInfo();
    }
    private void getPlacesInfo() {
        PlacesService characterService = RetrofitBuilder.createService(PlacesService.class);

        Call<PlaceResponse> response = characterService.getPlaces();

        response.enqueue(new Callback<PlaceResponse>() {

            @Override
            public void onResponse(@NonNull Call<PlaceResponse> call, @NonNull Response<PlaceResponse> response) {
                if(response.isSuccessful()){

                    PlaceResponse placeResponse = response.body();

                    ArrayList<Place> places = placeResponse.getResults();

                    for(Place place: places){
                        Log.i(TAG, "Character: " + place.getName());
                    }

                    placeAdapter.addCharacters(places);

                } else{
                    Log.e(TAG, "onError: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlaceResponse> call, @NonNull Throwable t) {
                throw new RuntimeException(t);
            }
        });
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

