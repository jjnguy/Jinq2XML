package xmlcomponents.autoparse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlProperty {

    public boolean optional() default false;
    
    public String valueName() default "";

    public XmlPropertyType type() default XmlPropertyType.ANY_ELEMENT;

    public static enum XmlPropertyType {
        TEXT_NODE, ANY_ELEMENT
    }
}
