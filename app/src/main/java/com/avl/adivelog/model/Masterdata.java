package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: Masterdata.java
 *
 * @author Volker Holthaus <v.holthaus@procar.de>
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Description: Lists of datas such as dive activities, buddies, equipment,...
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 */
public class Masterdata {

   /** Buddys */
   private ArrayList<Buddy> buddies = new ArrayList<Buddy>();

   /** Divetypes */
   private ArrayList<DiveType> divetypes = new ArrayList<DiveType>();

   /** dive activities */
   private ArrayList<DiveActivity> diveactivities = new ArrayList<DiveActivity>();

   /** dive suits */
   private ArrayList<Suit> suits = new ArrayList<Suit>();

   /** gloves */
   private ArrayList<GloveType> gloveTypes = new ArrayList<GloveType>();

   /** equipment sets */
   private ArrayList<EquipmentSet> equipmentSets = new ArrayList<EquipmentSet>();

   /** dive sites */
   private HashMap<Integer, diveSite> diveSites = new HashMap<Integer, diveSite>();

   //private MixDatabase favoriteMixes = new FavoriteMixes();

   public ArrayList<Buddy> getBuddys() {
      return buddies;
   }
   public void setBuddys(ArrayList<Buddy> buddies) {
      this.buddies = buddies;
   }
   public void addBuddy(Buddy buddy) {
      this.buddies.add(buddy);
   }

   public ArrayList<DiveType> getDivetypes() {
      return divetypes;
   }
   public void setDivetypes(ArrayList<DiveType> divetypes) {
      this.divetypes = divetypes;
   }
   public void addDivetype(DiveType divetype) {
      this.divetypes.add(divetype);
   }

   public ArrayList<DiveActivity> getDiveactivities() {
      return diveactivities;
   }
   public void setDiveactivities(ArrayList<DiveActivity> diveactivities) {
      this.diveactivities = diveactivities;
   }
   public void addDiveactivity(DiveActivity diveactivity) {
      this.diveactivities.add(diveactivity);
   }

   public ArrayList<Suit> getSuits() {
      return suits;
   }
   public void setSuits(ArrayList<Suit> suits) {
      this.suits = suits;
   }
   public void addSuit(Suit suit) {
      suits.add(suit);
   }

   public ArrayList<GloveType> getGloveTypes() {
      return gloveTypes;
   }
   public void setGloveTypes(ArrayList<GloveType> gloveTypes) {
      this.gloveTypes = gloveTypes;
   }
   public void addGloveType(GloveType gloveType) {
      gloveTypes.add(gloveType);
   }

   public ArrayList<EquipmentSet> getEquipmentSets() {
      return equipmentSets;
   }
   public void setEquipmentSets(ArrayList<EquipmentSet> equipmentSets) {
      this.equipmentSets = equipmentSets;
   }
   public void addEquipmentSet(EquipmentSet equipmentSet) {
      equipmentSets.add(equipmentSet);
   }

   public void addDiveSite(diveSite ds) {
      diveSites.put(ds.getPrivateId(), ds);
   }
   public ArrayList<diveSite> getDiveSites() {
      ArrayList<diveSite> alds = new ArrayList<diveSite>();

      for(diveSite ds: diveSites.values()) alds.add(ds);

      return alds;
   }

   public diveSite getDiveSiteByPrivateId(Integer privateId) {
      return diveSites.get(privateId);
   }

   public diveSite getDiveSite(ADive dive) {
      return getDiveSiteByPrivateId(dive.getDiveSiteId());
   }
}
