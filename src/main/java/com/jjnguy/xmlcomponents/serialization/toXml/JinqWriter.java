package com.jjnguy.xmlcomponents.serialization.toXml;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is a very experimental class. I have actually never tried this...
 * 
 * @author jjnguy
 * 
 */
public class JinqWriter {
   /**
    * This is a very experimental method. I have actually never tried this...
    * 
    */
   public String toXML(Object toSerialize) throws IllegalArgumentException, IllegalAccessException {
      // base cases
      if (toSerialize instanceof String) {
         return "<String>" + toSerialize.toString() + "</String>";
      } else if (toSerialize instanceof Collection<?>) {
         StringBuilder ret = new StringBuilder();
         ret.append("<Collection>");
         Collection<?> l = (Collection<?>) toSerialize;
         for (Object o : l) {
            ret.append(toXML(o));
         }
         ret.append("</Collection>");
         return ret.toString();
      } else if (toSerialize instanceof Map) {
         StringBuilder ret = new StringBuilder();
         ret.append("<Map>");
         Map<?, ?> m = (Map<?, ?>) toSerialize;
         for (Entry<?, ?> e : m.entrySet()) {
            ret.append("<Key>" + toXML(e.getKey()) + "</Key>");
            ret.append("<Value>" + toXML(e.getValue()) + "</Value>");
         }
         ret.append("</Map>");
         return ret.toString();
      } else {
         Class<?> clazzClass = toSerialize.getClass();
         StringBuilder ret = new StringBuilder();
         ret.append("<").append(clazzClass.getName()).append(">");
         
         Field[] fields = clazzClass.getDeclaredFields();
         for (Field f : fields) {
            ret.append(toXML(f.get(toSerialize)));
         }
         
         ret.append("</").append(clazzClass.getName()).append(">");
         return ret.toString();
      }
   }
}
