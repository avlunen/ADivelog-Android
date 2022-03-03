package com.avl.adivelog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.avl.adivelog.model.ADiveLog;

class MyAdapter extends FragmentPagerAdapter {
   Context context;
   int totalTabs;

   public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
      super(fm);
      context = c;
      this.totalTabs = totalTabs;
   }

   @Override
   public Fragment getItem(int position) {
      switch (position) {
         case 0:
            StatsFragment statsFragment = new StatsFragment();
            return statsFragment;
         case 1:
            LogFragment logFragment = new LogFragment();
            return logFragment;
         default:
            return null;
      }
   }

   @Override
   public int getCount() {
      return totalTabs;
   }
}