package com.erkprog.barkabar.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.erkprog.barkabar.R;
import com.erkprog.barkabar.ui.kaktus.KaktusFragment;
import com.erkprog.barkabar.ui.kloop.KloopFragment;
import com.erkprog.barkabar.ui.sputnik.SputnikFragment;


public class TabsFragment extends Fragment {
  private static final String TAG = "Fragment Tabs";
  ViewPager mViewPager;
  PagerSlidingTabStrip mTabs;

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
    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
    adapter.addFragment(KloopFragment.newInstance());
    adapter.addFragment(SputnikFragment.newInstance());
    adapter.addFragment(KaktusFragment.newInstance());
    mViewPager.setAdapter(adapter);
    mTabs.setViewPager(mViewPager);

  }

  public static Fragment newInstance() {
    return new TabsFragment();
  }

}
