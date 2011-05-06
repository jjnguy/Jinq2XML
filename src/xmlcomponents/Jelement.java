package xmlcomponents;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import xmlcomponents.complex.ExtendedElement;

public class Jelement extends Jode {
    private ExtendedElement e;

    public Jelement(ExtendedElement e) {
        super(e);
        this.e = e;
    }

    public Jelement(Element e) {
        this(new ExtendedElement(e));
    }

    public Jattr attribute(String name) {
        return new Jattr(e.getAttributeNode(name));
    }

    public List<Jattr> attributes() {
        List<Jattr> attributes = new ArrayList<Jattr>();
        for (int i = 0; i < e.getAttributes().getLength(); i++) {
            attributes.add(new Jattr((Attr) e.getAttributes().item(i)));
        }
        return attributes;
    }
}
