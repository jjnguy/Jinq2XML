package xmlcomponents;

import java.io.InputStream;

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

   /**
    * Creates a Jocument out of the data in the given input stream
    * 
    * @param in
    *           the data to turn into a Jocument
    * @return a Jocument representing the given InputStream
    */
   public static Jocument load(InputStream in) {
      return new Jocument(ExtendedDocument.load(in));
   }
}
