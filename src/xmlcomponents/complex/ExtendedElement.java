package xmlcomponents.complex;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public class ExtendedElement extends ExtendedNode implements Element {
    private Element e;

    public ExtendedElement(Element e) {
        super(e);
        this.e = e;
    }

    @Override
    public String getAttribute(String name) {
        return e.getAttribute(name);
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        return e.getAttributeNS(namespaceURI, localName);
    }

    @Override
    public Attr getAttributeNode(String name) {
        return getAttributeNode(name);
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        return getAttributeNodeNS(namespaceURI, localName);
    }

    @Override
    public NodeList getElementsByTagName(String name) {
        return getElementsByTagName(name);
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
            throws DOMException {
        return getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return e.getSchemaTypeInfo();
    }

    @Override
    public String getTagName() {
        return e.getTagName();
    }

    @Override
    public boolean hasAttribute(String name) {
        return e.hasAttribute(name);
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        return e.hasAttributeNS(namespaceURI, localName);
    }

    @Override
    public void removeAttribute(String name) throws DOMException {
        e.removeAttribute(name);
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        e.removeAttributeNS(namespaceURI, localName);
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        return e.removeAttributeNode(oldAttr);
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException {
        e.setAttribute(name, value);
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
            throws DOMException {
        e.setAttributeNS(namespaceURI, qualifiedName, value);
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        return e.setAttributeNode(newAttr);
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        return e.setAttributeNodeNS(newAttr);
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException {
        e.setIdAttribute(name, isId);
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId)
            throws DOMException {
        e.setIdAttributeNS(namespaceURI, localName, isId);
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        e.setIdAttributeNode(idAttr, isId);
    }
}
