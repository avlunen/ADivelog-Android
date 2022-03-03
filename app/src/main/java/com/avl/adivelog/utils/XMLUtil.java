package com.avl.adivelog.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
   public static String getValue(String tag, Element element) {
      NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
      Node node = nodeList.item(0);
      return node != null ? node.getNodeValue() : null;
   }
}
