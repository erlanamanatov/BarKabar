package com.erkprog.barkabar.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.erkprog.barkabar.ui.BaseFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

  private ArrayList<BaseFragment> mFragments;


  ViewPagerAdapter(FragmentManager fm) {
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
