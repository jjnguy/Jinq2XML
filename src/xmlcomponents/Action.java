package xmlcomponents;

/**
 * Interface for defining a single action on a Jode.
 * 
 * @author jjnguy
 * 
 */
public interface Action {
	/**
	 * Applies this method to the supplied Jode.
	 * 
	 * @param j the Jode to act on
	 */
	public void act(Jode j);
}
