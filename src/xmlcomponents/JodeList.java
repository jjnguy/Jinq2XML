package xmlcomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlcomponents.manipulation.Xformer;

/**
 * Represents a List of {@link Jode} . Will usually be created by calling <code>children()</code> on a Jode.
 * 
 * @author Justin Nelson
 * 
 */
public class JodeList implements Iterable<Jode>, NodeList {
   
   private InnerNodeList l;
   
   /**
    * Creates a {@link JodeList} out of a backing {@link NodeList}
    * 
    * @param l
    *           the node list to represent
    */
   public JodeList(NodeList l) {
      this.l = new InnerNodeList(l);
   }
   
   JodeList(List<Node> l) {
      this.l = new InnerNodeList(l);
   }
   
   /**
    * Returns a new JodeList containing one node of each name
    * 
    * @return a JodeList containing only distinct elements. Will select the first node with a given name in the list.
    */
   public JodeList distinct() {
      return new JodeList(l.distinct());
   }
   
   /**
    * Returns a new JodeList containing only distinct nodes as determined by the parameter passed into the method.
    * 
    * @return a JodeList containing only distinct elements.
    */
   public JodeList distinct(JodEqualityComparer comparer) {
      List<Node> results = new ArrayList<Node>();
      for (Jode j : this) {
         boolean contains = false;
         for (Node alreadyIn : results) {
            if (comparer.equal(j, new Jode(alreadyIn))) {
               contains = true;
               break;
            }
         }
         if (!contains) {
            results.add(j.extend());
         }
      }
      return new JodeList(results);
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
         private final Iterator<Node> backingList = JodeList.this.l.iterator();
         
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
      List<Node> ret = new ArrayList<Node>();
      for (Jode j : this) {
         if (filter.accept(j))
            ret.add(j.extend());
         else {
            for (Jode j2 : j.children().search(filter)) {
               ret.add(j2.extend());
            }
         }
      }
      return new JodeList(ret);
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
    * Sorts this list by Node name
    */
   public void sort() {
      l.sort();
   }
   
   /**
    * Will transform this JodeList into a List of the type specified in the XFormer
    * 
    * @param xform
    *           the method used to transform a Jode into the correct type
    * @return a List<T> of elements that were created from this JodeList
    */
   public <T> List<T> xform(Xformer<T> xform) {
      List<T> ret = new ArrayList<T>(this.size());
      // For each element in this Jode list, apply the transform
      for (Jode j : this) {
         ret.add(xform.xform(j));
      }
      return ret;
   }
   
   /**
    * Will attempt to parse this list of nodes into the passed in type. See {@link Jode}.toObject for more info.
    * 
    * @param <T>
    *           the type to transform into
    * @param clazz
    *           the type to transform into
    * @return a list of the passed in type
    */
   public <T> List<T> xform(final Class<T> clazz) {
      return xform(new Xformer<T>() {
         @Override
         public T xform(Jode j) {
            return j.toObject(clazz);
         }
      });
   }

   @Override
   public int getLength() {
      return size();
   }

   @Override
   public Node item(int index) {
      return this.get(index).extend();
   }
}

class InnerNodeList implements NodeList, Iterable<Node> {
   
   protected List<Node> jodes;
   
   public InnerNodeList(NodeList list) {
      this(list, false);
   }
   
   public InnerNodeList(NodeList list, boolean retainWhitespace) {
      jodes = new ArrayList<Node>(list.getLength());
      for (int i = 0; i < list.getLength(); i++) {
         Jode toAdd = new Jode(list.item(i));
         if (!toAdd.isWhiteSpace() || retainWhitespace) {
            jodes.add(toAdd.extend());
         }
      }
   }
   
   public InnerNodeList(List<Node> jodes) {
      this.jodes = jodes;
   }
   
   public InnerNodeList distinct() {
      Set<String> names = new HashSet<String>();
      for (Node n : this) {
         names.add(n.getNodeName());
      }
      List<Node> result = new ArrayList<Node>(names.size());
      for (String name : names) {
         result.add(this.filter(name).first());
      }
      return new InnerNodeList(result);
   }
   
   public Node first() {
      if (getLength() == 0)
         throw new JinqException("There is no first node");
      return item(0);
   }
   
   public Node single(String nodeName) {
      InnerNodeList lst = this.filter(nodeName);
      if (lst.getLength() != 1) {
         throw new JinqException("The call to 'single' " + nodeName + " did not return 1 result.  It returned "
               + lst.getLength() + " items.");
      }
      return lst.item(0);
   }
   
   public Node single(JodeFilter filter) {
      InnerNodeList lst = this.filter(filter);
      if (lst.getLength() != 1)
         throw new JinqException("The call to 'single' did not return 1 result.");
      return lst.item(0);
   }
   
   public InnerNodeList filter(final String nodeName) {
      return this.filter(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.extend().getNodeName().equals(nodeName);
         }
      });
   }
   
   public InnerNodeList filter(JodeFilter filter) {
      List<Node> result = new ArrayList<Node>();
      for (Node j : this) {
         if (filter.accept(new Jode(j))) {
            result.add(j);
         }
      }
      return new InnerNodeList(result);
   }
   
   public void sort() {
      Collections.sort(jodes, new Comparator<Node>() {
         @Override
         public int compare(Node o1, Node o2) {
            return o1.getNodeName().compareTo(o2.getNodeName());
         }
      });
   }
   
   @Override
   public int getLength() {
      return jodes.size();
   }
   
   @Override
   public Node item(int arg0) {
      if (arg0 >= getLength() || arg0 < 0)
         return null;
      return jodes.get(arg0);
   }
   
   @Override
   public Iterator<Node> iterator() {
      return jodes.iterator();
   }
}
