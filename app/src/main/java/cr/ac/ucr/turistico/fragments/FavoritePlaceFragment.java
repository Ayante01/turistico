package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.turistico.R;
import cr.ac.ucr.turistico.adapters.FavoritePlaceAdapter;
import cr.ac.ucr.turistico.models.Lugar;
import cr.ac.ucr.turistico.models.UsuarioLugar;

public class FavoritePlaceFragment extends Fragment {

    private AppCompatActivity activity;
    private FavoritePlaceAdapter favoritePlaceAdapter;
    public FavoritePlacesFragment favoritePlacesFragment;

    FirebaseDatabase fbDatabase;
    DatabaseReference myRef;
    DatabaseReference refUsersLikes;

    private ArrayList<Lugar> places;

    private ProgressBar pbLoading;
    private RecyclerView rvPlaces;

    private ArrayList<Lugar> placesLiked;
    FirebaseAuth aAuth;
    DatabaseReference placesRef;


    private ArrayList<String> placesID = new ArrayList<>();

    private String uId = "";

    private ArrayList<UsuarioLugar> dbUserPlace;

    public FavoritePlaceFragment(FavoritePlacesFragment favoritePlacesFragment) {
        this.favoritePlacesFragment = favoritePlacesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");
        refUsersLikes = fbDatabase.getReference("UPLikes");

        this.places = new ArrayList<>();

        fbDatabase = FirebaseDatabase.getInstance();
        refUsersLikes = fbDatabase.getReference("UPLikes");
        placesRef = fbDatabase.getReference("places");

        this.dbUserPlace = new ArrayList<>();
        this.placesLiked = new ArrayList<>();

        aAuth = FirebaseAuth.getInstance();
        fbDatabase = FirebaseDatabase.getInstance();

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
                    int id = ds.child("id").getValue(Integer.class);
                    int likes = ds.child("likes").getValue(int.class);

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
                    lugar.setLikes(likes);

                    places.add(lugar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });

        refUsersLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placesID.clear();
                dbUserPlace.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idUser = ds.child("userID").getValue(String.class);
                    String dbPlaceID = ds.child("placeID").getValue(String.class);
                    placesID.add(dbPlaceID);
                    UsuarioLugar userPlace = new UsuarioLugar();
                    userPlace.setIdPlace(dbPlaceID);
                    userPlace.setIdUser(idUser);
                    dbUserPlace.add(userPlace);
                }
                getPlacesInfo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    public void refreshDb() {
        refUsersLikes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbUserPlace.clear();
                placesID.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String idUser = ds.child("userID").getValue(String.class);
                    String dbPlaceID = ds.child("placeID").getValue(String.class);
                    placesID.add(dbPlaceID);
                    UsuarioLugar userPlace = new UsuarioLugar();
                    userPlace.setIdPlace(dbPlaceID);
                    userPlace.setIdUser(idUser);
                    dbUserPlace.add(userPlace);
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
        uId = aAuth.getCurrentUser().getUid();
        placesLiked.clear();
        for(UsuarioLugar userPlace : dbUserPlace){
            for(Lugar place : places){
                if(userPlace.getIdUser().equals(uId) && Integer.toString(place.getId()).equals(userPlace.getIdPlace())){
                    placesLiked.add(place);
                }
            }
        }
        favoritePlaceAdapter.addPlaces(placesLiked);
        showPlaces(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        pbLoading = view.findViewById(R.id.pb_loading);

        rvPlaces = view.findViewById(R.id.rv_places);

        favoritePlaceAdapter = new FavoritePlaceAdapter(activity, this);

        rvPlaces.setAdapter(favoritePlaceAdapter);
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
