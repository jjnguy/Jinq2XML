package xmlcomponents;

/**
 * Interface for defining a single action on a Jode. Helpful if you need an index while you iterate.
 * 
 * @author jjnguy
 * 
 */
public interface ActionWithIndex {
   /**
    * Applies this method to the supplied Jode.
    * 
    * @param j
    *           the Jode to act on
    * @param index
    *           the index of the current item being acted on
    */
   public void act(Jode j, int index);
}
