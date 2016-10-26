package com.jjnguy.xmlcomponents;

import org.junit.Before;
import org.junit.Test;

public class JodeListStreamTests {

   private JodeList list;
   
   @Before
   public void setUp() throws Exception {
      Jode doc = Jocument.load("tests/test_xml_files/xml_lists.xml", "root");
      list = doc.single().children();
   }
   
   @Test
   public void testStream() {
      throw new UnsupportedOperationException();
   }
}
