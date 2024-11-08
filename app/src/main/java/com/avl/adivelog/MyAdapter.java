package com.avl.adivelog;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;


class MyAdapter extends FragmentPagerAdapter {
   Context context;
   int totalTabs;
   FragmentManager m_fm;

   public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
      super(fm);
      m_fm = fm;
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

   @Override
   public int getItemPosition(Object object) {
      return POSITION_NONE;
   }
}