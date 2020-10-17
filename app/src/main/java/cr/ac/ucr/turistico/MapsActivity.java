/**
 * Maps Activity
 *
 * @author  Daniela Alarcón
 * @version 1.0
 * @since   2020-10-16
 */
package cr.ac.ucr.turistico;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * Variable tipo GoogleMap
     */
    private GoogleMap mMap;

    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Quesada and move the camera
        LatLng quesada = new LatLng(10.3271,  -84.4357);
        mMap.addMarker(new MarkerOptions().position(quesada).title("Marker in Quesada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quesada));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 17.0));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}