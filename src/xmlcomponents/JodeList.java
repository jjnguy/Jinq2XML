package xmlcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.NodeList;

public class JodeList implements NodeList, Iterable<Jode> {

    private List<Jode> jodes;

    public JodeList(NodeList list) {
        jodes = new ArrayList<Jode>(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            jodes.add(new Jode(list.item(i)));
        }
    }

    private JodeList(List<Jode> jodes) {
        this.jodes = jodes;
    }

    public Jode single(String nodeName) {
        JodeList lst = this.where(nodeName);
        if (lst.getLength() != 1)
            throw new JinqException("The call to 'single' did not return 1 result.");
        return lst.item(0);
    }

    public Jode single(JodeFilter filter) {
        JodeList lst = this.where(filter);
        if (lst.getLength() != 1)
            throw new JinqException("The call to 'single' did not return 1 result.");
        return lst.item(0);
    }

    public JodeList where(final String nodeName) {
        return this.where(new JodeFilter() {
            @Override
            public boolean accept(Jode j) {
                return j.getNodeName().equals(nodeName);
            }
        });
    }

    public JodeList where(JodeFilter filter) {
        List<Jode> result = new ArrayList<Jode>();
        for (Jode j : this) {
            if (filter.accept(j)) {
                result.add(j);
            }
        }
        return new JodeList(result);
    }

    @Override
    public int getLength() {
        return jodes.size();
    }

    @Override
    public Jode item(int arg0) {
        return jodes.get(arg0);
    }

    @Override
    public Iterator<Jode> iterator() {
        return jodes.iterator();
    }
}
