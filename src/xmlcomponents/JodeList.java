package xmlcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NodeList;

import xmlcomponents.complex.ExtendedNode;
import xmlcomponents.complex.ExtendedNodeList;

/**
 * Represents a List of Jodes. Will usually be created by calling children() on a Jode.
 * 
 * @author Justin Nelson
 * 
 */
public class JodeList implements Iterable<Jode> {

   private ExtendedNodeList l;

   /**
    * Creates a JodeList out of a backing NodeList
    * 
    * @param l
    *           the node list to represent
    */
   public JodeList(NodeList l) {
      this(new ExtendedNodeList(l));
   }

   JodeList(ExtendedNodeList l) {
      this.l = l;
   }

   /**
    * Will return a JodeList containing only nodes that matched the filter criteria
    * 
    * @param filter
    *           the filter criteria to use
    * @return a JodeList containing only nodes that matched the filter criteria
    */
   public JodeList filter(JodeFilter filter) {
      return new JodeList(l.filter(filter));
   }

   /**
    * Will return a JodeList containing only nodes that match the given node name
    * 
    * @param nodeName
    *           the name of the nodes we would like to filter by
    * @return JodeList containing only nodes that match the given node name
    */
   public JodeList filter(String nodeName) {
      return new JodeList(l.filter(nodeName));
   }

   /**
    * Will give you to single element of this list matching a given name. If there is more than one matching element,
    * this will throw an {@link JinqException}.
    * 
    * @param filter
    *           the filter to apply to this list
    * @return the single node matching this filter
    */
   public Jode single(JodeFilter filter) {
      return new Jode(l.single(filter));
   }

   /**
    * Will return the only element of this list with the given node name. If there is more than one matching element,
    * this will throw an {@link JinqException}.
    * 
    * @param nodeName
    *           the name of the node to return
    * @return the single node matching the given name
    */
   public Jode single(String nodeName) {
      return new Jode(l.single(nodeName));
   }

   /**
    * Gives the number of top-level Jode elements in this list
    * 
    * @return the number of top-level Jode elements in this list
    */
   public int size() {
      return l.getLength();
   }

   /**
    * Will transform this JodeList into a List of the type specified in the XFormer
    * 
    * @param xform
    *           the method used to transform a Jode into the correct type
    * @return a List<T> of elements that were created from this JodeList
    */
   public <T> List<T> xform(Xformer<T> xform) {
      List<T> ret = new ArrayList<T>();
      // For each element in this Jode list, apply the transform
      for (Jode j : this) {
         ret.add(xform.xform(j));
      }
      return ret;
   }

   public ExtendedNodeList extend() {
      return l;
   }

   @Override
   public Iterator<Jode> iterator() {
      return new Iterator<Jode>() {
         private Iterator<ExtendedNode> backingList = JodeList.this.l.iterator();

         @Override
         public boolean hasNext() {
            return backingList.hasNext();
         }

         @Override
         public Jode next() {
            return new Jode(backingList.next());
         }

         @Override
         public void remove() {
            backingList.remove();
         }
      };
   }
}
