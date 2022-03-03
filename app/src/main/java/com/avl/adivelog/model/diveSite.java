package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: DiveSite.java
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

/**
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class diveSite implements Comparable<diveSite> {
   private Integer privateId;
   private String publicId;
   private boolean publish;
   private boolean published;
   private String ownerId;
   private String spot;
   private String city;
   private String state;
   private String country;
   private String waters;
   private String timezone;
   private String description;
   private String directions;
   private String warnings;
   private String privateRemarks;
   private int siteType;
   private String longitude;
   private String latitude;
   private String maxDepth;
   private String avgDepth;
   private String minDepth;
   private String altitude;
   private String mainDiveActivity;
   private String evaluation;

   public diveSite() {
      privateId = 0;
      publicId = "";
      publish = false;
      published = false;
      ownerId = "";
      spot = "";
      city = "";
      state = "";
      country = "";
      waters = "";
      timezone = "";
      description = "";
      directions = "";
      warnings = "";
      privateRemarks = "";
      siteType = 0;
      longitude="";
      latitude="";
      maxDepth="";
      avgDepth="";
      minDepth="";
      altitude="";
      evaluation="10";
      mainDiveActivity="";
   }


   public String getAltitude() {
      return altitude;
   }

   public String getMainDiveActivity() {
      return mainDiveActivity;
   }

   public Integer getEvaluation() {
      return Integer.parseInt(evaluation);
   }

   public String getMaxDepth() {
      return maxDepth;
   }

   public String getAvgDepth() {
      return avgDepth;
   }

   public String getMinDepth() {
      return minDepth;
   }

   public String getLongitude() {
      return longitude;
   }

   public String getLatitude() {
      return latitude;
   }

   public String getPrivateRemarks() {
      return privateRemarks;
   }

   public String getCity() {
      return city;
   }

   public String getCountry() {
      return country;
   }

   public String getDescription() {
      return description;
   }

   public String getDirections() {
      return directions;
   }

   public String getOwnerId() {
      return ownerId;
   }

   public Integer getPrivateId() {
      return privateId;
   }

   public String getPublicId() {
      return publicId;
   }

   public boolean isPublish() {
      return publish;
   }

   public boolean isPublished() {
      return published;
   }

   public String getSpot() {
      return spot;
   }

   public String getState() {
      return state;
   }

   public String getWarnings() {
      return warnings;
   }

   public String getWaters() {
      return waters;
   }

   public String getTimezone() {
      return timezone;
   }
   public void setPrivateId(Integer privateId) {
      this.privateId = privateId;
   }

   public void setPublicId(String publicId) {
      this.publicId = publicId;
   }

   public void setPublish(boolean publish) {
      this.publish = publish;
   }

   public void setPublished(boolean published) {
      this.published = published;
   }

   public void setOwnerId(String ownerId) {
      this.ownerId = ownerId;
   }

   public void setSpot(String spot) {
      this.spot = spot;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public void setState(String state) {
      this.state = state;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public void setWaters(String waters) {
      this.waters = waters;
   }

   public void setTimezone(String timezone) {
      this.timezone = timezone;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setDirections(String directions) {
      this.directions = directions;
   }

   public void setWarnings(String warnings) {
      this.warnings = warnings;
   }

   public void setPrivateRemarks(String privateRemarks) {
      this.privateRemarks = privateRemarks;
   }

   public void setSiteType(int siteType) {
      this.siteType = siteType;
   }

   public void setLongitude(String longitude) {
      this.longitude = longitude;
   }

   public void setLatitude(String latitude) {
      this.latitude = latitude;
   }

   public void setMaxDepth(String maxDepth) {
      this.maxDepth = maxDepth;
   }

   public void setAvgDepth(String avgDepth) {
      this.avgDepth = avgDepth;
   }

   public void setMinDepth(String minDepth) {
      this.minDepth = minDepth;
   }

   public void setAltitude(String altitude) {
      this.altitude = altitude;
   }

   public void setMainDiveActivity(String mainDiveActivity) {
      this.mainDiveActivity = mainDiveActivity;
   }

   public void setEvaluation(String evaluation) {
      this.evaluation = evaluation;
   }

   public int compareTo(diveSite s) {
      if (this == s) {
         return 0;
      }
      if (s == null) {
         return 1;
      }
      return getIdentification().compareTo(s.getIdentification());
   }

   private String getIdentification() {
      StringBuffer sb = new StringBuffer();
      sb.append(spot);
      sb.append(city);
      sb.append(state);
      sb.append(country);
      return sb.toString().toLowerCase();
   }

   public int getSiteType() {
      return siteType;
   }
}
