/**
 * Place Activity
 *
 * @author  Daniela Alarcón
 * @version 1.0
 * @since   2020-10-17
 */
package cr.ac.ucr.turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cr.ac.ucr.turistico.adapters.ImageAdapter;

public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    /**
     * Variable tipo GoogleMap
     */
    private GoogleMap mMap;
    FirebaseDatabase fbDatabase;;
    DatabaseReference myRef;

    ImageView placeImg;
    TextView headerTitle;
    TextView informationBody;

    private PlaceActivity activity;

    /**
     * Método onCreate
     * Este metodo es ejecutado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        activity = this;

        placeImg = findViewById(R.id.iv_place_img);
        headerTitle = findViewById(R.id.tv_header_title);
        informationBody = findViewById(R.id.tv_information_body);

        Intent intent = getIntent();

        if (intent != null) {
            String placeName = intent.getStringExtra(getString(R.string.place_name));
            setPlaceInfo(placeName);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    /**
     * Método onItemClick
     * Este método abre la imagen en grande al darle click en la galería
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     *
     */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
                intent.putExtra("id", i);
                startActivity(intent);
            }
        });
    }
    /**
     * Método onMapReady
     *
     * Manipula el mapa una vez que se ha creado.
     * Este callback se activa cuando el mapa está listo para usarse.
     * Aquí es donde podemos agregar marcadores o líneas, agregar oyentes o mover la cámara. En este caso,
     * Solo agregamos un marcador del lugar requerido y además aumentamos el zoom de la cámara
     * Si los servicios de Google Play no están instalados en el dispositivo, se le pedirá al usuario que instale
     * dentro del SupportMapFragment. Este método solo se activará una vez que el usuario haya
     * instaado los servicios de Google Play y regrese a la aplicación.
     *
     * @param googleMap
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Quesada and move the camera
        LatLng nauyaca = new LatLng(9.2731049,  -83.8224666);
        mMap.addMarker(new MarkerOptions().position(nauyaca).title("Marker in Nauyaca Waterfalls"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nauyaca));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 14.0));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void setPlaceInfo(final String placeName){
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("place").getValue(String.class).equals(placeName)){

                        Glide.with(activity)
                                .load(ds.child("image").getValue(String.class))
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(placeImg);

                        headerTitle.setText(ds.child("place").getValue(String.class));
                        informationBody.setText(ds.child("info").getValue(String.class));

                    }
                    /*
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
                    */
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }
}