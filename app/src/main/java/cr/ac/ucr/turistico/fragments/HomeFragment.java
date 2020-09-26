/**
 * Home Fracment
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cr.ac.ucr.turistico.R;

public class HomeFragment extends Fragment {
    /**
     * Constructor
     */
    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     *HomeFragment newInstance
     * @return fragment
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FrameLayout flHeader = view.findViewById(R.id.fl_header);
        TextView tvHeaderTitle = view.findViewById(R.id.tv_header_title);
        TextView tvMedalsSubtitle = view.findViewById(R.id.tv_medals_subtitle);
        LinearLayout llyMedalsContainer = view.findViewById(R.id.lly_medals_container);
        LinearLayout llyBronze = view.findViewById(R.id.lly_bronze);
        ImageView ivBronze = view.findViewById(R.id.iv_bronze);
        TextView tvBronze = view.findViewById(R.id.tv_bronze);
        TextView tvBronzeDescription = view.findViewById(R.id.tv_bronze_description);
        LinearLayout llySilver = view.findViewById(R.id.lly_silver);
        ImageView ivSilver = view.findViewById(R.id.iv_silver);
        TextView tvSilver = view.findViewById(R.id.tv_silver);
        TextView tvSilverDescription = view.findViewById(R.id.tv_silver_description);
        LinearLayout llyGold = view.findViewById(R.id.lly_gold);
        ImageView ivGold = view.findViewById(R.id.iv_gold);
        TextView tvGold = view.findViewById(R.id.tv_gold);
        TextView tvGoldDescription = view.findViewById(R.id.tv_gold_description);
        return view;
    }
}