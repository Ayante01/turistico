package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.adapters.PlaceAdapter;
import cr.ac.ucr.turistico.models.Lugar;

public class PlaceFragment extends Fragment {

    private final String TAG = "PlaceFragment";
    private AppCompatActivity activity;
    private PlaceAdapter placeAdapter;

    FirebaseDatabase fbDatabase;;
    DatabaseReference myRef;

    private ArrayList<Lugar> auxArray = new ArrayList<>();
    private ArrayList<Lugar> beachesArray = new ArrayList<>();
    private ArrayList<Lugar> hillsArray = new ArrayList<>();
    private ArrayList<Lugar> waterfallsArray = new ArrayList<>();

    private String category;

    private ProgressBar pbLoading;
    private RecyclerView rvPlaces;

    public static PlaceFragment newInstance(String category) {
        PlaceFragment fragment = new PlaceFragment(category);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceFragment(String category) {
        this.category = category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
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
                    if (ds.child("category").getValue(String.class).equals("Playa")) {
                        beachesArray.add(lugar);
                    }
                    if (ds.child("category").getValue(String.class).equals("Cerro")) {
                        hillsArray.add(lugar);
                    }
                    if (ds.child("category").getValue(String.class).equals("Catarata")) {
                        waterfallsArray.add(lugar);
                    }
                }
                getPlacesInfo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    public void getPlacesInfo() {
        if(category.equals("Playa")){
            auxArray = beachesArray;
        }
        if(category.equals("Cerro")){
            auxArray = hillsArray;
        }
        if(category.equals("Catarata")){
            auxArray = waterfallsArray;
        }
        placeAdapter.addPlaces(auxArray);
        showPlaces(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        pbLoading = view.findViewById(R.id.pb_loading);

        rvPlaces = view.findViewById(R.id.rv_places);

        placeAdapter = new PlaceAdapter(activity);

        rvPlaces.setAdapter(placeAdapter);
        rvPlaces.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

        rvPlaces.setLayoutManager(linearLayoutManager);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showPlaces(boolean setVisible) {
        rvPlaces.setVisibility(setVisible ? View.VISIBLE : View.GONE);
        pbLoading.setVisibility(!setVisible ? View.VISIBLE : View.GONE);
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
}
