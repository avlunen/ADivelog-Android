package com.avl;

import android.app.Application;
import android.content.Context;

public class adivelogapp extends Application {
   private static Context context;

   public void onCreate() {
      super.onCreate();
      adivelogapp.context = getApplicationContext();
   }

   public static Context getAppContext() {
      return adivelogapp.context;
   }
}
