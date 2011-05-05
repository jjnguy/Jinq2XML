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

    public ExtendedAttr extend() {
        return a;
    }
}
