package com.avl.adivelog;

public class ListItem implements Comparable<String> {
   private String label;
   private String site;
   private String data;

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getSite() {
      return site;
   }

   public void setSite(String site) {
      this.site = site;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

   @Override
   public int compareTo(String s) {
      return this.label.compareTo(s);
   }
}
