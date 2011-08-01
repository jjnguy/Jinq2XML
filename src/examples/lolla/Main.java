package examples.lolla;

import java.net.MalformedURLException;
import java.net.URL;

import xmlcomponents.Jocument;
import xmlcomponents.Jode;
import xmlcomponents.output.JodeWriter;

public class Main {
   public static void main(String[] args) throws MalformedURLException {
      Jocument joc = Jocument.parse("<root attr=\"harrp!\"><one></one><two></two></root>");
      System.out.println(joc);
      System.out.println(joc.first());
   }
}
