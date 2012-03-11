package xmlcomponents.manipulation.xpath;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xmlcomponents.JinqException;
import xmlcomponents.Jode;
import xmlcomponents.JodeList;

/**
 * Class for making using XPath in Java easy.<br />
 * Usually people will not use this class directly. The methods on a Jode should be used instead.
 * 
 * @author Justin Nelson
 */
public class XPath {
   private static final XPathFactory xFact = XPathFactory.newInstance();
   private XPathExpression xpres;
   
   /**
    * Create a new XPath from the given path
    * 
    * @param path
    *           the path to parse into an XPath expression
    */
   public XPath(String path) {
      try {
         xpres = xFact.newXPath().compile(path);
      } catch (XPathExpressionException e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Will traverse from the given start node to the location pointed to in the XPath expression.
    * 
    * @param start
    *           the Jode to start at
    * @return the ending destination Jode
    */
   public Jode traverse(Jode start) {
      try {
         return new Jode((Node) xpres.evaluate(start.extend(), XPathConstants.NODE));
      } catch (XPathExpressionException e) {
         throw new JinqException(e);
      }
   }
   
   /**
    * Given a starting node, will match the xpath expression against multiple nodes
    * 
    * @param start
    *           the Jode to start at
    * @return the List of nodes that were found in the xpath search
    */
   public JodeList find(Jode start) {
      try {
         return new JodeList((NodeList) xpres.evaluate(start.extend(), XPathConstants.NODESET));
      } catch (XPathExpressionException e) {
         throw new JinqException(e);
      }
   }
}
