package xmlcomponents;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import xmlcomponents.autoparse.AutoParser;
import xmlcomponents.manipulation.xpath.XPath;
import xmlcomponents.output.JodeWriter;

/**
 * Class representing a Node from an XML document
 * 
 * @author jjnguy
 * 
 */
public class Jode implements Comparable<Jode> {
   private Node node;
   
   /**
    * Creates a new Jode from the given backing node
    * 
    * @param n
    *           the Node to use as a backing for this Jode
    */
   public Jode(Node n) {
      this.node = n;
      this.v = node.getTextContent();
      this.n = node.getNodeName();
   }
   
   /**
    * Convenience member for accessing the name of this node
    */
   public final String n;
   
   /**
    * Convenience member for accessing the text of this node
    */
   public final String v;
   
   /**
    * Gets the name of this Jode
    * 
    * @return the name of this Jode
    */
   public final String name() {
      return node.getNodeName();
   }
   
   /**
    * Will give you all of the attributes defined in this node
    * 
    * @return a list of the defined attributes
    */
   public List<Jattr> attributes() {
      NamedNodeMap attributes = node.getAttributes();
      if (attributes == null)
         return new ArrayList<Jattr>();
      List<Jattr> jattrs = new ArrayList<Jattr>();
      for (int i = 0; i < attributes.getLength(); i++) {
         jattrs.add(new Jattr((Attr) attributes.item(i)));
      }
      return jattrs;
   }
   
   /**
    * Will give you the attribute in this node with the given name. Throws an exception if this attribute wasn't defined
    * 
    * @param name
    *           the name of the attribute to find
    * @return the attribute with the given name
    */
   public Jattr attribute(String name) {
      return attribute(name, false);
   }
   
   /**
    * Will give you the attribute in this node with the given name. Throws an exception or returns null if this
    * attribute wasn't defined
    * 
    * @param name
    *           the name of the attribute to find
    * @param polite
    *           whether or not to throw an exception or return null in the case of a missing attribute
    * @return the attribute with the given name
    */
   public Jattr attribute(String name, boolean polite) {
      for (Jattr a : attributes()) {
         if (a.name().equals(name))
            return a;
      }
      if (polite)
         return null;
      else
         throw new JinqException("This node didn't containg an attribute with the name: " + name);
   }
   
   /**
    * Returns whether or not a Jode has an attribute with the given name
    * 
    * @param name
    *           the name to look for
    * @return true if the attribute was found, false otherwise.
    */
   public boolean hasAttribute(String name) {
      // if attribute() returns a value, then this jode has that attribute
      return attribute(name, true) != null;
   }
   
   /**
    * Gives you all of the children of this node
    * 
    * @return a JodeList of this node's direct children
    */
   public JodeList children() {
      return new JodeList(node.getChildNodes());
   }
   
   /**
    * Will give you all of the children of this node that match the given name
    * 
    * @param nodeName
    *           name of the nodes to return
    * @return every child of this node that matches the given name
    */
   public JodeList children(String nodeName) {
      return this.children().filter(nodeName);
   }
   
   /**
    * Will give you all of the children of this node that match the given filter
    * 
    * @param filter
    *           the filter to apply to the children of this node
    * @return every child of this node that matches the given filter
    */
   public JodeList children(JodeFilter filter) {
      return this.children().filter(filter);
   }
   
   /**
    * Returns whether or not this node has one child with the given name
    * 
    * @param childName
    *           the name of the child to look for
    * @return true if only one child was found, false otherwise.
    */
   public boolean hasSingleChild(String childName) {
      return children(childName).size() == 1;
   }
   
   /**
    * Returns whether or not this node has more than one child with the given name
    * 
    * @param childName
    *           the name of the child to look for
    * @return true if this node has 2 or more children with the given name, false otherwise.
    */
   public boolean hasMultipleChildren(String childName) {
      return children(childName).size() > 1;
   }
   
