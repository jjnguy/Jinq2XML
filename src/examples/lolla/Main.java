package examples.lolla;

import java.net.MalformedURLException;

import xmlcomponents.Action;
import xmlcomponents.Jocument;
import xmlcomponents.Jode;
import xmlcomponents.JodeFilter;

public class Main {
   public static void main(String[] args) throws MalformedURLException {
      Jocument joc = Jocument.parse("<root attr=\"harrp!\">" +
      		                           "<one>" +
      		                              "<two></two>" +
      		                           "</one>" +
      		                           "<two>" +
      		                              "<one>" +
      		                                 "<eight></eight>" +
      		                              "</one>" +
      		                           "</two>" +
      		                           "<two>" +
      		                              "<one2>" +
      		                                 "<eight>" +
      		                                    "<thee>" +
      		                                       "<one></one>" +
      		                                    "</thee>" +
      		                                 "</eight>" +
      		                              "</one2>" +
      		                           "</two>" +
      		                        "</root>");
      System.out.println();
      joc.children().search(new JodeFilter() {
         @Override
         public boolean accept(Jode j) {
            return j.n.equals("one");
         }
      }).each(new Action() {
         @Override
         public void act(Jode j) {
            System.out.println(j);
         }
      });
   }
}
