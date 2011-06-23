package xmlcomponents;
import java.io.InputStream;

import org.w3c.dom.Document;

import xmlcomponents.complex.ExtendedDocument;

public class Jocument extends Jode {

	private ExtendedDocument d;

	public Jocument(Document d) {
		this(new ExtendedDocument(d));
	}

	public Jocument(ExtendedDocument d) {
		super(d);
		this.d = d;
	}

	public ExtendedDocument extend() {
		return d;
	}

	public static Jocument load(String fileLocation) {
		return new Jocument(ExtendedDocument.load(fileLocation));
	}

	public static Jocument load(InputStream in) {
		return new Jocument(ExtendedDocument.load(in));
	}
}
