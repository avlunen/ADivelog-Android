package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: DiveDetailWindow.java
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 *
 * This file is part of JDiveLog.
 * JDiveLog is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * JDiveLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JDiveLog; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import com.avl.adivelog.utils.UnitConverter;
import com.avl.adivelog.model.udcf.Gas;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


/**
 * Description: Dive information
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class ADive implements Comparable<ADive> {

   /** Number of the dive */
   private Long diveNumber;

   /**
    * Units used in this dive entry.
    * Valid values: <b>si</b>, <b>metric</b> or <b>imperial</b>.<br>
    * <b>si</b>: Pressure [Pa], Altitude [m], Temperature [K], Density [kg/m^3], Time [s], Volume [m^3]<br>
    * <b>metric</b>: Pressure [bar], Altitude [m], Temperature [Degree Celsius], Density [kg/m^3], Time [min], Volume [m^3]
    * <b>imperial</b>: Pressure [psi], Altitude [feet], Temperature [Degree Fahrenheit], Density [ kg/m^3 ], Time [min], Volume [m^3]
    * See <a href="http://www.streit.cc/dive/udcf_doc_ger.html">The UDCF Specification</a>.
    */
   private String units;

   /** The date of the dive */
   private Date date = null;

   /**
    * private id of dive site.
    */
   private Integer diveSiteId;

   /** The amv */
   private Double amv;

   /** Maximum depth during dive */
   private Double depth;

   /** Average depth during dive */
   private Double average_depth;

   /**
    * Duration of the dive
    * @see #units */
   private Double duration;

   /** Description of the visibility */
   private String visibility;

   /** Water temperature */
   private Double temperature;

   /** Surface temperature */
   private Double surfaceTemperature;

   /** Buddy */
   private String buddy;

   /** Divetype */
   private String divetype;

   /** Divetype */
   private String diveactivity;

   /** Equipment used during dive */
   private Equipment equipment;

   /** comment to the dive */
   private String comment;


   public String getComment() {
      return comment;
   }
   public void setComment(String comment) {
      this.comment = comment;
   }

   public void setAMV(Double amv) {
      this.amv = amv;
   }
   public void setAMV(String amv) {
      if (amv == null || "".equals(amv)) {
         this.amv = null;
      } else {
         this.amv = Double.valueOf(amv);
      }
   }
   public Double getAMV() {
      if (amv == null && getAverageDepth() != null && !(getAverageDepth().doubleValue() == 0)) {
         UnitConverter convToMetric = new UnitConverter(UnitConverter.getSystem(getUnits()), UnitConverter.SYSTEM_METRIC);
         UnitConverter convFromMetric = new UnitConverter(UnitConverter.SYSTEM_METRIC, UnitConverter.getSystem(getUnits()));
         double consumption = 0.0;
         Iterator<Tank> it = getEquipment().getTanks().iterator();
         while(it.hasNext()) {
            Gas gas = it.next().getGas();
            if (gas != null && gas.getTankvolume() != null) {
               Double pstart = convToMetric.convertPressure(gas.getPstart());
               Double pend = convToMetric.convertPressure(gas.getPend());
               Double vol = convToMetric.convertVolume(gas.getTankvolume());
               Double depth = convToMetric.convertAltitude(getAverageDepth());
               if (pstart != null && pend != null && vol != null && depth != null && pstart.doubleValue() != 0 && pend.doubleValue() != 0 && vol.doubleValue() != 0 && depth.doubleValue() != 0) {
                  double c = (pstart.doubleValue() - pend.doubleValue()) * vol / (1 + (depth / 10));
                  consumption += c;
               }
            }
         }
         if (consumption != 0 && duration != null && duration.doubleValue() != 0) {
            amv = convFromMetric.convertAMV(Double.valueOf(consumption / convToMetric.convertTime(duration)));
         }

      }
      return amv;
   }
   public Double getDepth() {
      return depth;
   }
   public Double getAverageDepth() {
      return average_depth;
   }
   public void setDepth(Double depth) {
      this.depth = depth;
   }
   public void setDepth(String depth) {
      if (depth == null || "".equals(depth)) {
         this.depth = null;
      } else {
         this.depth = Double.valueOf(depth);
      }
   }
   public void setAverageDepth(Double average_depth) {
      this.average_depth = average_depth;
   }
   public void setAverageDepth(String average_depth) {
      if (average_depth == null || "".equals(average_depth)) {
         this.average_depth = null;
      } else {
         this.average_depth = Double.valueOf(average_depth);
      }
   }
   public Long getDiveNumber() {
      return diveNumber;
   }
   public void setDiveNumber(Long diveNumber) {
      this.diveNumber = diveNumber;
   }
   public void setDiveNumber(String diveNumber) {
      if (diveNumber == null || "".equals(diveNumber)) {
         this.diveNumber = null;;
      } else {
         this.diveNumber = Long.valueOf(diveNumber);
      }
   }
   public Double getDuration() {
      return duration;
   }
   public void setDuration(Double duration) {
      this.duration = duration;
   }
   public void setDuration(String duration) {
      if (duration != null && !"".equals(duration)) {
         this.duration = Double.valueOf(duration);
      }
   }
   public Equipment getEquipment() {
      return equipment;
   }
   public void setEquipment(Equipment equipment) {
      this.equipment = equipment;
   }

   public Double getTemperature() {
      return temperature;
   }
   public void setTemperature(Double temperature) {
      this.temperature = temperature;
   }
   public void setTemperature(String temperature) {
      if (temperature == null || "".equals(temperature)) {
         this.temperature = null;
      } else {
         this.temperature = Double.valueOf(temperature);
      }
   }
   public Double getSurfaceTemperature() {
      return surfaceTemperature;
   }
   public void setSurfaceTemperature(Double surfaceTemperature) {
      this.surfaceTemperature = surfaceTemperature;
   }
   public void setSurfaceTemperature(String surfaceTemperature) {
      if (surfaceTemperature == null || "".equals(surfaceTemperature)) {
         this.surfaceTemperature = null;
      } else {
         this.surfaceTemperature = Double.valueOf(surfaceTemperature);
      }
   }
   public String getUnits() {
      return units;
   }
   public void setUnits(String units) {
      this.units = units;
   }
   public String getVisibility() {
      return visibility;
   }
   public void setVisibility(String visibility) {
      this.visibility = visibility;
   }
   public Date getDate() {
      return this.date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public void setDate(String year, String month, String day) {
      Calendar gc = Calendar.getInstance();
      if (year != null && month != null && day != null && !"".equals(year) && !"".equals(month) && !"".equals(day)) {
         int y = Integer.parseInt(year);
         int m = Integer.parseInt(month);
         int d = Integer.parseInt(day);
         Date date = getDate();
         if (date != null) {
            gc.setTime(date);
         }
         gc.set(Calendar.YEAR, y);
         gc.set(Calendar.MONTH, m-1);
         gc.set(Calendar.DAY_OF_MONTH, d);
         setDate(gc.getTime());
      }
   }

   public void setTime(String hour, String minute) {
      Calendar gc = Calendar.getInstance();
      if (hour != null && minute != null && !"".equals(hour) && !"".equals(minute)) {
         Date date = getDate();
         if (date != null) {
            gc.setTime(date);
         }
         gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
         gc.set(Calendar.MINUTE, Integer.parseInt(minute));
         setDate(gc.getTime());
      }
   }
   public String getTime() {
      return Long.valueOf(getDate().getTime()).toString();
   }

   public String getBuddy() {
      return buddy;
   }
   public void setBuddy(String buddy) {
      this.buddy = buddy;
   }

   public String getDiveType() {
      return divetype;
   }
   public void setDiveType(String divetype) {
      this.divetype = divetype;
   }
   public String getDiveActivity() {
      return diveactivity;
   }
   public void setDiveActivity(String diveactivity) {
      this.diveactivity = diveactivity;
   }
   public int compareTo(ADive d) {
      if (this == d) {
         return 0;
      }
      if (d == null) {
         return 1;
      }
      int ret = 0;
      if (getDiveNumber() != null && d.getDiveNumber() != null) {
         ret = getDiveNumber().compareTo(d.getDiveNumber());
      }
      if (ret == 0) {
         if (getDate() != null && d.getDate() != null) {
            ret = getDate().compareTo(d.getDate());
         }
      }
      if (ret == 0) {
         // not comparable, but not the same...
         ret = -1;
      }
      return ret;
   }

   public Double getStoredAMV() {
      return amv;
   }

   public void setDiveSiteId(Integer siteId) {
      diveSiteId = siteId;
   }

   public Integer getDiveSiteId() {
      return diveSiteId;
   }

   public boolean before(ADive dive2) {
      if (dive2 == null) {
         return true;
      }
      Date d1 = getDate();
      Date d2 = dive2.getDate();
      if (d1 == null) {
         return false;
      }
      if (d2 == null) {
         return true;
      }
      return d1.before(d2);
   }
}
