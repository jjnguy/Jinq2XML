package xmlcomponents;

import org.w3c.dom.Attr;

import xmlcomponents.complex.ExtendedAttr;

public class Jattr {
    private ExtendedAttr a;

    public Jattr(Attr a) {
        this(new ExtendedAttr(a));
    }

    public Jattr(ExtendedAttr a) {
        this.a = a;
    }

    public String name() {
        return a.getNodeName();
    }

    public String value() {
        return a.getNodeValue();
    }

    public Jode parent(){
        return new Jode(a.getOwnerElement());
    }
    
    public ExtendedAttr extend() {
        return a;
    }
}
