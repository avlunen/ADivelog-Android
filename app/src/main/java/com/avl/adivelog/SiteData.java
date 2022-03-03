package com.avl.adivelog;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteData implements Parcelable {
   private String m_site_name;
   private Double m_latitude;
   private Double m_longitude;

   public String getM_site_name() {
      return m_site_name;
   }

   public void setM_site_name(String m_site_name) {
      this.m_site_name = m_site_name;
   }

   public Double getM_latitude() {
      return m_latitude;
   }

   public void setM_latitude(Double m_latitude) {
      this.m_latitude = m_latitude;
   }

   public Double getM_longitude() {
      return m_longitude;
   }

   public void setM_longitude(Double m_longitude) {
      this.m_longitude = m_longitude;
   }

   public SiteData() {}

   protected SiteData(Parcel in) {
      String[] data = new String[3];

      in.readStringArray(data);
      // the order needs to be the same as in writeToParcel() method
      this.m_site_name = data[0];
      this.m_latitude = Double.valueOf(data[1]);
      this.m_longitude = Double.valueOf(data[2]);
   }

   public static final Creator<SiteData> CREATOR = new Creator<SiteData>() {
      @Override
      public SiteData createFromParcel(Parcel in) {
         return new SiteData(in);
      }

      @Override
      public SiteData[] newArray(int size) {
         return new SiteData[size];
      }
   };

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeStringArray(new String[] {this.m_site_name,
            this.m_latitude.toString(),
            this.m_longitude.toString()});
   }
}
