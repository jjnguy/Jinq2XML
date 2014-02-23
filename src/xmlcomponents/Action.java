package xmlcomponents;

/**
 * Interface for defining a single action on a {@link Jode}.
 * 
 * @author jjnguy
 * 
 */
@FunctionalInterface
public interface Action {
   /**
    * Applies this method to the supplied {@link Jode}.
    * 
    * @param j
    *           the Jode to act on
    */
   public void act(Jode j);
}
