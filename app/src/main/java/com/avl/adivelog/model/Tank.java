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

import com.avl.adivelog.model.udcf.Gas;
import com.avl.adivelog.utils.XMLUtil;

import org.w3c.dom.Element;

/**
 * Description:
 * The tank represents a single or a double set and is a part of the equipment.
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 * @author Alexander von Lünen
 */
public class Tank {
   /** Name of the tank (2x12x232) */
   private String name;
   /** Volume (12L, 24L) */
   private float volume;
   /** W PS (200, 232, 250, 300 bar) */
   private int workingPressure;
   /** Weight of the tank (14 kg) */
   private float weight;
   /** Tanktype (Steel, Aluminium, Carbon, ...) */
   private String type;

   private Gas gas;

   public Tank() {
      name = "";
      volume = (float)0.0;
      workingPressure = 0;
      weight = (float)0.0;
      type = "";
      gas = new Gas();
   }

   public Tank(Element e) {
      name = XMLUtil.getValue("Name", e);
      volume = Float.valueOf(XMLUtil.getValue("Volume", e));
      workingPressure = Integer.valueOf(XMLUtil.getValue("WorkingPressure", e));
      weight = Float.valueOf(XMLUtil.getValue("Weight", e));
      type = XMLUtil.getValue("Type", e);

      gas = new Gas();
      Element g = (Element) e.getElementsByTagName("MIX").item(0);
      gas.setHelium(XMLUtil.getValue("HE", g));
      gas.setNitrogen(XMLUtil.getValue("N2", g));
      gas.setOxygen(XMLUtil.getValue("O2", g));
      gas.setName(XMLUtil.getValue("MIXNAME", g));

      Element t = (Element)g.getElementsByTagName("TANK").item(0);
      gas.setPstart(XMLUtil.getValue("PSTART", t));
      gas.setPend(XMLUtil.getValue("PEND", t));
      gas.setTankvolume(XMLUtil.getValue("TANKVOLUME", t));
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public float getVolume() {
      return volume;
   }

   public void setVolume(float volume) {
      this.volume = volume;
   }

   public float getWeight() {
      return weight;
   }

   public void setWeight(float weight) {
      this.weight = weight;
   }

   public int getWorkingPressure() {
      return workingPressure;
   }

   public void setWorkingPressure(int workingPressure) {
      this.workingPressure = workingPressure;
   }


   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Tank deepClone() {
      Tank t = new Tank();
      t.type = type;
      t.name = name;
      t.volume = volume;
      t.weight = weight;
      t.workingPressure = workingPressure;
      if ( gas != null ) t.gas = gas.deepClone();
      return t;
   }

   public Gas getGas() {
      return gas;
   }

   public void setGas(Gas gas) {
      this.gas = gas;
   }
}
