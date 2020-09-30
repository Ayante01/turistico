/**
 * Clase MainViewPagerAdapter
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * Variables
     */
    /**
     * Metodo MainViewPagerAdapter
     * @param fragmentManager
     * @param fragments
     */

    private final ArrayList<Fragment> fragments;

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager,@NonNull ArrayList<Fragment> fragments){
                super(fragmentManager);
        this.fragments = fragments;
    }
    /**
     * Metodo getItem
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    /**
     * Metodo getCount
     * @return fragments
     */
    @Override
    public int getCount() {
        return  fragments!= null ? fragments.size() : 0;
    }
}
