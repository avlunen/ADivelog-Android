package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: JDiveLog.java
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

import android.os.Build;

import com.avl.adivelog.utils.UnitConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Description: main class
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class ADiveLog {
   // private StatisticSettings statisticSettings;

   private Masterdata masterdata;

   private TreeSet<ADive> dives = new TreeSet<ADive>();

   public void addDive(ADive dive) {
      dives.add(dive);
   }

   public void removeDive(ADive dive) { dives.remove(dive); }

   public TreeSet<ADive> getDives() {
      return dives;
   }

   public ADive getDive(Long num) { // BUBU: could this be made more efficient with a binary search?
      for(ADive d : dives) {
         if(d.getDiveNumber().equals(num)) return d;
      }
      return null;
   }

   public BigDecimal getMaxDepth() {
      BigDecimal maxDepth = new BigDecimal(0);
      Iterator<ADive> it = dives.iterator();

      while (it.hasNext()) { // iterate through dives, compare depth with previous max. depth
         ADive dive = it.next();
         if (dive.getDepth() != null) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            BigDecimal tmp = new BigDecimal(c.convertAltitude(dive.getDepth()));
            maxDepth = tmp.max(maxDepth);
         }
      }
      if (maxDepth == null || maxDepth.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }

      return maxDepth;
   }

   public Integer getMaxDiveTime() {
      Integer maxDiveTime = 0;

      for (ADive dive : dives) { // iterate through dives, compare depth with previous max. depth
         if (dive.getDepth() != null) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            Integer tmp = Integer.valueOf(c.convertTime(dive.getDuration()).intValue());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               maxDiveTime = Integer.max(tmp, maxDiveTime);
            }
         }
      }
      if (maxDiveTime == null || maxDiveTime.equals(0)) {
         return 0;
      }

      return maxDiveTime.intValue();
   }

   public BigDecimal getMaxTemperature() {
      BigDecimal maxTemperature = new BigDecimal(0);

      for (ADive dive : dives) { // iterate through dives, compare temperature with previous max. temperature
         if (dive != null && dive.getTemperature() != null && dive.getTemperature() != 0) {
            UnitConverter uc = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            BigDecimal tmp = BigDecimal.valueOf(uc.convertTemperature(dive.getTemperature()));
            maxTemperature = tmp.max(maxTemperature);
         }
      }
      if (maxTemperature == null || maxTemperature.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }
      return maxTemperature;
   }

   public BigDecimal getMinTemperature() {
      BigDecimal minTemperature = new BigDecimal(100); // set to 100, rather than 0, otherwise no minimum will ever be found

      for (ADive dive : dives) { // iterate through dives, compare temperature with previous max. temperature
         if (dive != null && dive.getTemperature() != null && dive.getTemperature() != 0) {
            UnitConverter uc = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            BigDecimal tmp = BigDecimal.valueOf(uc.convertTemperature(dive.getTemperature()));
            minTemperature = tmp.min(minTemperature);
         }
      }
      if (minTemperature == null || minTemperature.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }
      return minTemperature;
   }
   // end of avl

   public String getComplete_Divetime() {
      BigDecimal divetime = new BigDecimal(0);
      Integer days = Integer.valueOf(0);
      Integer hours = Integer.valueOf(0);
      Integer mhours = Integer.valueOf(0);
      Integer minutes = Integer.valueOf(0);

      for (ADive dive : dives) {
         if (dive.getDuration() != null) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            divetime = divetime.add(BigDecimal.valueOf(c.convertTime(dive.getDuration())));
         }
      }

      // change the complete divetime in days, hours and minutes
      if (divetime == null || divetime.equals(new BigDecimal(0))) {
         return "00:00:00";
      }
      days = divetime.intValue() / 1440;
      hours = (divetime.intValue() % 1440) / 60;
      mhours = divetime.intValue() / 60;
      minutes = divetime.intValue() % 60;

      return String.format("%02d", mhours) + " hours, " + String.format("%02d", minutes) + " mins";
      //return String.format("%02d", days) + " days, " + String.format("%02d", hours) + " hours, " + String.format("%02d", minutes) + " mins";
   }

   public String getAvgDiveTime() {
      BigDecimal minutes = BigDecimal.valueOf(0);
      BigDecimal divetime = BigDecimal.valueOf(0);

      for (ADive dive : dives) {
         if (dive.getDuration() != null) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            divetime = divetime.add(BigDecimal.valueOf(c.convertTime(dive.getDuration())));
         }
      }

      minutes = divetime.divide(BigDecimal.valueOf(dives.size()), RoundingMode.FLOOR);

      return minutes.toPlainString() + " mins";
   }

   public BigDecimal getAverageDepth() {
      BigDecimal averageDepth = new BigDecimal(0);
      int i = 0;

      for (ADive dive : dives) {
         if (dive.getDepth() != null) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            averageDepth = averageDepth.add(BigDecimal.valueOf(c.convertAltitude(dive.getDepth())));
            i++;
         }
      }
      if (averageDepth == null || averageDepth.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }
      return i == 0 ? new BigDecimal(0) : averageDepth.divide(new BigDecimal(i), 2, RoundingMode.HALF_UP);
   }

   public BigDecimal getAverageAmv() {
      BigDecimal averageAmv = new BigDecimal(0);
      int i = 0;

      for (ADive dive : dives) {
         if (dive != null && dive.getAMV() != null && dive.getAMV() != 0) {
            UnitConverter c = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            averageAmv = averageAmv.add(BigDecimal.valueOf(c.convertAMV(dive.getAMV())));
            i++;
         }
      }
      if (averageAmv == null || averageAmv.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }
      return i == 0 ? new BigDecimal(0) : averageAmv.divide(new BigDecimal(i), 2, RoundingMode.HALF_UP);
   }

   public BigDecimal getAverageTemperature() {
      BigDecimal averageTemperature = new BigDecimal(0);
      int i = 0;

      for (ADive dive : dives) {
         if (dive != null && dive.getTemperature() != null && dive.getTemperature() != 0) {
            UnitConverter uc = new UnitConverter(UnitConverter.getSystem(dive.getUnits()), UnitConverter.getDisplaySystem());
            averageTemperature = averageTemperature.add(BigDecimal.valueOf(uc.convertTemperature(dive.getTemperature())));
            i++;
         }
      }
      if (averageTemperature == null || averageTemperature.equals(new BigDecimal(0))) {
         return new BigDecimal(0);
      }
      return i == 0 ? new BigDecimal(0) : averageTemperature.divide(new BigDecimal(i), 2, RoundingMode.HALF_UP);
   }

   public void setDives(TreeSet<ADive> dives) {
      this.dives = dives;
   }

   public Long getNextDiveNumber() {
      long number = 0;

      for (ADive dive : dives) {
         if (dive.getDiveNumber() != null && dive.getDiveNumber() > number) {
            number = dive.getDiveNumber();
         }
      }
      return number + 1;
   }

   public Masterdata getMasterdata() {
      if (masterdata == null) {
         masterdata = new Masterdata();
      }
      return masterdata;
   }

   public void setMasterdata(Masterdata masterdata) {
      this.masterdata = masterdata;
   }

   /**
    * @return average number of dives per year
    */
   public Double avgDivesPerYear() {
      GregorianCalendar first_dive = new GregorianCalendar();
      first_dive.setTime(getFirstDive().getDate());
      GregorianCalendar last_dive = new GregorianCalendar();
      last_dive.setTime(getLastDive().getDate());

      int years = last_dive.get(Calendar.YEAR) - first_dive.get(Calendar.YEAR);

      if(years > 0) return Double.valueOf((double)dives.size() / (double)years);

      return 0.0;
   }
   /**
    * @return the last (latest) dive in the logbook.
    */
   public ADive getLastDive() {
      if (getDives().isEmpty()) {
         return null;
      }
      return getDives().last();
   }

   /**
    * @return the first (earliest) dive in the logbook.
    */
   public ADive getFirstDive() {
      if (getDives().isEmpty()) {
         return null;
      }
      return getDives().first();
   }
}
