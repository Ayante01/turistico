/**
 * Favorite Places Fracment
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cr.ac.ucr.turistico.R;

public class FavoritePlacesFragment extends Fragment {
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
}