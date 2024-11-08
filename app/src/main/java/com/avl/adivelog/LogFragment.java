package com.avl.adivelog;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avl.adivelog.model.ADive;
import com.avl.adivelog.model.ADiveLog;
import com.avl.adivelog.model.Equipment;
import com.avl.adivelog.model.diveSite;
import com.avl.adivelog.utils.UnitConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class LogFragment extends Fragment {
   public LogFragment() {
      // Required empty public constructor
   }
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_log, container, false);

      ADiveLog adl = ((MainActivity) view.getContext()).getDivelog();
      if (adl != null) PopulateView(adl, view);
      return view;
   }

   public View PopulateView(ADiveLog ad, View view) {
      ArrayList<ListItem> itemArray = new ArrayList<>();
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

      for(ADive d : ad.getDives()) {
         diveSite v_dive_site = ad.getMasterdata()
               .getDiveSiteByPrivateId(d.getDiveSiteId());
         ListItem it = new ListItem();
         it.setLabel(d.getDiveNumber().toString());
         it.setSite(v_dive_site.getSpot()+", "+v_dive_site.getCity()+"\n"+v_dive_site.getCountry());
         it.setData(sdf.format(d.getDate())
               + "\n" + d.getDepth() + UnitConverter.getDisplayAltitudeUnit()
               + ", " + d.getDuration().intValue() + UnitConverter.getDisplayTimeUnit());
         itemArray.add(it);
      }
      ListView listView = (ListView) view.findViewById(R.id.log_list);

      Collections.reverse(itemArray); // present list in descending order, i.e. newest dive first

      listView.setAdapter(new CustomListAdapter(view.getContext(), itemArray));
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            // Getting listview click value into String variable.
            ADiveLog dl = ((MainActivity) view.getContext()).getDivelog();
            ADive d = dl.getDive((long) (dl.getDives().size() - position));
            if(d == null) return;
            diveSite v_dive_site = ((MainActivity) view.getContext()).getDivelog().getMasterdata()
                  .getDiveSite(d);
            Equipment eqm = d.getEquipment();

            Bundle extras = new Bundle();
            extras.putString("DIVE_NUM", d.getDiveNumber().toString());
            extras.putString("DATE", sdf.format(d.getDate()));
            extras.putString("BUDDY", d.getBuddy());
            extras.putString("COMMENT", d.getComment());
            extras.putString("SPOT", v_dive_site.getSpot());
            extras.putString("CITY", v_dive_site.getCity());
            extras.putString("STATE", v_dive_site.getState());
            extras.putString("COUNTRY", v_dive_site.getCountry());
            Double dep = d.getDepth();
            if(dep != null)
               extras.putString("DEPTH", dep.toString()+UnitConverter.getDisplayAltitudeUnit());
            else
               extras.putString("DEPTH", getString(R.string.Empty_Cell));
            Double dur = d.getDuration();
            if(dur != null)
               extras.putString("DURATION", Integer.valueOf(dur.intValue()).toString()+UnitConverter.getDisplayTimeUnit());
            else
               extras.putString("DURATION", getString(R.string.Empty_Cell));
            Double temp = d.getTemperature();
            if(temp != null)
               extras.putString("TEMPERATURE", temp.toString()+UnitConverter.getDisplayTemperatureUnit());
            else
               extras.putString("TEMPERATURE", getString(R.string.Empty_Cell));
            extras.putString("SUIT", eqm.getSuit());
            extras.putString("GLOVES", eqm.getGloves());
            extras.putString("WEIGHT", eqm.getWeight());
            extras.putInt("NO_OF_TANKS", eqm.getTanks().size());
            for(int i=0;i<eqm.getTanks().size();i++) {
               ArrayList<String> t = new ArrayList<String>();

               t.add(eqm.getTanks().get(i).getType()); // 0

               Double hel = eqm.getTanks().get(i).getGas().getHelium();
               if(hel == null) hel = 0.0;
               t.add(hel.toString()); // 1

               Double nit = eqm.getTanks().get(i).getGas().getNitrogen();
               if (nit == null) nit = 0.0;
               t.add(nit.toString()); // 2

               Double ox = eqm.getTanks().get(i).getGas().getOxygen();
               if(ox == null) ox = 0.0;
               t.add(ox.toString()); // 3

               Double vol = eqm.getTanks().get(i).getGas().getTankvolume();
               if(vol == null) vol = 0.0;
               t.add(vol.toString()); // 4

               Integer press = eqm.getTanks().get(i).getWorkingPressure();
               if(press == null) press = 0;
               t.add(press.toString()); // 5

               Double pstart = eqm.getTanks().get(i).getGas().getPstart();
               if(pstart == null) pstart = 0.0;
               t.add(pstart.toString()); // 6

               Double pend = eqm.getTanks().get(i).getGas().getPend();
               if (pend == null) pend = 0.0;
               t.add(pend.toString()); // 7

               extras.putStringArrayList("TANK_"+i, t);
            }
            Double stemp = d.getTemperature();
            if(stemp != null)
               extras.putString("S_TEMP", stemp.toString()+UnitConverter.getDisplayTemperatureUnit());
            else
               extras.putString("S_TEMP", getString(R.string.Empty_Cell));
            Double atemp = d.getSurfaceTemperature();
            if(atemp != null)
               extras.putString("A_TEMP", atemp.toString()+UnitConverter.getDisplayTemperatureUnit());
            else
               extras.putString("A_TEMP", getString(R.string.Empty_Cell));
            SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
            extras.putString("TIME_IN", stf.format(d.getDate()));

            Intent intent = new Intent((MainActivity) view.getContext(), DetailActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
         }
      });

      return view;
   }
   public void onClickSwitchActivity(View view) {
      Intent intent = new Intent((MainActivity) view.getContext(), DetailActivity.class);
      startActivity(intent);
   }
}
