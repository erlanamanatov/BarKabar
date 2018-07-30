package com.erkprog.barkabar.ui.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ViewPager viewPager = findViewById(R.id.view_pager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    adapter.addFragment(new SputnikFragment());
    viewPager.setAdapter(adapter);

    TabLayout tabLayout = findViewById(R.id.main_tabs);
    tabLayout.setupWithViewPager(viewPager);

//    FragmentManager fm = getSupportFragmentManager();
//    Fragment fragment = fm.findFragmentById(R.id.sputnik_fragment_container);
//
//    if (fragment == null) {
//      fragment = new SputnikFragment();
//      fm.beginTransaction().replace(R.id.sputnik_fragment_container, fragment).commit();
//    }

  }
}