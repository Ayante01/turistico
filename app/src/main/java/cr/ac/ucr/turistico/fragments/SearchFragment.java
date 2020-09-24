/**
 * Search Fracment
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import cr.ac.ucr.turistico.R;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private AppCompatActivity activity;
    private Button btnWaterfalls;
    private Button btnHills;
    private Button btnBeaches;
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
            case R.id.btn_waterfall:
                changeToWaterFall();
                break;
            case R.id.btn_hill:
                changeToHills();
                break;
            case R.id.btn_beach:
                changeToBeaches();
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
}