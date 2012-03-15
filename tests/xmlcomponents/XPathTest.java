package xmlcomponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import xmlcomponents.manipulation.xpath.XPath;

public class XPathTest {
   
   private Jode doc;
   private XPath xpath;
   
   @Before
   public void setUp() throws Exception {
      doc = Jocument.load("tests/test_xml_files/simple.xml", "root");
      xpath = new XPath("//childList");
   }
   
   @Test
   public void testXPath_falidDoesntBlowUp() {
      // essentially tested by setup
   }
   
   @Test
   public void testTraverse() {
      Jode result = xpath.traverse(doc);
      assertThat(result.n, equalTo("childList"));
   }
   
   @Test
   public void testFind() {
      JodeList result = xpath.find(doc);
      assertThat(result.size(), equalTo(doc.children("childList").size()));
   }
}
