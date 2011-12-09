package xmlcomponents.autoparse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlProperty {

    public XmlPropertyType type();

    public static enum XmlPropertyType {
        TEXT_NODE
    }
}
