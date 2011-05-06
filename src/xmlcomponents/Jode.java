package xmlcomponents;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

import xmlcomponents.complex.ExtendedNode;

public class Jode {
	private ExtendedNode n;

	public Jode(Node n) {
		this(new ExtendedNode(n));
	}

	public Jode(ExtendedNode n) {
		this.n = n;
	}

	public JodeList children() {
		return new JodeList(n.children());
	}

	public JodeList children(JodeFilter filter) {
		return new JodeList(n.children(filter));
	}

	public JodeList children(String nodeName) {
		return new JodeList(n.children(nodeName));
	}

	public Jattr attribute(String name) {
		for (Jattr a : attributes()) {
			if (a.name().equals(name))
				return a;
		}
		return null;
	}

	public List<Jattr> attributes() {
		List<Jattr> attributes = new ArrayList<Jattr>();
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			attributes.add(new Jattr((Attr) n.getAttributes().item(i)));
		}
		return attributes;
	}

	/**
	 * Provided so people can use the full functionality of a Node if they want.
	 * 
	 * @return the internal ExtendedNode
	 */
	public ExtendedNode extend() {
		return n;
	}
}
