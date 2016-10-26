package com.jjnguy.xmlcomponents;

/**
 * Interface for defining equality between two {@link Jode}
 * 
 * @author jjnguy
 */
@FunctionalInterface
public interface JodeEqualityComparer {
   /**
    * Returns whether or not two Jodes are equal
    * 
    * @param _1
    *           the first Jode
    * @param _2
    *           the second Jode
    * @return true if both Jodes are considered equal. False otherwise.
    */
   public boolean equal(Jode _1, Jode _2);
   
}
