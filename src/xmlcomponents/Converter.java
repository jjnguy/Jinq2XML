package xmlcomponents;

/**
 * Generic interface for converting from one type to another
 * 
 * @author jjnguy
 * 
 * @param <T_From>
 *           the type to convert from
 * @param <T_To>
 *           the type to convert to
 */
public interface Converter<T_From, T_To> {
   /**
    * Converts the given value into the expected return type.
    * 
    * @param value
    *           the item to convert
    * @return the converted value
    */
   public T_To convert(T_From value);
   
}
