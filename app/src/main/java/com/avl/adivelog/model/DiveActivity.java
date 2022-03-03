package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: DiveActivity.java
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
/**
 * Description: Class describing the diveactivities
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class DiveActivity implements Comparable<DiveActivity> {
   /** description */
   private String description = null;

   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }

   public DiveActivity deepClone() {
      DiveActivity t = new DiveActivity();
      t.description = description;
      return t;
   }

   public int compareTo(DiveActivity other) {
      return description.compareTo(other.description);
   }
}

