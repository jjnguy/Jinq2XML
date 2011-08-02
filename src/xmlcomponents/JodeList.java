package xmlcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NodeList;

import xmlcomponents.complex.ExtendedNode;
import xmlcomponents.complex.ExtendedNodeList;
import xmlcomponents.manipulation.Xformer;

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
    * Performs the given action on each node
    * 
    * @param a
    *           the action to perform
    */
   public void each(Action a) {
      for (Jode j : this) {
         a.act(j);
      }
   }

   /**
    * Performs the given action on each node
    * 
    * @param a
    *           the action to perform
    */
   public void each(ActionWithIndex a) {
      for (int i = 0; i < size(); i++) {
         a.act(get(i), i);
      }
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
    * Gets the first element of the list
    * 
    * @return the first element of this list, or null if the list has no children
    */
   public Jode first() {
      if (size() <= 0)
         return null;
      else
         return get(0);
   }

   public Jode first(JodeFilter filter) {
      for (Jode j : this) {
         if (filter.accept(j))
            return j;
      }
      return null;
   }

   /**
    * Gets the first element of the list with the given node name
    * 
    * @param nodeName
    *           the node name to match
    * @return the first child matching the given name, or null if one didn't exist
    */
   public Jode first(final String nodeName) {
      return first(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals(nodeName);
         }
      });
   }

   /**
    * Gets the node at index i
    * 
    * @param i
    *           the index to get the node at
    * @return the node at index i
    */
   public Jode get(int i) {
      return new Jode(l.item(i));
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
            throw new UnsupportedOperationException("You are not allowed to remove jodes fron a list");
         }
      };
   }

   /**
    * Joins a node list into a string with the given delimiter
    * 
    * @param delim
    *           the string to use to split up the list
    * @return a String created from joining all of the nodes in this list with the given delimiter
    */
   public String join(String delim) {
      return join(delim, new Xformer<String>() {
         @Override
         public String xform(Jode j) {
            return j.n;
         }
      });
   }

   /**
    * Joins a node list into a string with the given delimiter
    * 
    * @param delim
    *           the string to use to split up the list
    * @param xformer
    *           the transformer to use to transform each node
    * @return a String created from joining all of the nodes in this list with the given delimiter
    */
   public String join(String delim, Xformer<String> xformer) {
      StringBuilder bldr = new StringBuilder();
      for (Jode j : this) {
         bldr.append(xformer.xform(j)).append(delim);
      }
      return bldr.substring(0, bldr.length() - delim.length());
   }

   /**
    * Recursively finds all nodes with the given name
    * 
    * @param nodeName
    *           the name of the node to search for
    * @return a list of discovered nodes
    */
   public JodeList search(final String nodeName) {
      return search(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals(nodeName);
         }
      });
   }

   /**
    * Recursively finds all nodes that match the given filter
    * 
    * @param filter
    *           the filter to search for
    * @return a list of discovered nodes
    */
   public JodeList search(JodeFilter filter) {
      List<ExtendedNode> ret = new ArrayList<ExtendedNode>();
      for (Jode j : this) {
         if (filter.accept(j))
            ret.add(j.extend());
         else {
            for (Jode j2 : j.children().search(filter)) {
               ret.add(j2.extend());
            }
         }
      }
      return new JodeList(new ExtendedNodeList(ret));
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
}