   /**
    * Determines whether or not this node is a whitespace node or not.
    * 
    * @return true if this Node is simply whitespace, or false otherwise.
    */
   public boolean isWhiteSpace() {
      return node.getNodeType() == Node.TEXT_NODE && node.getTextContent().trim().length() == 0;
   }
   
   /**
    * Finds the first child of this Jode
    * 
    * @return the first child of this Jode, or null if this element has no children
    */
   public Jode first() {
      return children().first();
   }
   
   /**
    * Finds the first child node that matches the given node name
    * 
    * @param nodeName
    *           the node name to match
    * @return the first child node to match the given node name, or null otherwise
    */
   public Jode first(String nodeName) {
      return children().first(nodeName);
   }
   
   /**
    * Finds the first child node that matches the given filter
    * 
    * @param filter
    *           the filter to test all children against
    * @return the first child node to match the given node name, or null otherwise
    */
   public Jode first(JodeFilter filter) {
      return children().first(filter);
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
      if (filter.accept(this)) {
         ret.add(this.extend());
      }
      for (Jode j : this.children()) {
         JodeList subResult = j.search(filter);
         for (Jode subJ : subResult) {
            ret.add(subJ.extend());
         }
      }
      return new JodeList(ret);
   }
   
   /**
    * If this node has one child, this will give you that child. Otherwise it will throw a new exception.
    * 
    * @return the only child of this Jode
    */
   public Jode single() {
      JodeList children = children();
      // Check to make sure we actually only have 1 child
      if (children.size() != 1)
         throw new JinqException("Call to single() did not return a single result");
      return this.children().first();
   }
   
   /**
    * Equivalent to calling children().single(String). In fact, that's exactly what this method does. This returns the
    * only item that matches the given node name. Will throw an exception if more than one or no item matches.
    * 
    * @param nodeName
    *           the name of the node to return
    * @return gives the only child of this node
    */
   public Jode single(String nodeName) {
      return children().single(nodeName);
   }
   
   /**
    * Equivalent to calling children().single(JodeFilter). In fact, that's exactly what this method does. This returns the
    * only item that matches the given filter. Will throw an exception if more than one or no item matches.
    * 
    * @param filter
    *           the filter to apply to the children of this node
    * @return the only child of this node to match the given filter
    */
   public Jode single(JodeFilter filter) {
      return children().single(filter);
   }
   
   @Override
   public String toString() {
      return new JodeWriter(this).toString();
   }
   
   /**
    * This parses this xml node (and children) into an instance of the given class. In order for this to work, the given
    * type and all types of it's member variables must have a no argument constructor. All types must also only have
    * fields that are primitives, strings, or (array)lists. I'm sure there are other assumptions I made that I'm leaving
    * out.
    * 
    * This method is probably not quite ready for prime time.
    * 
    * @param <T>
    *           the type to parse this Jode into
    * @param clazz
    *           the type to parse this Jode into
    * @return the object represented by this xml
    */
   public <T> T toObject(Class<T> clazz) {
      return AutoParser.parse(this, clazz);
   }
   
   /**
    * Gets the text content of this node
    * 
    * @return the text inside this node
    */
   public String value() {
      return node.getTextContent();
   }
   
   /**
    * Uses an Xpath string to collect a set of nodes.
    * 
    * @param path
    *           the xpath to search for
    * @return a list of matching nodes
    */
   public JodeList xPathFind(String path) {
      return new XPath(path).find(this);
   }
   
   /**
    * Uses Xpath to traverse to the specified node.
    * 
    * @param path
    *           the path to travel to
    * @return the specified node
    */
   public Jode xPathTraverse(String path) {
      return new XPath(path).traverse(this);
   }
   
   /**
    * Provided so people can use the full functionality of a Node if they want.
    * 
    * @return the internal Node
    */
   public Node extend() {
      return node;
   }
   
   @Override
   public int compareTo(Jode other) {
      return this.n.compareTo(other.n);
   }
}
