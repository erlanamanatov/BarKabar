package com.erkprog.barkabar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.erkprog.barkabar.ui.sputnik.SputnikFragment;


public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.sputnik_fragment_container);

    if (fragment == null) {
      fragment = new SputnikFragment();
      fm.beginTransaction().replace(R.id.sputnik_fragment_container, fragment).commit();
    }

  }
}
