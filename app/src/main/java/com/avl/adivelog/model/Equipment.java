package com.avl.adivelog.model;

/*
 * Project: JDiveLog: A Dive Logbook written in Java
 * File: Equipment.java
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
import com.avl.adivelog.utils.XMLUtil;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description: Information about the dive gear used during a dive
 *
 * @author Pascal Pellmont <jdivelog@pellmont.dyndns.org>
 */
public class Equipment {

   /** Name of the equipment set */
   private String name = null;
   /** tanks used */
   private ArrayList<Tank> tanks = new ArrayList<Tank>();
   /** weigths */
   private String weight = null;
   /** suit (wetsuit, drysuit, ...) */
   private String suit = null;
   /** gloves (dry, wet, ...) */
   private String gloves = null;
   /** comment */
   private String comment = null;

   public Equipment() { }
   public Equipment(Element e) {
      //setComment(XMLUtil.getValue("Comment", e));
      //setGloves(XMLUtil.getValue("Gloves", e));
      //setSuit(XMLUtil.getValue("Suit", e));
      /*
      NodeList nl = e.getElementsByTagName("Tanks");
      for (int i = 1; i < nl.getLength(); i++) {
         Node node = nl.item(i);
         if (node.getNodeType() == Node.ELEMENT_NODE) {
            Tank t = new Tank((Element) node);
            addTank(t);
         }
      }
      */
      // 'Weight' is a tag on this level, but also beneath the child node 'tanks', so I need to pick
      // the right one. Haven't found a better way yet to do this
      NodeList nl = e.getChildNodes();
      for (int i = 0; i < nl.getLength(); i++) {
         Node node = nl.item(i);
         if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Weight"))
            setWeight(node.getTextContent());
         else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Comment"))
            setComment(node.getTextContent());
         else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Gloves"))
            setGloves(node.getTextContent());
         else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Suit"))
            setSuit(node.getTextContent());
         else if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Tanks")) {
            NodeList tl = node.getChildNodes();
            for (int j = 0; j < tl.getLength(); j++) {
               Node n = tl.item(j);
               if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("Tank")) {
                  Tank t = new Tank((Element) n);
                  addTank(t);
               }
            }
         }
         //setWeight(XMLUtil.getValue("Weight", e));
      }
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getGloves() {
      return gloves;
   }

   public void setGloves(String gloves) {
      this.gloves = gloves;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSuit() {
      return suit;
   }

   public void setSuit(String suit) {
      this.suit = suit;
   }

   public String getWeight() {
      return weight;
   }

   public void setWeight(String weight) {
      this.weight = weight;
   }

   public ArrayList<Tank> getTanks() {
      return tanks;
   }

   public void setTanks(ArrayList<Tank> tanks) {
      this.tanks = tanks;
   }

   public void addTank(Tank tank) {
      this.tanks.add(tank);
   }


   public Equipment deepClone() {
      Equipment e = new Equipment();
      e.name = name;
      e.tanks = new ArrayList<Tank>();
      for (Tank tank : tanks) {
         e.tanks.add(tank.deepClone());
      }
      e.weight = weight;
      e.suit = suit;
      e.gloves = gloves;
      e.comment = comment;
      return e;
   }
}
