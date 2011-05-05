package xmlcomponents;

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

    public JodeList children(JodeFilter filter){
        return new JodeList(n.children(filter));
    }
    
    public JodeList children(String nodeName){
        return new JodeList(n.children(nodeName));
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
