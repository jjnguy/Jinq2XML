package xmlcomponents;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import xmlcomponents.complex.ExtendedNode;
import xmlcomponents.manipulation.xpath.XPath;
import xmlcomponents.output.JodeWriter;

/**
 * Class representing a Node from an XML document
 * 
 * @author Justin Nelson
 * 
 */
public class Jode {
   private ExtendedNode node;

   /**
    * Creates a new Jode from the given backing node
    * 
    * @param n
    *           the Node to use as a backing for this Jode
    */
   public Jode(Node n) {
      this(new ExtendedNode(n));
   }

   Jode(ExtendedNode node) {
      this.node = node;
      v = node.getTextContent();
      this.n = node.getNodeName();
   }

   /**
    * Convenience member for accessing the name of this node
    */
   public final String n;

   /**
    * Gets the name of this Jode
    * 
    * @return the name of this Jode
    */
   public final String name() {
      return node.getNodeName();
   }

   /**
    * Convenience member for accessing the text of this node
    */
   public final String v;

   /**
    * Will give you all of the attributes defined in this node
    * 
    * @return a list of the defined attributes
    */
   public List<Jattr> attributes() {
      List<Jattr> jattrs = new ArrayList<Jattr>();
      NamedNodeMap attributes = node.getAttributes();
      if (attributes == null)
         return jattrs;
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
    * Gives you all of the children of this node
    * 
    * @return a JodeList of this node's direct children
    */
   public JodeList children() {
      return new JodeList(node.children());
   }

   /**
    * Will give you all of the children of this node that match the given name
    * 
    * @param nodeName
    *           name of the nodes to return
    * @return every child of this node that matches the given name
    */
   public JodeList children(String nodeName) {
      return new JodeList(node.children(nodeName));
   }

   /**
    * Will give you all of the children of this node that match the given filter
    * 
    * @param filter
    *           the filter to apply to the children of this node
    * @return every child of this node that matches the given filter
    */
   public JodeList children(JodeFilter filter) {
      return new JodeList(node.children(filter));
   }

   /**
    * Uses an Xpath string to collect a set of nodes.
    * 
    * @param path
    *           the xpath to traverse
    * @return a list of matching nodes
    */
   public JodeList find(String path) {
      return new XPath(path).find(this);
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

   public Jode first(JodeFilter filter) {
      return children().first(filter);
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
      return new Jode(children.extend().item(0));
   }

   /**
    * Equivalent to calling children().single(nodeName)
    * 
    * @param nodeName
    *           the name of the node to return
    * @return gives the only child of this node
    */
   public Jode single(String nodeName) {
      return children().single(nodeName);
   }

   /**
    * Equivalent to calling children().single(filter)
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
    * Uses Xpath to traverse to the specified node.
    * 
    * @param path
    *           the path to travel to
    * @return the specified node
    */
   public Jode traverse(String path) {
      return new XPath(path).traverse(this);
   }

   /**
    * Gets the text content of thsi node
    * 
    * @return the text inside this node
    */
   public String value() {
      return node.getTextContent();
   }

   /**
    * Provided so people can use the full functionality of a Node if they want.
    * 
    * @return the internal ExtendedNode
    */
   public ExtendedNode extend() {
      return node;
   }
}
