package com.avl.adivelog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends Activity {
   private Bundle extras;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);
      Intent intent = getIntent();
      extras = intent.getExtras();

      // Receiving value into activity using intent.
      TextView textview = (TextView) findViewById(R.id.tvDiveNum);
      textview.setText(extras.getString("DIVE_NUM"));
      textview = (TextView) findViewById(R.id.tvDate);
      textview.setText(extras.getString("DATE"));
      textview = (TextView) findViewById(R.id.tvSpot);
      textview.setText(extras.getString("SPOT"));
      textview = (TextView) findViewById(R.id.tvCity);
      textview.setText(extras.getString("CITY"));
      textview = (TextView) findViewById(R.id.tvState);
      textview.setText(extras.getString("STATE"));
      textview = (TextView) findViewById(R.id.tvCountry);
      textview.setText(extras.getString("COUNTRY"));
      textview = (TextView) findViewById(R.id.tvDiveTime);
      textview.setText(extras.getString("DURATION"));
      textview = (TextView) findViewById(R.id.tvDepth);
      textview.setText(extras.getString("DEPTH"));
      textview = (TextView) findViewById(R.id.tvATemp);
      textview.setText(extras.getString("A_TEMP"));
      textview = (TextView) findViewById(R.id.tvWTemp);
      textview.setText(extras.getString("S_TEMP"));
      textview = (TextView) findViewById(R.id.tvBuddy);
      textview.setText(extras.getString("BUDDY"));
      textview = (TextView) findViewById(R.id.tvTime);
      textview.setText(extras.getString("TIME_IN"));

   }

   public void onClickClose(View view) {
      finish();
   }

   public void onClickEquipment(View view) {
      Intent intent = new Intent(this, EquipmentActivity.class);
      intent.putExtras(extras);
      startActivity(intent);
   }

}