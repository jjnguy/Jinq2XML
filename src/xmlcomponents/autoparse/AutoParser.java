package xmlcomponents.autoparse;

import java.lang.reflect.Field;

import xmlcomponents.Jode;

/**
 * Class for automatically parsing simple nodes with some attributes.
 * 
 * This parser is not smart.
 * 
 * @author Justin Nelson
 */
public class AutoParser {

   /**
    * Parses a node into the given class type.  Only parses xml attributes into member variables with the same name.
    * @param j
    * @param clazz
    * @return
    */
   public static <T> T parse(Jode j, Class<T> clazz) {
      T ret;
      try {
         ret = clazz.newInstance();
         for (Field f : clazz.getDeclaredFields()) {
            String fieldName = f.getName();
            f.setAccessible(true);
            f.set(ret, j.attribute(fieldName).value());
         }
         return ret;
      } catch (Exception e) {
         return null;
      }
   }
}
