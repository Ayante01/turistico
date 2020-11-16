/**
 * La aplicación turistico será una con la cual los usuarios podrán encontrar distintos destinos turísticos
 * de Costa Rica, asi como tomar fotos a estos y subirlas para ganar medallas
 *
 * La clase Main activity se encarga de unir los diferentes Fragments en una solo pantalla la cual posee un menu
 * infeorior para navegar entre estos
 *
 * @author  Olman Castro
 * @version 1.0
 * @since   2020-09-24
 */
package cr.ac.ucr.turistico;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import cr.ac.ucr.turistico.adapters.MainViewPagerAdapter;
import cr.ac.ucr.turistico.adapters.PlaceAdapter;
import cr.ac.ucr.turistico.fragments.FavoritePlacesFragment;
import cr.ac.ucr.turistico.fragments.HomeFragment;
import cr.ac.ucr.turistico.fragments.ProfileFragment;
import cr.ac.ucr.turistico.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity{
    /**
     * Variables
     */
    private  ViewPager pager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;

    SearchFragment searchFragment = new SearchFragment();
    FavoritePlacesFragment favoritePlacesFragment = new FavoritePlacesFragment();

    /**
     * Metodo onCreate
     * Este metodo es ejecutrado al crearse la clase dentro de la aplicación
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.vp_pager);
        setUpViewPager();

        bottomNavigationView = findViewById(R.id.bnv_btn_menu);
        setUpViewPagerListener();
        setUpBottomNavViewListener();

    }
    /**
     * Metodo setUpViewPager
     * Este metodo es utilizado para inicializar los fragments que seran utulizados en la vista del Main Activity,
     * ademas se crea un MainViewPagerAdapter y se agrega como adapter por medio de pager
     */
    private void setUpViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(HomeFragment.newInstance());
        fragments.add(searchFragment.newInstance());
        fragments.add(favoritePlacesFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(mainViewPagerAdapter);
    }
    /**
     * Metodo setUpBottomNavViewListener
     * Se utiliza como Listener y navegador del menu bajo, cada vez que se selecccione un item este metodo se encargara
     * de desplazar la vista hacia ese fragment
     */
    private void setUpBottomNavViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        pager.setCurrentItem(0);
                        return true;
                    case R.id.search:
                        pager.setCurrentItem(1);
                        return true;
                    case R.id.favorites:
                        pager.setCurrentItem(2);
                        return true;
                    case R.id.profile:
                        pager.setCurrentItem(3);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    /**
     * Metodo setUpViewPagerListener
     * Se utilza para navegar por medio de slice entre los diferentes fragments del Main Activity
     */
    private void setUpViewPagerListener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem =  bottomNavigationView.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}