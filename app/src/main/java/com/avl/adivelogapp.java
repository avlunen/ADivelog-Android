package com.avl;

import android.app.Application;
import android.content.Context;

import com.preference.PowerPreference;

public class adivelogapp extends Application {
   private static Context context;

   public void onCreate() {
      super.onCreate();
      adivelogapp.context = getApplicationContext();
      PowerPreference.init(this);
   }

   public static Context getAppContext() {
      return adivelogapp.context;
   }
}
