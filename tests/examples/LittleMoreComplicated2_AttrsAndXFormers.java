package examples;

import java.util.List;

import com.jjnguy.xmlcomponents.Jocument;
import com.jjnguy.xmlcomponents.Jode;
import com.jjnguy.xmlcomponents.JodeList;

public class LittleMoreComplicated2_AttrsAndXFormers {
   static class Item {
      public String type;
      public String title;
      public String description;
   }
   
   public static void main(String[] args) {
      // Load the file
      Jode root = Jocument.load("tests/test_xml_files/little_more_complicated.xml", "Library");
      // navigate to the `NonFiction` items
      // Note the chained calls
      JodeList nonFiction = root.single("Genres").single("NonFiction").children();
      
      // apply an XFormer on each node. Produces a typed List
      List<Item> nonFictionItems = nonFiction.xform(j -> {
         Item result = new Item();
         result.type = j.n;
         // grab attribute by name, and use its value
         result.title = j.attribute("title").v;
         result.description = j.hasAttribute("issue") ? j.attribute("issue").v : j.v;
         return result;
      });
      for (Item item : nonFictionItems) {
         System.out.println(String.format("Item: type: %s, title: %s, description: %s", item.type, item.title,
               item.description));
      }
   }
}
