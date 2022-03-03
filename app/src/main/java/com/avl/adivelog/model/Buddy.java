package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: Tank.java
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
 * Description: Class describing a buddy
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class Buddy implements Comparable<Buddy>{
   /** Firstname */
   private String firstname;

   /** Lastname */
   private String lastname;

   public String getFirstname() {
      return firstname;
   }
   public String getLastname() {
      return lastname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }
   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public Buddy deepClone() {
      Buddy t = new Buddy();
      t.firstname = firstname;
      t.lastname = lastname;
      return t;
   }

   public int compareTo(Buddy other) {
      return (firstname+"."+lastname).compareTo(other.firstname+"."+other.lastname);
   }
}

