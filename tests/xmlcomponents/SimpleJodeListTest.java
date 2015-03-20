package xmlcomponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

public class SimpleJodeListTest {
   
   private JodeList list;
   
   @Before
   public void setUp() throws Exception {
      Jode doc = Jocument.load("tests/test_xml_files/xml_lists.xml", "root");
      list = doc.single().children();
   }
   
   @Test
   public void testEachAction() {
      final List<Jode> visited = new ArrayList<Jode>();
      list.each(j -> visited.add(j));
      assertThat(visited.size(), equalTo(list.size()));
   }
   
   @Test
   public void testEachActionWithIndex() {
      final boolean[] visited = new boolean[list.size()];
      list.each((j, index) -> visited[index] = true);
      for (boolean b : visited) {
         assertTrue(b);
      }
   }
   
   @Test
   public void testFilterJodeFilter() {
      JodeList filtered = list.filter(j -> {
         return !j.v.trim().equals("");
      });
      assertThat(filtered.size(), equalTo(3));
   }
   
   @Test
   public void testFilterString() {
      JodeList filtered = list.filter("item");
      assertThat(filtered.size(), equalTo(7));
   }
   
   @Test
   public void testFirst() {
      Jode first = list.first();
      assertThat(first.attribute("first").value(), equalTo("true"));
   }
   
   @Test
   public void testFirst_noFirst() {
      Jode first = list.first((j) -> false);
      assertThat(first, nullValue());
      first = list.filter("blah").first();
      assertThat(first, nullValue());
   }
   
   @Test
   public void testFirstJodeFilter() {
      Jode first = list.first(j -> !j.v.trim().equals(""));
      assertThat(first.attribute("first").value(), equalTo("withText"));
   }
   
   @Test
   public void testFirstString() {
      Jode first = list.first("anotherItem");
      assertThat(first.attribute("first").value(), equalTo("true_2"));
   }
   
   @Test
   public void testGet() {
      Jode first = list.get(0);
      Jode third = list.get(3);
      assertThat(first.attribute("first").value(), equalTo("true"));
      assertThat(third.attribute("idx").value(), equalTo("3"));
   }
   
   @Test
   public void testItem() {
      JodeList filtered = list.distinct();
      Node zero_1 = filtered.item(0);
      Jode zero_2 = filtered.get(0);
      assertTrue(zero_1.getNodeName().equals(zero_2.n));
   }
   
   public void testDistinct() {
      JodeList distinct  = list.distinct();
      assertThat(distinct.size(), equalTo(3));
   }
   
   @Test(expected = UnsupportedOperationException.class)
   public void testIterator_throwsExceptionOnRemove() {
      Iterator<Jode> itr = list.iterator();
      itr.next();
      itr.remove();
   }
   
   @Test
   public void testJoinString() {
      String result = list.join(", ");
      String expected = "item, item, item, item, item, item, item, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, singleItem";
      assertThat(result, equalTo(expected));
   }
   
   @Test
   public void testJoinStringXformerOfString() {
      String result = list.join(", ", j -> j.n);
      String expected = "item, item, item, item, item, item, item, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, anotherItem, singleItem";
      assertThat(result, equalTo(expected));
   }
   
   @Test
   public void testSingleString() {
      Jode result = list.single("singleItem");
      assertThat(result.n, equalTo("singleItem"));
   }
   
   @Test
   public void testSingleJodeFilter() {
      Jode result = list.single(j -> {
         return j.hasAttribute("first") && j.attribute("first").value().equals("true");
      });
      assertThat(result.n, equalTo("item"));
      assertThat(result.attribute("first").value(), equalTo("true"));
   }
   
   @Test(expected = JinqException.class)
   public void testSingleNameThrowsException() {
      list.single("item");
   }
   
   @Test(expected = JinqException.class)
   public void testSingleFilterThrowsException() {
      list.single(j -> true);
   }
   
   @Test
   public void testSize() {
      assertThat(list.size(), is(15));
   }
   
   @Test
   public void testSortWithCustom() {
      list.sort((j1, j2) -> {
         if (!j1.n.equals(j2.n)) {
            return j1.n.compareTo(j2.n);
         }
         return j1.attributes().size() - j2.attributes().size();
      });
      Jode prev = null;
      for (Jode current : list) {
         if (prev != null)
            assertTrue(prev.compareTo(current) >= 0);
      }
   }
   
   @Test
   public void testSort() {
      list.sort();
      Jode prev = null;
      for (Jode current : list) {
         if (prev != null)
            assertTrue(prev.compareTo(current) >= 0);
      }
   }
   
   @Test
   public void testXform_xformer() {
      List<String> result = list.xform(j -> j.n);
      for (int i = 0; i < result.size(); i++) {
         assertThat(result.get(i), equalTo(list.get(i).n));
      }
   }
}
