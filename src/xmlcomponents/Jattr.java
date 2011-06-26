package xmlcomponents;

import org.w3c.dom.Attr;

import xmlcomponents.complex.ExtendedAttr;

/**
 * Represents an attribute in XML
 * 
 * @author Justin Nelson
 * 
 */
public class Jattr {
   private ExtendedAttr a;

   /**
    * Creates a new Jattr out of a backing Attr
    * 
    * @param a
    *           the Attr to represent
    */
   public Jattr(Attr a) {
      this(new ExtendedAttr(a));
   }

   Jattr(ExtendedAttr a) {
      this.a = a;
   }

   /**
    * Gives you new name of this attribute
    * 
    * @return the name of the attribute
    */
   public String name() {
      return a.getNodeName();
   }

   /**
    * Gives you the value of this attribute
    * 
    * @return the value of this attribute
    */
   public String value() {
      return a.getNodeValue();
   }

   /**
    * Gets you to the node that this attribute was defined in.
    * 
    * @return the parent of this attribute
    */
   public Jode parent() {
      return new Jode(a.getOwnerElement());
   }

   public ExtendedAttr extend() {
      return a;
   }
}
