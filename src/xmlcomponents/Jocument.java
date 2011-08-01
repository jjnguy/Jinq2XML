package xmlcomponents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.w3c.dom.Document;

import xmlcomponents.complex.ExtendedDocument;

/**
 * Represents an XML document. Also provides static methods for creating Jocuments.
 * 
 * @author Justin Nelson
 * 
 */
public class Jocument extends Jode {

   private ExtendedDocument d;

   /**
    * Creates a new Jocument from a backing Document
    * 
    * @param d
    *           the Document to represent
    */
   public Jocument(Document d) {
      this(new ExtendedDocument(d));
   }

   Jocument(ExtendedDocument d) {
      super(d);
      this.d = d;
   }

   public ExtendedDocument extend() {
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
      return new Jocument(ExtendedDocument.load(fileLocation));
   }

   public static Jode load(String fileLocation, String rootName) {
      return load(fileLocation).single(rootName);
   }

   public static Jocument load(URL in) {
      try {
         return load(in.openStream());
      } catch (IOException e) {
         throw new JinqException(e);
      }
   }

   public static Jode load(URL in, String rootNode) {
      try {
         return load(in.openStream(), rootNode);
      } catch (IOException e) {
         throw new JinqException(e);
      }
   }

   public static Jocument load(InputStream in) {
      return new Jocument(ExtendedDocument.load(in));
   }

   public static Jode load(InputStream in, String rootNode) {
      return new Jocument(ExtendedDocument.load(in)).single(rootNode);
   }

   public static Jocument parse(String xml) {
      return Jocument.load(new ByteArrayInputStream(xml.getBytes()));
   }
}
