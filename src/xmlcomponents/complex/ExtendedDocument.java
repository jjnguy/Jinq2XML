package xmlcomponents.complex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import xmlcomponents.JinqException;

public class ExtendedDocument extends ExtendedNode implements Document {
	private Document doc;

	public ExtendedDocument(Document doc) {
		super(doc);
		this.doc = doc;
	}

	public static ExtendedDocument load(InputStream in) {
		try {
			return new ExtendedDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder()
			        .parse(in));
		} catch (Exception e) {
			throw new JinqException(e);
		}
	}

	public static ExtendedDocument load(String fileLocation) {
		try {
			return ExtendedDocument.load(new FileInputStream(new File(fileLocation)));
		} catch (FileNotFoundException e) {
			throw new JinqException(e);
		}
	}

	// /////////////////////
	// Boring stuff Below//
	// ///////////////////

	@Override
	public Node adoptNode(Node source) throws DOMException {
		return doc.adoptNode(source);
	}

	@Override
	public Attr createAttribute(String name) throws DOMException {
		return doc.createAttribute(name);
	}

	@Override
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		return doc.createAttributeNS(namespaceURI, qualifiedName);
	}

	@Override
	public CDATASection createCDATASection(String data) throws DOMException {
		return doc.createCDATASection(data);
	}

	@Override
	public Comment createComment(String data) {
		return doc.createComment(data);
	}

	@Override
	public DocumentFragment createDocumentFragment() {
		return doc.createDocumentFragment();
	}

	@Override
	public Element createElement(String tagName) throws DOMException {
		return doc.createElement(tagName);
	}

	@Override
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		return doc.createElementNS(namespaceURI, qualifiedName);
	}

	@Override
	public EntityReference createEntityReference(String name) throws DOMException {
		return doc.createEntityReference(name);
	}

	@Override
	public ProcessingInstruction createProcessingInstruction(String target, String data)
	        throws DOMException {
		return doc.createProcessingInstruction(target, data);
	}

	@Override
	public Text createTextNode(String data) {
		return doc.createTextNode(data);
	}

	@Override
	public DocumentType getDoctype() {
		return doc.getDoctype();
	}

	@Override
	public Element getDocumentElement() {
		return doc.getDocumentElement();
	}

	@Override
	public String getDocumentURI() {
		return doc.getDocumentURI();
	}

	@Override
	public DOMConfiguration getDomConfig() {
		return doc.getDomConfig();
	}

	@Override
	public Element getElementById(String elementId) {
		return doc.getElementById(elementId);
	}

	@Override
	public NodeList getElementsByTagName(String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		return doc.getElementsByTagNameNS(namespaceURI, localName);
	}

	@Override
	public DOMImplementation getImplementation() {
		return doc.getImplementation();
	}

	@Override
	public String getInputEncoding() {
		return doc.getInputEncoding();
	}

	@Override
	public boolean getStrictErrorChecking() {
		return doc.getStrictErrorChecking();
	}

	@Override
	public String getXmlEncoding() {
		return doc.getXmlEncoding();
	}

	@Override
	public boolean getXmlStandalone() {
		return doc.getXmlStandalone();
	}

	@Override
	public String getXmlVersion() {
		return doc.getXmlVersion();
	}

	@Override
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		return doc.importNode(importedNode, deep);
	}

	@Override
	public void normalizeDocument() {
		doc.normalizeDocument();
	}

	@Override
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		return doc.renameNode(n, namespaceURI, qualifiedName);
	}

	@Override
	public void setDocumentURI(String documentURI) {
		doc.setDocumentURI(documentURI);
	}

	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		doc.setStrictErrorChecking(strictErrorChecking);
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		doc.setXmlStandalone(xmlStandalone);
	}

	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		doc.setXmlVersion(xmlVersion);
	}

}
