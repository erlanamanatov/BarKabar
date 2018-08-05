package com.erkprog.barkabar.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.ui.BaseFragment;
import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;


public class TabsFragment extends Fragment {
  private static final String TAG = "Fragment Tabs";
  ViewPager mViewPager;
  PagerSlidingTabStrip mTabs;
  ViewPagerAdapter mAdapter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_tabs, container, false);
    mViewPager = v.findViewById(R.id.view_pager);
    mTabs = v.findViewById(R.id.main_tabs);
    return v;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAdapter = new ViewPagerAdapter(getChildFragmentManager());
    mAdapter.addFragment(KloopFragment.newInstance());
    mAdapter.addFragment(SputnikFragment.newInstance());
    mAdapter.addFragment(KaktusFragment.newInstance());
    mViewPager.setAdapter(mAdapter);
    mTabs.setViewPager(mViewPager);

    mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        ((BaseFragment) mAdapter.getItem(position)).customizeTab(mTabs);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    ((BaseFragment) mAdapter.getItem(0)).customizeTab(mTabs);

  }

  public static Fragment newInstance() {
    return new TabsFragment();
  }

}
