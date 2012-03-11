package xmlcomponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import util.domain.jodeListTests.Root;

public class SimpleJodeTests {
   
   private Jode j;
   
   @Before
   public void setUp() throws Exception {
      j = Jocument.load("tests/test_xml_files/simple.xml", "root");
   }
   
   @Test
   public void testName() {
      assertThat(j.n, equalTo("root"));
      assertThat(j.name(), equalTo("root"));
   }
   
   @Test
   public void testAttributes() {
      List<Jattr> attributes = j.attributes();
      assertThat(attributes.size(), is(2));
   }
   
   @Test
   public void testNoAttributes() {
      List<Jattr> attributes = j.single("childList").attributes();
      assertThat(attributes.size(), is(0));
   }
   
   @Test
   public void testAttribute() {
      Jattr name = j.attribute("name");
      Jattr count = j.attribute("count");
      assertThat(name.name(), equalTo("name"));
      assertThat(name.value(), equalTo("root"));
      assertThat(count.name(), equalTo("count"));
      assertThat(count.value(), equalTo("1"));
   }
   
   @Test(expected = JinqException.class)
   public void testAttrThrowsException() {
      j.attribute("attr that doesn't exist");
      fail("Should have thrown an exception.");
   }
   
   @Test
   public void testAttribute_Polte_doesnt_Throw() {
      Jattr attr = j.attribute("attr that doesn't exist", true);
      assertThat(attr, nullValue());
   }
   
   @Test
   public void testHasAttribute() {
      assertTrue(j.hasAttribute("name"));
      assertFalse(j.hasAttribute("attr that doesn't exist"));
   }
   
   @Test
   public void testChildren() {
      JodeList children = j.children();
      assertThat(children.size(), is(6));
   }
   
   @Test
   public void testChildrenStringFilter() {
      JodeList children = j.children("childList");
      JodeList repeated_children = j.children("anotherNode");
      JodeList grandChildren = children.first().children("child");
      assertThat(children.size(), is(1));
      assertThat(grandChildren.size(), is(4));
      assertThat(repeated_children.size(), is(4));
   }
   
   @Test
   public void testChildrenJodeFilter() {
      JodeList all_children = j.children(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return true;
         }
      });
      JodeList no_children = j.children(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return false;
         }
      });
      JodeList some_children = j.children(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals("childList") || j.n.equals("anotherNode");
         }
      });
      assertThat(all_children.size(), is(6));
      assertThat(no_children.size(), is(0));
      assertThat(some_children.size(), is(5));
   }
   
   @Test
   public void testHasSingleChild() {
      assertTrue(j.hasSingleChild("childList"));
      assertFalse(j.hasSingleChild("anotherNode"));
   }
   
   @Test
   public void testHasMultipleChildren() {
      assertFalse(j.hasMultipleChildren("childList"));
      assertTrue(j.hasMultipleChildren("anotherNode"));
   }
   
   @Test
   public void testFirst() {
      Jode first = j.first();
      assertThat(first.n, equalTo("childList"));
   }
   
   @Test
   public void testFirstString() {
      Jode simplefirst = j.first("singleChild");
      Jode acutalTestfirst = j.first("anotherNode");
      assertThat(simplefirst.n, equalTo("singleChild"));
      assertThat(acutalTestfirst.attribute("first").value(), equalTo("true"));
   }
   
   @Test
   public void testFirstJodeFilter() {
      Jode acutalTestfirst = j.first(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals("anotherNode") && !j.attribute("first").value().equals("true");
         }
      });
      assertThat(acutalTestfirst.attribute("first").value(), equalTo("false_1"));
   }
   
   @Test
   public void testSingle() {
      Jode single = j.first("singleChild").single();
      assertThat(single.n, equalTo("grandChild"));
   }
   
   @Test(expected = JinqException.class)
   public void testSingle_ThrowsException() {
      j.single();
      fail("Should have thrown an exception.");
   }
   
   @Test
   public void testSingleString() {
      Jode single = j.single("singleChild");
      assertThat(single.n, equalTo("singleChild"));
   }
   
   @Test
   public void testSingleJodeFilter() {
      Jode single = j.single(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals("anotherNode") && j.attribute("first").value().equals("true");
         }
      });
      assertThat(single.n, equalTo("anotherNode"));
   }
   
   @Test
   public void testToObject() {
      Jode root = Jocument.load("tests/test_xml_files/xml_lists.xml", "root");
      Root result = root.toObject(Root.class);
      // TODO more testing around this.
      assertThat(result, equalTo(result));
   }
   
   @Test
   public void testValue() {
      String value = j.value();
      String expectedValue = "text0text1text2text3grandChildText";
      assertThat(value.replaceAll("\\s*", ""), equalTo(expectedValue));
   }
}
