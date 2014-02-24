package examples;

import xmlcomponents.Jocument;
import xmlcomponents.Jode;
import xmlcomponents.JodeList;

public class LittleMoreComplicated1_IterateNonFiction {
   public static void main(String[] args) {
      // Load the file
      Jode root = Jocument.load("tests/test_xml_files/little_more_complicated.xml", "Library");
      // navigate to the `NonFiction` items
      // Note the chained calls
      JodeList nonFiction = root.single("Genres").single("NonFiction").children();
      
      // Iterate the results
      for (Jode item : nonFiction) {
         System.out.println("Item - " + item.n);
      }
      
      System.out.println();
      
      // alternate iteration method
      nonFiction.each(j -> System.out.println("Item - " + j.n));
   }
}
