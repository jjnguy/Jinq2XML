package example.lolla.model;

public class Event {
   public final int id;
   public final String title;
   public final String link;
   public final String date;

   public Event(int id, String title, String link, String date) {
      this.id = id;
      this.title = title;
      this.date = date;
      this.link = link;
   }

   @Override
   public String toString() {
      return "Event: " + title;
   }
}
