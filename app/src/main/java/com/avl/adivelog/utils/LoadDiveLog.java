package com.avl.adivelog.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.avl.adivelog.model.ADive;
import com.avl.adivelog.model.ADiveLog;
import com.avl.adivelog.model.Buddy;
import com.avl.adivelog.model.DiveActivity;
import com.avl.adivelog.model.DiveType;
import com.avl.adivelog.model.Equipment;
import com.avl.adivelog.model.EquipmentSet;
import com.avl.adivelog.model.Masterdata;
import com.avl.adivelog.model.Suit;
import com.avl.adivelog.model.Tank;
import com.avl.adivelog.model.diveSite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class LoadDiveLog {
   public static ADiveLog loadLog(Uri uri, Context con) throws IOException{
      // load divelog file
      InputStream fis = null;
      ADiveLog adl = new ADiveLog();
      Masterdata md = new Masterdata();
      NodeList nList = null;

      if(uri == null) return null;

      try {
         con.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
         fis = con.getContentResolver().openInputStream(uri);
         ArrayList<String> itemArray = new ArrayList<>();

         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(fis);

         Element element = doc.getDocumentElement();
         element.normalize();

         // load buddies
         nList = doc.getElementsByTagName("Buddys").item(0).getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               Buddy b = new Buddy();
               b.setFirstname(XMLUtil.getValue("Firstname", element2));
               b.setLastname(XMLUtil.getValue("Lastname", element2));
               md.addBuddy(b);
            }
         }
         // load dive types
         nList = doc.getElementsByTagName("Divetypes").item(0).getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               DiveType dt = new DiveType();
               dt.setDescription(XMLUtil.getValue("Description", element2));
               md.addDivetype(dt);
            }
         }
         // load dive activities
         nList = doc.getElementsByTagName("Diveactivities").item(0).getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               DiveActivity da = new DiveActivity();
               da.setDescription(XMLUtil.getValue("Description", element2));
               md.addDiveactivity(da);
            }
         }
         // load suits
         nList = doc.getElementsByTagName("Suits").item(0).getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               Suit s = new Suit();
               s.setDescription(XMLUtil.getValue("Description", element2));
               md.addSuit(s);
            }
         }
         // load equipment sets
         nList = doc.getElementsByTagName("EquipmentSets").item(0).getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               EquipmentSet es = new EquipmentSet();
               es.setName(XMLUtil.getValue("Name", element2));
               es.setSuit(XMLUtil.getValue("Suit", element2));
               es.setGloves(XMLUtil.getValue("Gloves", element2));
               es.setTankType(XMLUtil.getValue("TankType", element2));
               es.setTankVolume(XMLUtil.getValue("TankVolume", element2));
               md.addEquipmentSet(es);
            }
         }
         // load dive sites
         nList = doc.getElementsByTagName("DiveSites");
         Node parent = nList.item(0);
         nList = parent.getChildNodes();
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               diveSite ds = new diveSite();
               ds.setAltitude(XMLUtil.getValue("altitude", element2));
               ds.setAvgDepth(XMLUtil.getValue("avgDepth", element2));
               ds.setCity(XMLUtil.getValue("city", element2));
               ds.setCountry(XMLUtil.getValue("country", element2));
               ds.setDescription(XMLUtil.getValue("description", element2));
               ds.setDirections(XMLUtil.getValue("directions", element2));
               ds.setEvaluation(XMLUtil.getValue("evaluation", element2));
               ds.setLatitude(XMLUtil.getValue("latitude", element2));
               ds.setLongitude(XMLUtil.getValue("longitude", element2));
               ds.setMainDiveActivity(XMLUtil.getValue("mainDiveActivity", element2));
               ds.setMaxDepth(XMLUtil.getValue("maxDepth", element2));
               ds.setMinDepth(XMLUtil.getValue("minDepth", element2));
               ds.setOwnerId(XMLUtil.getValue("ownerId", element2));
               ds.setPrivateId(Integer.valueOf(XMLUtil.getValue("privateId", element2)));
               ds.setPublicId(XMLUtil.getValue("publicId", element2));
               ds.setPrivateRemarks(XMLUtil.getValue("privateRemarks", element2));
               if (XMLUtil.getValue("publish", element2).equals("false"))
                  ds.setPublish(false);
               else ds.setPublish(true);
               if (XMLUtil.getValue("published", element2).equals("false"))
                  ds.setPublished(false);
               else ds.setPublished(true);
               ds.setSiteType(Integer.valueOf(XMLUtil.getValue("siteType", element2)));
               ds.setSpot(XMLUtil.getValue("spot", element2));
               ds.setState(XMLUtil.getValue("state", element2));
               ds.setTimezone(XMLUtil.getValue("timezone", element2));
               ds.setWarnings(XMLUtil.getValue("warnings", element2));
               ds.setWaters(XMLUtil.getValue("waters", element2));
               md.addDiveSite(ds);
            }
         }
         adl.setMasterdata(md);

         // add dives
         nList = doc.getElementsByTagName("JDive");
         for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
               Element element2 = (Element) node;
               ADive ad = new ADive();
               ad.setAMV(XMLUtil.getValue("amv", element2));
               ad.setAverageDepth(XMLUtil.getValue("Average_Depth", element2));
               ad.setBuddy(XMLUtil.getValue("Buddy", element2));
               ad.setComment(XMLUtil.getValue("Comment", element2));

               Element dat = (Element) element2.getElementsByTagName("DATE").item(0);
               ad.setDate(XMLUtil.getValue("YEAR", dat), XMLUtil.getValue("MONTH", dat), XMLUtil.getValue("DAY", dat));

               ad.setDepth(XMLUtil.getValue("Depth", element2));
               ad.setDiveActivity(XMLUtil.getValue("DiveActivity", element2));
               ad.setDiveNumber(XMLUtil.getValue("DiveNum", element2));
               ad.setDiveSiteId(Integer.valueOf(XMLUtil.getValue("diveSiteId", element2)));
               ad.setDiveType(XMLUtil.getValue("DiveType", element2));
               ad.setDuration(XMLUtil.getValue("Duration", element2));

               Element eql = (Element) element2.getElementsByTagName("Equipment").item(0);
               Equipment eq = new Equipment(eql);
               ad.setEquipment(eq);

               ad.setSurfaceTemperature(XMLUtil.getValue("surfaceTemperature", element2));
               ad.setTemperature(XMLUtil.getValue("TEMPERATURE", element2));

               Element tim = (Element) element2.getElementsByTagName("TIME").item(0);
               ad.setTime(XMLUtil.getValue("HOUR", tim), XMLUtil.getValue("MINUTE", tim));

               ad.setUnits(XMLUtil.getValue("UNITS", element2));
               ad.setVisibility(XMLUtil.getValue("Visibility", element2));
               adl.addDive(ad);
            }
         }
      }
      catch (FileNotFoundException fe) {
         fe.printStackTrace();
      }
      catch (SAXException se) {
         se.printStackTrace();
      }
      catch (IOException ioe) {
         ioe.printStackTrace();
      }
      catch (ParserConfigurationException pce) {
         pce.printStackTrace();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      finally {
         if (adl.getDives().size() <= 0) adl = null;

         if(fis != null) fis.close();
      }

      return adl;
   }
}
