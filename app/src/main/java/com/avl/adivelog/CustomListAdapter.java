package com.avl.adivelog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avl.adivelog.ListItem;

import java.util.ArrayList;
public class CustomListAdapter extends BaseAdapter {
   private ArrayList<ListItem> listData;
   private LayoutInflater layoutInflater;
   public CustomListAdapter(Context aContext, ArrayList<ListItem> listData) {
      this.listData = listData;
      layoutInflater = LayoutInflater.from(aContext);
   }
   @Override
   public int getCount() {
      return listData.size();
   }
   @Override
   public Object getItem(int position) {
      return listData.get(position);
   }
   @Override
   public long getItemId(int position) {
      return position;
   }
   public View getView(int position, View v, ViewGroup vg) {
      ViewHolder holder;
      if (v == null) {
         v = layoutInflater.inflate(R.layout.activity_listview, null);
         holder = new ViewHolder();
         holder.uLabel = (TextView) v.findViewById(R.id.label);
         holder.uSite = (TextView) v.findViewById(R.id.site);
         holder.uData = (TextView) v.findViewById(R.id.data);
         v.setTag(holder);
      } else {
         holder = (ViewHolder) v.getTag();
      }
      holder.uLabel.setText(listData.get(position).getLabel());
      holder.uSite.setText(listData.get(position).getSite());
      holder.uData.setText(listData.get(position).getData());
      return v;
   }
   static class ViewHolder {
      TextView uLabel;
      TextView uSite;
      TextView uData;
   }
}