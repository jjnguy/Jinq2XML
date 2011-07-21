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

public class XPath {
	private static final XPathFactory xFact = XPathFactory.newInstance();
	private String path;
	private XPathExpression xpres;

	public XPath(String path) {
		try {
			this.path = path;
			xpres = xFact.newXPath().compile(path);
		} catch (XPathExpressionException e) {
			throw new JinqException(e);
		}
	}

	public Jode traverse(Jode start) {
		try {
			return new Jode((Node) xpres.evaluate(start.extend(), XPathConstants.NODE));
		} catch (XPathExpressionException e) {
			throw new JinqException(e);
		}
	}

	public JodeList find(Jode start) {
		try {
			return new JodeList((NodeList) xpres.evaluate(start.extend(), XPathConstants.NODESET));
		} catch (XPathExpressionException e) {
			throw new JinqException(e);
		}
	}
}
