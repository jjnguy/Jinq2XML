package xmlcomponents.manipulation;

import xmlcomponents.Jode;

/**
 * Interface for enabling Jodes to be transformed into Plain old Java objects
 * 
 * @author Justin Nelson
 * 
 * @param <T>
 *           the type that the given Jode will be transformed into
 */
@FunctionalInterface
public interface Xformer<T> {
   /**
    * Given a Jode, will create an object of type T
    * 
    * @param j
    *           the source Jode
    * @return an object of type T derived from the given Jode
    */
   public T xform(Jode j);
}
