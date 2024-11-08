package com.avl.adivelog;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avl.adivelog.model.ADiveLog;
import com.avl.adivelog.utils.UnitConverter;

import java.text.SimpleDateFormat;

public class StatsFragment extends Fragment {
   public StatsFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_stats, container, false);

      ADiveLog ad = ((MainActivity) view.getContext()).getDivelog();
      if(ad != null) PopulateView(ad, view);

      return view;
   }

   public View PopulateView(ADiveLog adl, View view) {

      if(adl != null) {
         Integer numOfDives = adl.getDives().size();
         TextView textview = (TextView) view.findViewById(R.id.tvNumOfDives);
         textview.setText(numOfDives.toString());

         textview = (TextView) view.findViewById(R.id.tvTotalDiveTime);
         textview.setText(adl.getComplete_Divetime());

         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
         textview = (TextView) view.findViewById(R.id.tvLastDive);
         textview.setText(simpleDateFormat.format(adl.getLastDive().getDate()));

         textview = (TextView) view.findViewById(R.id.tvFirstDive);
         textview.setText(simpleDateFormat.format(adl.getFirstDive().getDate()));

         textview = (TextView) view.findViewById(R.id.tvAvgDive);
         textview.setText(adl.avgDivesPerYear().toString());

         textview = (TextView) view.findViewById(R.id.tvDeepestDive);
         textview.setText(adl.getMaxDepth().toString() + " " + UnitConverter.getDisplayAltitudeUnit());

         textview = (TextView) view.findViewById(R.id.tvAvgDepth);
         textview.setText(adl.getAverageDepth().toString() + " " + UnitConverter.getDisplayAltitudeUnit());

         textview = (TextView) view.findViewById(R.id.tvLongestDive);
         textview.setText(adl.getMaxDiveTime().toString() + " mins");

         textview = (TextView) view.findViewById(R.id.tvAvgDiveTime);
         textview.setText(adl.getAvgDiveTime());

         textview = (TextView) view.findViewById(R.id.tvColdestDive);
         textview.setText(adl.getMinTemperature().toString() + " " + UnitConverter.getDisplayTemperatureUnit());

         textview = (TextView) view.findViewById(R.id.tvWarmestDive);
         textview.setText(adl.getMaxTemperature().toString() + " " + UnitConverter.getDisplayTemperatureUnit());

         textview = (TextView) view.findViewById(R.id.tvAvgTemp);
         textview.setText(adl.getAverageTemperature().toString() + " " + UnitConverter.getDisplayTemperatureUnit());

         textview = (TextView) view.findViewById(R.id.tvBoatDives);
         Integer boatdives = adl.getBoatDives();
         textview.setText(boatdives.toString() + "/" + (numOfDives - boatdives));

         textview = (TextView) view.findViewById(R.id.tvSeaDives);
         Long seadives = adl.getSeaDives();
         textview.setText(seadives.toString());
      }
      return view;
   }
}