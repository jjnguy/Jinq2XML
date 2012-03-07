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
    * Convenience field for holding the attribute's name
    */
   public final String n;
   /**
    * Convenience field for holding the attribute's value
    */
   public final String v;
   
   /**
    * Creates a new Jattr out of a backing {@link Attr}
    * 
    * @param a
    *           the Attr to represent
    */
   public Jattr(Attr a) {
      this(new ExtendedAttr(a));
   }
   
   Jattr(ExtendedAttr a) {
      this.a = a;
      this.v = a.getNodeValue();
      this.n = a.getNodeName();
   }
   
   /**
    * Gives you the name of this attribute
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
    * Gives you the value of this attribute after applying the supplied {@link Converter}
    * 
    * @return the value of this attribute
    */
   public <T> T value(Converter<String, T> converter) {
      return converter.convert(a.getNodeValue());
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
   
   @Override
   public String toString() {
      return name() + " = " + value();
   }
}
