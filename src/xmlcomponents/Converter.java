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
   
   public T_To convert(T_From value);
   
}
