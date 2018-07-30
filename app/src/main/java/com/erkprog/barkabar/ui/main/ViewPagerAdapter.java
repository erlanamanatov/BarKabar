package com.erkprog.barkabar.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.erkprog.barkabar.ui.BaseFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

  private ArrayList<BaseFragment> mFragments;


  public ViewPagerAdapter(FragmentManager fm) {
    super(fm);
    mFragments = new ArrayList<>();
  }

  public void addFragment(BaseFragment fragment) {
    mFragments.add(fragment);
  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mFragments.get(position).getTitle();
  }
}
