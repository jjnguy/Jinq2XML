package xmlcomponents.autoparse;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import xmlcomponents.Converter;
import xmlcomponents.Jattr;
import xmlcomponents.Jode;
import xmlcomponents.autoparse.annotation.XmlProperty;
import xmlcomponents.autoparse.annotation.XmlProperty.XmlPropertyType;

/**
 * Class for automatically parsing simple nodes with some attributes.
 * 
 * This parser is not smart.
 * 
 * @author Justin Nelson
 */
public class AutoParser {

   /**
    * Parses a node into the given class type. with the same name. Use the XmlProperty annotation to customize how this
    * parses your file.
    * 
    * @param j
    *           the Jode to parse
    * @param clazz
    *           the type to parse the Jode (j) into
    * @return the object represented by the xml structure
    */
   public static <T> T parse(Jode j, Class<T> clazz) {
      T ret;
      try {
         ret = clazz.newInstance();
         for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            Object fieldValue = resolveField(f, j);
            f.set(ret, fieldValue);
         }
         return ret;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private static Object resolveField(Field f, Jode j) throws IllegalAccessException, InstantiationException {
      Class<?> type = f.getType();
      XmlProperty anno = f.getAnnotation(XmlProperty.class);
      String fieldName = getValueName(f);
      if (anno != null && anno.type() == XmlPropertyType.TEXT_NODE) {
         return j.v;
      } else if (j.hasAttribute(fieldName)) {
         Jattr attr = j.attribute(fieldName);
         Converter<String, Object> converter = determineConverter(type);
         return attr.value(converter);
      } else if (j.hasSingleChild(fieldName) && !type.isInstance((Collection<?>) null)) {
         Object complex = type.newInstance();
         for (Field subF : complex.getClass().getDeclaredFields()) {
            subF.setAccessible(true);
            Object resolvedResult = resolveField(subF, j.single(fieldName));
            if (resolvedResult != null || !subF.getType().isPrimitive()) {
               subF.set(complex, resolvedResult);
            }
         }
         return complex;
      } else if (type.isInstance((List) (new ArrayList()))) {
         ArrayList<Object> values = new ArrayList<Object>();
         ParameterizedType genericType = (ParameterizedType) f.getGenericType();
         Class realGenericType_finally = (Class) genericType.getActualTypeArguments()[0];
         for (Jode subJ : j.children(fieldName)) {
            values.add(parse(subJ, realGenericType_finally));
         }
         return values;
      }
      if (anno == null || !anno.optional()) {
         throw new IllegalArgumentException("Could not parse field named '" + fieldName + "' for jode named '" + j.n
               + "'");
      }
      // By here, we know that the item we are trying to parse has not been able to be resolved, but it is optional,
      // so we just return null.
      return null;
   }

   private static String getValueName(Field f) {
      XmlProperty anno = f.getAnnotation(XmlProperty.class);
      if (anno != null && !anno.valueName().equals("")) {
         return anno.valueName();
      } else {
         return f.getName();
      }
   }

   private static String cammelToPascal(String s) {
      return (s.charAt(0) + "").toUpperCase() + s.substring(1);
   }

   private static boolean empty(String s) {
      return s == null || s.equals("");
   }

   private static Converter<String, Object> determineConverter(Class<?> type) {
      if (type.equals(String.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               return value;
            }
         };
      } else if (type.equals(Integer.class) || type.equals(int.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return 0;
               return Integer.parseInt(value);
            }
         };
      } else if (type.equals(Long.class) || type.equals(long.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return 0;
               return Long.parseLong(value);
            }
         };
      } else if (type.equals(Byte.class) || type.equals(byte.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return 0;
               return Byte.parseByte(value);
            }
         };
      } else if (type.equals(Double.class) || type.equals(double.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return 0;
               return Double.parseDouble(value);
            }
         };
      } else if (type.equals(Float.class) || type.equals(float.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return 0;
               return Float.parseFloat(value);
            }
         };
      } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               if (empty(value))
                  return false;
               return Boolean.parseBoolean(value);
            }
         };
      }
      // Begin complex types
      else if (type.equals(URL.class)) {
         return new Converter<String, Object>() {
            @Override
            public Object convert(String value) {
               try {
                  return new URL(value);
               } catch (MalformedURLException e) {
                  throw new IllegalArgumentException("Could not convert the given value into a URL.");
               }
            }
         };
      }
      throw new IllegalArgumentException("The type '" + type + "' is not supported in atribute parsing.");
   }
}
