package xmlcomponents.autoparse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xmlcomponents.autoparse.AutoParser;

/**
 * This annotation is used to tell the {@link AutoParser} which fields it should parse, and what names to expect.
 * 
 * @author jjnguy
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlProperty {
   /**
    * Determines if this field is optional. Default is <code>false</code>
    * 
    * @return whether or not this field is optional
    */
   public boolean optional() default false;
   
   /**
    * Specifies the name of the attribute to parse for this field
    * 
    * @return the name of this field in the XML
    */
   public String valueName() default "";
   
   /**
    * Used to tell the parser that this field should be filled with the TextNode of the XML.
    * 
    */
   public XmlPropertyType type() default XmlPropertyType.ANY_ELEMENT;
   
   public static enum XmlPropertyType {
      TEXT_NODE, ANY_ELEMENT
   }
}
