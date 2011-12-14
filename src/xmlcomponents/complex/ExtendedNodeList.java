package xmlcomponents.complex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.NodeList;

import xmlcomponents.JinqException;
import xmlcomponents.Jode;
import xmlcomponents.JodeFilter;

public class ExtendedNodeList implements NodeList, Iterable<ExtendedNode> {

   protected List<ExtendedNode> jodes;

   public ExtendedNodeList(NodeList list) {
      this(list, false);
   }

   public ExtendedNodeList(NodeList list, boolean retainWhitespace) {
      jodes = new ArrayList<ExtendedNode>(list.getLength());
      for (int i = 0; i < list.getLength(); i++) {
         ExtendedNode toAdd = new ExtendedNode(list.item(i));
         if (!toAdd.isWhiteSpace() || retainWhitespace) {
            jodes.add(toAdd);
         }
      }
   }

   public ExtendedNodeList(List<ExtendedNode> jodes) {
      this.jodes = jodes;
   }

   public ExtendedNodeList distinct() {
      Set<String> names = new HashSet<String>();
      for (ExtendedNode n : this) {
         names.add(n.getNodeName());
      }
      List<ExtendedNode> result = new ArrayList<ExtendedNode>(names.size());
      for (String name : names) {
         result.add(this.filter(name).first());
      }
      return new ExtendedNodeList(result);
   }

   public ExtendedNode first() {
      if (getLength() == 0)
         throw new JinqException("There is no first node");
      return item(0);
   }

   public ExtendedNode single(String nodeName) {
      ExtendedNodeList lst = this.filter(nodeName);
      if (lst.getLength() != 1)
         throw new JinqException("The call to 'single' did not return 1 result.");
      return lst.item(0);
   }

   public ExtendedNode single(JodeFilter filter) {
      ExtendedNodeList lst = this.filter(filter);
      if (lst.getLength() != 1)
         throw new JinqException("The call to 'single' did not return 1 result.");
      return lst.item(0);
   }

   public ExtendedNodeList filter(final String nodeName) {
      return this.filter(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.extend().getNodeName().equals(nodeName);
         }
      });
   }

   public ExtendedNodeList filter(JodeFilter filter) {
      List<ExtendedNode> result = new ArrayList<ExtendedNode>();
      for (ExtendedNode j : this) {
         if (filter.accept(new Jode(j))) {
            result.add(j);
         }
      }
      return new ExtendedNodeList(result);
   }

   public void sort() {
      Collections.sort(jodes);
   }

   @Override
   public int getLength() {
      return jodes.size();
   }

   @Override
   public ExtendedNode item(int arg0) {
      if (arg0 >= getLength() || arg0 < 0)
         return null;
      return jodes.get(arg0);
   }

   @Override
   public Iterator<ExtendedNode> iterator() {
      return jodes.iterator();
   }
}
