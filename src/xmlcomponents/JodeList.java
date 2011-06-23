package xmlcomponents;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NodeList;

import xmlcomponents.complex.ExtendedNode;
import xmlcomponents.complex.ExtendedNodeList;

public class JodeList implements Iterable<Jode> {

	private ExtendedNodeList l;

	public JodeList(NodeList l) {
		this(new ExtendedNodeList(l));
	}

	public JodeList(ExtendedNodeList l) {
		this.l = l;
	}

	public JodeList filter(JodeFilter filter) {
		return new JodeList(l.filter(filter));
	}

	public JodeList filter(String nodeName) {
		return new JodeList(l.filter(nodeName));
	}

	public Jode single(JodeFilter filter) {
		return new Jode(l.single(filter));
	}

	public Jode single(String nodeName) {
		return new Jode(l.single(nodeName));
	}

	public int size() {
		return l.getLength();
	}

	public <T> List<T> xform(Xformer<T> xform) {
		List<T> ret = new ArrayList<T>();
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
