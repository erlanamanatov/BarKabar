package com.erkprog.barkabar.ui.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.erkprog.barkabar.R;
import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  TabLayout mTabLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ViewPager viewPager = findViewById(R.id.view_pager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    adapter.addFragment(KloopFragment.newInstance());
    adapter.addFragment(SputnikFragment.newInstance());
    adapter.addFragment(KaktusFragment.newInstance());

    viewPager.setAdapter(adapter);

    mTabLayout = findViewById(R.id.main_tabs);
    mTabLayout.setupWithViewPager(viewPager);
    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
  }
}
