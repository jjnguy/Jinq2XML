package com.jjnguy.xmlcomponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a List of {@link Jode} . Will usually be created by calling <code>children()</code> on a Jode.
 * 
 * @author Justin Nelson
 * 
 */
public class JodeList implements Iterable<Jode>, NodeList {

   private List<Node> nodes;

   /**
    * Creates a {@link JodeList} out of a backing {@link NodeList}
    * 
    * @param l
    *           the node list to represent
    */
   public JodeList(NodeList l) {
      this.nodes = new ArrayList<Node>(l.getLength());
      for (int i = 0; i < l.getLength(); i++) {
         if (!new Jode(l.item(i)).isWhiteSpace()) {
            this.nodes.add(l.item(i));
         }
      }
   }

   JodeList(List<Node> l) {
      this.nodes = l;
   }

   /**
    * Returns a new JodeList containing one node of each name
    * 
    * @return a JodeList containing only distinct elements. Will select the first node with a given name in the list.
    */
   public JodeList distinct() {
      return distinct((j1, j2) -> j1.n.equals(j2.n));
   }

   /**
    * Returns a new JodeList containing only distinct nodes as determined by the parameter passed into the method.
    * 
    * @return a JodeList containing only distinct elements.
    */
   public JodeList distinct(JodeEqualityComparer comparer) {
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
   public void each(Consumer<Jode> a) {
      for (Jode j : this) {
         a.accept(j);
      }
   }

   /**
    * Performs the given action on each node
    * 
    * @param a
    *           the action to perform. The second param is the current index
    */
   public void each(ObjIntConsumer<Jode> a) {
      for (int i = 0; i < size(); i++) {
         a.accept(get(i), i);
      }
   }

   /**
    * Will return a JodeList containing only nodes that matched the filter criteria
    * 
    * @param filter
    *           the filter criteria to use
    * @return a JodeList containing only nodes that matched the filter criteria
    */
   public JodeList filter(final String nodeName) {
      return this.filter(j -> j.extend().getNodeName().equals(nodeName));
   }

   /**
    * Will return a JodeList containing only nodes that match the given node name
    * 
    * @param nodeName
    *           the name of the nodes we would like to filter by
    * @return JodeList containing only nodes that match the given node name
    */
   public JodeList filter(Predicate<Jode> filter) {
      return new JodeList(this.nodes.stream()
            .filter(n -> filter.test(new Jode(n))).collect(Collectors.toList()));
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

   public Jode first(Predicate<Jode> filter) {
      for (Jode j : this) {
         if (filter.test(j))
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
      return first(j -> j.n.equals(nodeName));
   }

   /**
    * Gets the node at index i
    * 
    * @param i
    *           the index to get the node at
    * @return the node at index i
    */
   public Jode get(int i) {
      return new Jode(nodes.get(i));
   }

   @Override
   public Iterator<Jode> iterator() {
      return new Iterator<Jode>() {
         private final Iterator<Node> backingList = JodeList.this.nodes.iterator();

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
      return join(delim, j -> j.n);
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
   public String join(String delim, Function<Jode, String> xformer) {
      StringBuilder bldr = new StringBuilder();
      for (Jode j : this) {
         bldr.append(xformer.apply(j)).append(delim);
      }
      return bldr.substring(0, bldr.length() - delim.length());
   }

   /**
    * Will return the only element of this list with the given node name. If there is more than one matching element, this will throw an {@link JinqException}.
    * 
    * @param nodeName
    *           the name of the node to return
    * @return the single node matching the given name
    */
   public Jode single(String nodeName) {
      JodeList resultList = this.filter(nodeName);
      if (resultList.getLength() != 1) {
         throw new JinqException("The call to 'single' " + nodeName + " did not return 1 result.  It returned " + resultList.getLength() + " items.");
      }
      return resultList.get(0);
   }

   /**
    * Will give you to single element of this list matching a given name. If there is more than one matching element, this will throw an {@link JinqException}.
    * 
    * @param filter
    *           the filter to apply to this list
    * @return the single node matching this filter
    */
   public Jode single(Predicate<Jode> filter) {
      JodeList lst = this.filter(filter);
      if (lst.getLength() != 1)
         throw new JinqException("The call to 'single' did not return 1 result.");
      return lst.get(0);
   }

   /**
    * Gives the number of top-level Jode elements in this list
    * 
    * @return the number of top-level Jode elements in this list
    */
   public int size() {
      return nodes.size();
   }

   /**
    * Sorts this list by Node name
    */
   public void sort() {
      sort((j1, j2) -> j1.name().compareTo(j2.name()));
   }

   /**
    * Sorts this list in place using the given Comparator
    */
   public void sort(final Comparator<Jode> comparator) {
      Comparator<Node> alteredComparator = 
            (n1, n2) -> comparator.compare(new Jode(n1), new Jode(n2));
      Collections.sort(nodes, alteredComparator);
   }

   /**
    * Will transform this JodeList into a List of the type specified in the XFormer
    * 
    * @param xform
    *           the method used to transform a Jode into the correct type
    * @return a List<T> of elements that were created from this JodeList
    */
   public <T> List<T> xform(Function<Jode, T> xform) {
      List<T> ret = new ArrayList<T>(this.size());
      // For each element in this Jode list, apply the transform
      for (Jode j : this) {
         ret.add(xform.apply(j));
      }
      return ret;
   }

   @Override
   public int getLength() {
      return size();
   }

   @Override
   public Node item(int index) {
      return this.nodes.get(index);
   }
}
