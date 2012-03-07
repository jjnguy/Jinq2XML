package xmlcomponents;

/**
 * Interface for defining criteria for filtering a list of nodes
 * 
 * @author Justin Nelson
 * 
 */
public interface JodeFilter {
   /**
    * Will decide whether or not this {@link Jode} passes the filter
    * 
    * @param j
    *           the Jode to evaluate
    * @return <code>true</code> if this node passes the filter, and <code>false</code> if it does not
    */
   public boolean accept(Jode j);
   
}
