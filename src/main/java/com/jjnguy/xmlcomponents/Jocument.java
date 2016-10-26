package com.jjnguy.xmlcomponents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Represents an XML document. Also provides static methods for creating Jocuments.
 * 
 * @author jjnguy
 * 
 */
public class Jocument extends Jode {
   
   private Document d;
   
   /**
    * Creates a new Jocument from a backing {@link Document}
    * 
    * @param d
    *           the Document to represent
    */
   public Jocument(Document d) {
      super(d);
      this.d = d;
   }
   
   @Override
   public Document extend() {
      return d;
   }
   
   /**
    * Creates a Jocument out of a file located at the given file location
    * 
    * @param fileLocation
    *           the place to find the XML file
    * @return a Jocument represent the given xml file
    */
   public static Jocument load(String fileLocation) {
      try {
         return load(new FileInputStream(new File(fileLocation)));
      } catch (FileNotFoundException e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Creates a Jocument out of a file located at the given file location and makes the given node name the root of the
    * Jocument
    * 
    * @param fileLocation
    *           the place to find the XML file
    * @return a Jocument represent the given xml file
    */
   public static Jode load(String fileLocation, String rootName) {
      return load(fileLocation).single(rootName);
   }
   
   /**
    * Creates a Jocument out of a file located at the given URL
    * 
    * @param url
    *           the place to find the XML file
    * @return a Jocument represent the given xml file
    */
   public static Jocument load(URL url) {
      try {
         return load(url.openStream());
      } catch (IOException e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Creates a Jocument out of a file located at the given URL and makes the given node name the root of the Jocument
    * 
    * @param url
    *           the place to find the XML file
    * @return a Jocument represent the given xml file
    */
   public static Jode load(URL url, String rootNode) {
      try {
         return load(url.openStream(), rootNode);
      } catch (IOException e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Creates a Jocument out of the given stream
    * 
    * @param in
    *           the XML file stream
    * @return a Jocument represent the given xml file
    */
   public static Jocument load(InputStream in) {
      try {
         return new Jocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in));
      } catch (Exception e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Creates a Jocument out of the given stream and makes the given node name the root of the Jocument
    * 
    * @param in
    *           the XML file stream
    * @return a Jocument represent the given xml file
    */
   public static Jode load(InputStream in, String rootNode) {
      return load(in).single(rootNode);
   }

   /**
    * Parses a given xml string into a Jocument
    *
    * @param xml
    *           the XML to parse
    * @return a Jocument representing the XML text
    */
   public static Jocument parse(String xml) {
      return Jocument.load(new ByteArrayInputStream(xml.getBytes()));
   }

   /**
    * Parses a given xml string into a Jocument
    *
    * @param xml
    *           the XML to parse
    * @param rootNodeName
    *          the name of the root node of the document
    * @return a Jocument representing the XML text
    */
   public static Jode parse(String xml, String rootNodeName) {
      return Jocument.load(new ByteArrayInputStream(xml.getBytes()), rootNodeName);
   }
}
