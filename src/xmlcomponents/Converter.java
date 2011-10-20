package xmlcomponents;

public interface Converter<T, S> {

   public S convert(T value);

}
