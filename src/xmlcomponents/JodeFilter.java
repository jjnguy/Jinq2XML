package xmlcomponents;

import org.w3c.dom.Node;

/**
 * Interface for defining criteria for filtering a list of nodes
 * 
 * @author Justin Nelson
 * 
 */
@FunctionalInterface
public interface JodeFilter {
   /**
    * Will decide whether or not this {@link Jode} passes the filter
    * 
    * @param j
    *           the Jode to evaluate
    * @return <code>true</code> if this node passes the filter, and <code>false</code> if it does not
    */
   public boolean accept(Jode j);
   
   public default boolean accept(Node n){
      return accept(new Jode(n));
   }
}
