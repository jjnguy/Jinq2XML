package xmlcomponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class JodeSearchTests {
   
   private Jode root;
   
   @Before
   public void setUp() {
      root = Jocument.load("tests/test_xml_files/nested_elements.xml", "root");
   }
   
   @Test
   public void testSearchName() {
      JodeList result = root.search("searchTarget");
      assertThat(result.size(), equalTo(7));
   }
}
