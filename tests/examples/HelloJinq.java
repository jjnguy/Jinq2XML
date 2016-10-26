package examples;

import com.jjnguy.xmlcomponents.Jocument;
import com.jjnguy.xmlcomponents.Jode;

public class HelloJinq {
   public static void main(String[] args) {
      // load the file
      Jode root = Jocument.load("tests/test_xml_files/hello.xml", "root");
      // grab the hello node
      Jode hello = root.single("Hello");
      // print the node's name and value
      System.out.println(hello.n + " " + hello.v);
   }
}
