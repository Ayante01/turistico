package cr.ac.ucr.turistico;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import cr.ac.ucr.turistico.adapters.MainViewPagerAdapter;
import cr.ac.ucr.turistico.fragments.FavoritePlacesFragment;
import cr.ac.ucr.turistico.fragments.HomeFragment;
import cr.ac.ucr.turistico.fragments.ProfileFragment;
import cr.ac.ucr.turistico.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  ViewPager pager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;

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

    private  void setUpViewPager(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(HomeFragment.newInstance());
        fragments.add(SearchFragment.newInstance());
        fragments.add(FavoritePlacesFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(mainViewPagerAdapter);
    }

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

    @Override
    public void onClick(View view) {
    }

}