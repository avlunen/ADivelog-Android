package com.avl.adivelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.avl.adivelog.model.ADiveLog;
import com.avl.adivelog.model.diveSite;
import com.avl.adivelog.utils.LoadDiveLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager viewPager;

   ADiveLog  adl;

   public ADiveLog getDivelog() {
      return adl;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      adl = LoadDiveLog.loadLog("avl_log.jlb", this);

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
}