package examples.lolla;

import java.net.MalformedURLException;
import java.net.URL;

import xmlcomponents.Jocument;
import xmlcomponents.Jode;

public class Main {
	public static void main(String[] args) throws MalformedURLException {
		Jocument joc = Jocument
		        .load(new URL(
		                "http://api.dostuffmedia.com/events.xml?key=2beb11af18abf85a56d9152f54bd3b606feb10b2"));
		Jode found = joc.traverse("//events/event[1]/id");
		System.out.println(found.n + ": " + found.v);
	}
}
