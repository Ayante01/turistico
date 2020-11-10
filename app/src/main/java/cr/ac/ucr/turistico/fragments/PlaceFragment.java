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
import cr.ac.ucr.turistico.models.PlacesLiked;

public class PlaceFragment extends Fragment {

    private final String TAG = "PlaceFragment";
    private AppCompatActivity activity;
    private PlaceAdapter placeAdapter;

    FirebaseDatabase fbDatabase;
    DatabaseReference myRef;
    DatabaseReference refUsersLikes;

    private ArrayList<Lugar> places = new ArrayList<>();
    private ArrayList<Lugar> auxArray = new ArrayList<>();
    private ArrayList<Lugar> beachesArray = new ArrayList<>();
    private ArrayList<Lugar> hillsArray = new ArrayList<>();
    private ArrayList<Lugar> waterfallsArray = new ArrayList<>();

    private ArrayList<String> likes = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();

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

    } public PlaceFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");
        refUsersLikes = fbDatabase.getReference("UPLikes");

        refUsersLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String dbPlace = ds.child("places").getValue(String.class);
                    String dbUserID = ds.child("userID").getValue(String.class);

                    likes.add(dbPlace);
                    users.add(dbUserID);
                }
                getLikesInfo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });

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
                    Long id = ds.child("id").getValue(Long.class);

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
                    lugar.setId(id);

                    if (ds.child("category").getValue(String.class).equals("Playa")) {
                        beachesArray.add(lugar);
                    }
                    if (ds.child("category").getValue(String.class).equals("Cerro")) {
                        hillsArray.add(lugar);
                    }
                    if (ds.child("category").getValue(String.class).equals("Catarata")) {
                        waterfallsArray.add(lugar);
                    }
                    places.add(lugar);
                }
                getPlacesInfo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    public void getLikesInfo() {
        placeAdapter.addDBLikes(likes, users);
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
        if(category.equals("Todos")){
            auxArray = places;
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
