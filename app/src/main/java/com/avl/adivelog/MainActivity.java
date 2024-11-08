package com.avl.adivelog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avl.adivelog.model.ADiveLog;
import com.avl.adivelog.model.diveSite;
import com.avl.adivelog.utils.LoadDiveLog;
import com.preference.PowerPreference;
import com.preference.Preference;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager viewPager;

   ADiveLog  adl;
   Uri m_filename;
   Preference preference;

   public ADiveLog getDivelog() {
      return adl;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      preference = PowerPreference.getFileByName(String.valueOf(getText(R.string.pref_name)));
      Uri uri = Uri.parse(preference.getString("uri"));
      if(uri != null) {
         try {
            adl = LoadDiveLog.loadLog(uri, this);
         }
         catch (IOException e) { e.printStackTrace(); }
      }

      // set up tabs
      tabLayout = findViewById(R.id.tabLayout);
      viewPager = findViewById(R.id.viewPager);
      tabLayout.addTab(tabLayout.newTab().setText(R.string.Tab1));
      tabLayout.addTab(tabLayout.newTab().setText(R.string.Tab2));

      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
      final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(),
            tabLayout.getTabCount());
      viewPager.setAdapter(adapter);
      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
         }
         @Override
         public void onTabUnselected(TabLayout.Tab tab) {
         }
         @Override
         public void onTabReselected(TabLayout.Tab tab) {
         }
      });

      Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
      setSupportActionBar(myToolbar);
   }

   public void onClickMap(View view) {
      Intent intent = new Intent((MainActivity) view.getContext(), MapActivity.class);
      ArrayList<diveSite> ds = adl.getMasterdata().getDiveSites();
      ArrayList<SiteData> sdl = new ArrayList<SiteData>();

      for(diveSite d: ds) {
         SiteData sd = new SiteData();
         sd.setM_site_name(d.getSpot());
         sd.setM_latitude(Double.valueOf(d.getLatitude()));
         sd.setM_longitude(Double.valueOf(d.getLongitude()));
         sdl.add(sd);
      }
      intent.putParcelableArrayListExtra("SPOTS",sdl);

      startActivity(intent);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if(requestCode == 123 && resultCode == RESULT_OK) {
         m_filename = data.getData(); //The uri with the location of the file
         try {
            adl = LoadDiveLog.loadLog(m_filename, this);
            if(adl != null) Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show();
            else return;

            MyAdapter ma = (MyAdapter) viewPager.getAdapter();
            ma.notifyDataSetChanged();

            preference.setString("uri", m_filename.toString());
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   // method to inflate the options menu when
   // the user opens the menu for the first time
   @Override
   public boolean onCreateOptionsMenu( Menu menu ) {
      getMenuInflater().inflate(R.menu.main, menu);
      return super.onCreateOptionsMenu(menu);
   }

   // methods to control the operations that will
   // happen when user clicks on the action buttons
   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
         case R.id.load:
            Intent intent = new Intent().setType("*/*").setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
            break;
      }
      return super.onOptionsItemSelected(item);
   }
}