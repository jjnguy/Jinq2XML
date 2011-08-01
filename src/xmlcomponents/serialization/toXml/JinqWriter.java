package xmlcomponents.serialization.toXml;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class JinqWriter {

   public String toXML(Object toSerialize) {
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
         return null;
      }
   }
}
