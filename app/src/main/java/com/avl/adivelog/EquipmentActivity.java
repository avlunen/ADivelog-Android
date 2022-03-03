package com.avl.adivelog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.avl.adivelog.utils.UnitConverter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EquipmentActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_equipment);
      Intent intent = getIntent();
      Bundle extras = intent.getExtras();
      TextView tv;
      String tmp;
      UnitConverter toDisplay = new UnitConverter(UnitConverter.DEFAULT_SYSTEM, UnitConverter.getDisplaySystem());
      DecimalFormat df = new DecimalFormat("###.#");

      tv = (TextView) findViewById(R.id.tvDiveNum);
      tmp = extras.getString("DIVE_NUM");
      tv.setText(tmp);

      tv = (TextView) findViewById(R.id.tvGloves);
      tmp = extras.getString("GLOVES");
      tv.setText(tmp);

      tv = (TextView) findViewById(R.id.tvsuit);
      tmp = extras.getString("SUIT");
      tv.setText(tmp);

      tv = (TextView) findViewById(R.id.tvWeights);
      tmp = extras.getString("WEIGHT");
      tv.setText(tmp+UnitConverter.getDisplayWeightUnit());

      int noOfTanks = extras.getInt("NO_OF_TANKS");

      for(int i = 0; i < noOfTanks; i++) {
         final TableLayout mTableLayout = (TableLayout) findViewById(R.id.tbl_tanktable);
         mTableLayout.setStretchAllColumns(true);
         final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.equipment_view, null);

         ArrayList<String> el = extras.getStringArrayList("TANK_"+i);

         // Filling in cells
         tv = (TextView) tableRow.findViewById(R.id.tableCell1); // type
         tv.setText(el.get(0));

         tv = (TextView) tableRow.findViewById(R.id.tableCell2); // volume
         tv.setText(toDisplay.convertVolume(Double.valueOf(el.get(4))).toString()+UnitConverter.getDisplayVolumeUnit());

         tv = (TextView) tableRow.findViewById(R.id.tableCell3); // gas mix
         Double o2 = Double.valueOf(el.get(3)) * 100.0;
         Double n = Double.valueOf(el.get(2)) * 100.0;
         Double he = Double.valueOf(el.get(1)) * 100.0;
         tv.setText(df.format(o2)+"/"+df.format(n)+"/"+df.format(he));

         tv = (TextView) tableRow.findViewById(R.id.tableCell4); // pstart
         tv.setText(el.get(6)+UnitConverter.getDisplayPressureUnit());

         tv = (TextView) tableRow.findViewById(R.id.tableCell5); // pend
         tv.setText(el.get(7)+UnitConverter.getDisplayPressureUnit());

         //Add row to the table
         mTableLayout.addView(tableRow);
      }
   }
}