package example.lolla;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import xmlcomponents.Jocument;
import xmlcomponents.Jode;
import xmlcomponents.Xformer;
import example.lolla.model.Event;

public class Main {
   private static final String apiUrl = "http://api.dostuffmedia.com/";
   private static final Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("webproxy.int.westgroup.com", 80));

   public static void main(String[] args) throws MalformedURLException, IOException {
      List<Event> evts = events();
      System.out.println(evts.size());
   }

   static Jocument fromUrl(String url) throws MalformedURLException, IOException {
      InputStream data = null;
      URL u = new URL(url);
      URLConnection conn = p == null ? u.openConnection() : u.openConnection(p);
      data = conn.getInputStream();
      return Jocument.load(data);
   }

   static List<Event> events() throws MalformedURLException, IOException {
      return fromUrl(apiUrl + "events.xml?key=2beb11af18abf85a56d9152f54bd3b606feb10b2").single("events")
            .children("event").xform(new Xformer<Event>() {
               @Override
               public Event xform(Jode evt) {
                  int id = Integer.parseInt(evt.single("id").v);
                  String title = evt.single("title").v;
                  String link = evt.single("link").v;
                  String date = evt.single("date").v;
                  return new Event(id, title, link, date);
               }
            });
   }
}
