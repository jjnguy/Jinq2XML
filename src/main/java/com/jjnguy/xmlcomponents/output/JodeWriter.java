package com.jjnguy.xmlcomponents.output;

import com.jjnguy.xmlcomponents.Jattr;
import com.jjnguy.xmlcomponents.Jode;

/**
 * Describes {@link Jode}s as a string
 */
public class JodeWriter {
   
   private Jode j;
   
   public JodeWriter(Jode j) {
      this.j = j;
   }
   
   @Override
   public String toString() {
      final StringBuilder bldr = new StringBuilder();
      bldr.append("<").append(j.n);
      for (Jattr a : j.attributes()) {
         bldr.append(" ").append(a.name()).append("=\"").append(a.value()).append("\"");
      }
      bldr.append(">");
      j.children().each(j -> bldr.append(new JodeWriter(j).toString()));
      bldr.append("</").append(j.n).append(">");
      return bldr.toString();
   }
}
