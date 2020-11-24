/**
 * Favorite Places Fracment
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firestore.v1.StructuredQuery;

import cr.ac.ucr.turistico.R;


public class FavoritePlacesFragment extends Fragment implements View.OnClickListener{

    private static FavoritePlacesFragment fragment;
    private Context context;
    private AppCompatActivity activity;
    /**
     * Constructor
     */
    public FavoritePlacesFragment() {
        // Required empty public constructor
    }
    /**
     *FavoritePlacesFragment newInstance
     * @return fragment
     */
    public static FavoritePlacesFragment newInstance() {
        FavoritePlacesFragment fragment = new FavoritePlacesFragment();
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
        setupViewPager();
    }

    public void setupViewPager() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment fragment = new FavoritePlaceFragment(this);
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
        View view = inflater.inflate(R.layout.fragment_favorite_places, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

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
}