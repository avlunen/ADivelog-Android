package com.avl.adivelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avl.adivelog.model.diveSite;
import com.avl.adivelog.utils.UnitConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class StatsFragment extends Fragment {
   public StatsFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_stats, container, false);

      Integer numOfDives = ((MainActivity)view.getContext()).getDivelog().getDives().size();
      TextView textview = (TextView) view.findViewById(R.id.tvNumOfDives);
      textview.setText(numOfDives.toString());

      textview = (TextView) view.findViewById(R.id.tvTotalDiveTime);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getComplete_Divetime());

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
      textview = (TextView) view.findViewById(R.id.tvLastDive);
      textview.setText(simpleDateFormat.format(((MainActivity)view.getContext()).getDivelog().getLastDive().getDate()));

      textview = (TextView) view.findViewById(R.id.tvFirstDive);
      textview.setText(simpleDateFormat.format(((MainActivity)view.getContext()).getDivelog().getFirstDive().getDate()));

      textview = (TextView) view.findViewById(R.id.tvAvgDive);
      textview.setText(((MainActivity)view.getContext()).getDivelog().avgDivesPerYear().toString());

      textview = (TextView) view.findViewById(R.id.tvDeepestDive);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getMaxDepth().toString()+" "+ UnitConverter.getDisplayAltitudeUnit());

      textview = (TextView) view.findViewById(R.id.tvAvgDepth);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getAverageDepth().toString()+" "+ UnitConverter.getDisplayAltitudeUnit());

      textview = (TextView) view.findViewById(R.id.tvLongestDive);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getMaxDiveTime().toString()+" mins");

      textview = (TextView) view.findViewById(R.id.tvAvgDiveTime);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getAvgDiveTime());

      textview = (TextView) view.findViewById(R.id.tvColdestDive);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getMinTemperature().toString()+" "+ UnitConverter.getDisplayTemperatureUnit());

      textview = (TextView) view.findViewById(R.id.tvWarmestDive);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getMaxTemperature().toString()+" "+ UnitConverter.getDisplayTemperatureUnit());

      textview = (TextView) view.findViewById(R.id.tvAvgTemp);
      textview.setText(((MainActivity)view.getContext()).getDivelog().getAverageTemperature().toString()+" "+ UnitConverter.getDisplayTemperatureUnit());

      return view;
   }
}