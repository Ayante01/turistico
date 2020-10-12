/**
 * Search Fragment
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cr.ac.ucr.turistico.R;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private AppCompatActivity activity;
    private Button btnWaterfalls;
    private Button btnHills;
    private Button btnBeaches;
    private FrameLayout flHeader;

    private PlaceFragment placeFragment;

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
        this.placeFragment = new PlaceFragment("Playa");
        setupViewPager("Playa");
    }

    private void setupViewPager(String category) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = new PlaceFragment(category);
        ft.replace(R.id.ly_places , fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
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

        btnWaterfalls.setOnClickListener(this);
        btnHills.setOnClickListener(this);
        btnBeaches.setOnClickListener(this);
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
    /**
     * Metodo changeToWaterFall
     * Cambia el color de los botones y la tipografia
     */
    private void changeToWaterFall() {
        setupViewPager("Catarata");
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
        setupViewPager("Cerro");
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
        setupViewPager("Playa");
        btnWaterfalls.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnWaterfalls.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnHills.setBackground(ContextCompat.getDrawable(activity, R.drawable.text_area));
        btnHills.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.black));
        btnBeaches.setBackground(ContextCompat.getDrawable(activity, R.drawable.button));
        btnBeaches.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.white));
    }

}
