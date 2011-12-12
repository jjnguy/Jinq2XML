package xmlcomponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SimpleJattrTests {
    private Jode j;
    private Jattr name;
    private Jattr count;

    @Before
    public void setUp() throws Exception {
        j = Jocument.load("tests/test_xml_files/simple.xml", "root");
        name = j.attribute("name");
        count = j.attribute("count");
    }

    @Test
    public void testName() {
        assertThat(name.name(), equalTo("name"));
        assertThat(count.name(), equalTo("count"));
    }

    @Test
    public void testValue() {
        assertThat(name.value(), equalTo("root"));
        assertThat(count.value(), equalTo("1"));
    }

    @Test
    public void testValueConverterOfStringT() {
        assertThat(count.value(new Converter<String, Integer>() {
            @Override
            public Integer convert(String value) {
                return Integer.parseInt(value);
            }
        }), is(1));
    }

    @Test
    public void testParent() {
        assertThat(name.parent().n, equalTo(j.n));
    }
}
