/**
 * Place Activity
 *
 * @author Daniela Alarcón
 * @version 1.0
 * @since 2020-10-17
 */
package cr.ac.ucr.turistico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import cr.ac.ucr.turistico.adapters.ImageAdapter;

public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    /**
     * Variable tipo GoogleMap
     */
    private GoogleMap mMap;
    private FirebaseDatabase fbDatabase;

    private DatabaseReference myRef;

    private ImageView placeImg;
    private TextView headerTitle;
    private TextView informationBody;

    private LinearLayout firstService;
    private LinearLayout secondService;
    private LinearLayout thirdService;
    private LinearLayout fourthService;
    private LinearLayout fifthService;

    private ImageView lineFirstService;
    private ImageView lineSecondService;
    private ImageView lineThirdService;
    private ImageView lineFourthService;

    private double latitude;
    private double longitude;

    private PlaceActivity context;
    private Toolbar tToolbar;
    private Button btnUpload;

    private DatabaseReference databaseReference;
    private FirebaseAuth aAuth;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

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


        firstService = findViewById(R.id.ly_first_service);
        secondService = findViewById(R.id.ly_second_service);
        thirdService = findViewById(R.id.ly_third_service);
        fourthService = findViewById(R.id.ly_fourth_service);
        fifthService = findViewById(R.id.ly_fifth_service);

        lineFirstService = findViewById(R.id.iv_line_first_service);
        lineSecondService = findViewById(R.id.iv_line_second_service);
        lineThirdService = findViewById(R.id.iv_line_third_service);
        lineFourthService = findViewById(R.id.iv_line_fourth_service);

        context = this;

        placeImg = findViewById(R.id.iv_place_img);
        headerTitle = findViewById(R.id.tv_header_title);
        informationBody = findViewById(R.id.tv_information_body);

        btnUpload = findViewById(R.id.btn_upload_gallery);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadGalleryPic();
            }
        });

        getPlaceInfo();


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

        tToolbar = findViewById(R.id.t_toolbar);
        tToolbar.setTitle("");

        setSupportActionBar(tToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }

    public void mapRefresh(String placeName){
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in "+ placeName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 14.0));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void setPlaceInfo(final String placeName) {
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference("places");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("place").getValue(String.class).equals(placeName)) {
                        latitude =  ds.child("latitude").getValue(Double.class);
                        longitude = ds.child("longitude").getValue(Double.class);

                        mapRefresh(placeName);
                        if(ds.child("image").getValue(String.class) != null){
                            Glide.with(context)
                                    .load(ds.child("image").getValue(String.class))
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(placeImg);

                            headerTitle.setText(ds.child("place").getValue(String.class));
                            informationBody.setText(ds.child("info").getValue(String.class));
                        }

                        if (ds.child("transport").getValue(boolean.class) == false) {
                            firstService.setVisibility(View.GONE);
                            lineFirstService.setVisibility(View.GONE);
                        }
                        if (ds.child("wifi").getValue(boolean.class) == false) {
                            secondService.setVisibility(View.GONE);
                            lineSecondService.setVisibility(View.GONE);
                        }
                        if (ds.child("beach").getValue(boolean.class) == false) {
                            thirdService.setVisibility(View.GONE);
                            lineThirdService.setVisibility(View.GONE);
                        }
                        if (ds.child("transport").getValue(boolean.class) == false) {
                            fourthService.setVisibility(View.GONE);
                            lineFourthService.setVisibility(View.GONE);
                        }
                        if (ds.child("restaurant").getValue(boolean.class) == false) {
                            fifthService.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DataSnapshot: ", error.getMessage());
            }
        });
    }

    private void getPlaceInfo() {
        databaseReference.child(aAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("imgLugar")) {
                        String image = snapshot.child("imgLugar").getValue().toString();
                       // Picasso.get().load(image).into(profileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

         //   profileImageView.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadGalleryPic() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Upload your photo");
        progressDialog.setMessage("Please wait while your photo is uploading");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePicsRef.child("fotosLugar")
                    .child(aAuth.getCurrentUser().getUid() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("imgLugar", myUri);

                        databaseReference.child(aAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }
}