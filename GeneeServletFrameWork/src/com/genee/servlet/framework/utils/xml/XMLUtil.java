package com.genee.servlet.framework.utils.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {
	
	private File xmlFile = null;
	private InputStream inputStream = null;
	private Element root = null;
	private ArrayList<XMLResult> xmlResult = new ArrayList<XMLResult>();
	private Document doc = null;
	private static int XPATH_TAG_MODE = 0;
	private static int XPATH_NOTAG_MODE = 1;

	public XMLUtil(File file) {
		if (file == null) {
			System.out.println("The input param file is null.");
			return;
		}
		xmlFile = file;
	}

	public XMLUtil(String filename) {
		try {
			xmlFile = new File(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XMLUtil(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private Document getDoc() {
		if (doc != null) {
			return doc;
		}

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			if (xmlFile != null)
				doc = builder.parse(xmlFile);
			else if (inputStream != null)
				doc = builder.parse(inputStream);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Element getRoot() {
		if (root != null) {
			return root;
		}
		if (getDoc() == null) {
			return null;
		}
		root = getDoc().getDocumentElement();
		return root;
	}

	private String findXpath(Element element, int mode) {
		Node parent = null;
		int index = 0;
		String tagName = element.getTagName();
		parent = element.getParentNode();
		if (parent != null && parent instanceof Element) {
			for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
				Node node = parent.getChildNodes().item(i);
				if (node instanceof Element) {
					if (mode == XMLUtil.XPATH_NOTAG_MODE) {
						index++;
					}
					Element one = (Element) node;
					if (one.getTagName().equals(tagName)) {
						if (one.equals(element)) {
							break;
						}
						if (mode == XMLUtil.XPATH_TAG_MODE) {
							index++;
						}
					}
				}
			}

			String tmp = "";
			if (mode == XMLUtil.XPATH_NOTAG_MODE) {
				tagName = "";
				index--;

				tmp = "(" + index + ")";
			} else {
				tmp = tagName + (index > 0 ? "(" + index + ")" : "");
			}

			return findXpath((Element) parent, mode) + "\\" + tmp;
		} else {
			return tagName;
		}
	}

	public Element getElement(String xpath) {
		String[] items = xpath.split("\\\\");
		Element root = getRoot();

		if (root == null) {
			return null;
		}

		for (int i = 0; i < items.length; i++) {
			String item = items[i].trim();
			int index = 0;
			int counter = 0;
			boolean found = false;

			try {
				int p1 = item.lastIndexOf("(");
				int p2 = item.lastIndexOf(")");

				index = Integer.valueOf(item.substring(p1 + 1, p2));
				item = item.substring(0, p1).trim();
				System.out.println(item.substring(p1, p2));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// root
			if (i == 0) {
				if (index != 0) {
					return null;
				}

				if (item.length() == 0 || root.getTagName().equals(item)) {
					continue;
				} else {
					return null;
				}
			}

			for (int j = 0; j < root.getChildNodes().getLength(); j++) {
				Node node = root.getChildNodes().item(j);
				if (node instanceof Element) {
					Element element = (Element) node;

					// notag_mode
					if (item.length() == 0) {
						if (counter == index) {
							root = element;
							found = true;
							break;
						}

						counter++;
					}
					// tag mode
					else if (element.getTagName().equals(item)) {
						if (counter == index) {
							root = element;
							found = true;
							break;
						} else {
							counter++;
						}
					}
				}
			}

			if (!found) {
				return null;
			}
		}

		return root;
	}

	private void parseXML(Element element, String tag, String attr, int type) {
		if (element.getTagName().equals(tag)) {
			XMLResult oneResult = null;
			if (XMLResult.ATTRIBUTE == type && element.hasAttribute(attr)) {
				oneResult = new XMLResult(XMLResult.ATTRIBUTE, element
						.getAttribute(attr), findXpath(element,
						XMLUtil.XPATH_TAG_MODE));
				xmlResult.add(oneResult);
			} else if (XMLResult.CONTENT == type) {
			//	oneResult = new XMLResult(XMLResult.CONTENT, element
			//			.getTextContent(), findXpath(element,
			//			XMLUtil.XPATH_TAG_MODE));
				xmlResult.add(oneResult);
			} else if (XMLResult.TAG == type) {
				oneResult = new XMLResult(XMLResult.TAG, element.getTagName(),
						findXpath(element, XMLUtil.XPATH_TAG_MODE));
				xmlResult.add(oneResult);
			}
		}

		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node instanceof Element) {
				parseXML((Element) node, tag, attr, type);
			}
		}
	}

	public ArrayList<XMLResult> getAttrValues(String tag, String attr) {
		xmlResult.clear();
		parseXML(getRoot(), tag, attr, XMLResult.ATTRIBUTE);

		return xmlResult;
	}

	public ArrayList<XMLResult> getContents(String tag) {
		xmlResult.clear();
		parseXML(getRoot(), tag, null, XMLResult.CONTENT);

		return xmlResult;
	}

	public ArrayList<XMLResult> getXPaths(String tag) {
		xmlResult.clear();
		parseXML(getRoot(), tag, null, XMLResult.TAG);

		return xmlResult;
	}

	public String getAttrValue(String tag, String attr, int n) {
		ArrayList<XMLResult> results = getAttrValues(tag, attr);

		if (results.size() == 0 || n < 0 || n >= results.size()) {
			return "";
		} else {
			return results.get(n).getData();
		}
	}

	public String getAttrValue(String tag, String attr) {
		return getAttrValue(tag, attr, 0);
	}

	public String getContent(String tag, int n) {
		ArrayList<XMLResult> results = getContents(tag);

		if (results.size() == 0 || n < 0 || n >= results.size()) {
			return "";
		} else {
			return results.get(n).getData();
		}
	}

	public String getContent(String tag) {
		return getContent(tag, 0);
	}

	public String getXPath(String tag, int n) {
		ArrayList<XMLResult> results = getContents(tag);

		if (results.size() == 0 || n < 0 || n >= results.size()) {
			return "";
		} else {
			return results.get(n).getXpath();
		}
	}

	public String getXPath(String tag) {
		return getXPath(tag, 0);
	}

	public Element addElement(Element parent, String tagname) {
		Element elment = getDoc().createElement(tagname);

		if (parent == null) {
			elment.appendChild(parent);
		} else {
			parent.appendChild(elment);
		}

		return elment;
	}

	public Element insertBefore(Element element, String tagname) {
		Element newElment = getDoc().createElement(tagname);
		Element parent = null;

		if (element == null) {
			return null;
		} else {
			parent = (Element) element.getParentNode();

			if (parent == null || !(parent instanceof Element)) {
				return null;
			}

			return (Element) parent.insertBefore(newElment, element);
		}
	}

	public Element setElement(Element element, String tagname) {
		if (element == null) {
			return null;
		}

		Node parent = element.getParentNode();

		if (parent == null || !(parent instanceof Element)) {
			return null;
		}

		Element newElement = getDoc().createElement(tagname);

		// copy all
		while (element.getChildNodes().getLength() > 0) {
			Node childNode = element.getFirstChild();
			newElement.appendChild(childNode);
		}

		// copy all attributes
		for (int i = 0; i < element.getAttributes().getLength(); i++) {
			Attr attr = (Attr) element.getAttributes().item(i);
			setAttrValue(newElement, attr.getName(), attr.getValue());
		}

		((Element) parent).insertBefore(newElement, element);
		((Element) parent).removeChild(element);

		return newElement;
	}

	public boolean removeElement(Element element) {
		if (element == null) {
			return false;
		}

		Element parent = (Element) element.getParentNode();

		if (parent == null) {
			return false;
		}

		parent.removeChild(element);

		return true;
	}

	public boolean setAttrValue(Element element, String attr, String value) {
		if (element == null) {
			return false;
		}

		if (element.hasAttribute(attr)) {
			element.setAttribute(attr, value);
		} else {
			Attr newAttr = getDoc().createAttribute(attr);
			newAttr.setValue(value);
			element.setAttributeNode(newAttr);
		}

		return true;
	}

	public boolean removeAttrValue(Element element, String attr) {
		if (element == null) {
			return false;
		}

		if (element.hasAttribute(attr)) {
			element.removeAttribute(attr);
		} else {
			return false;
		}

		return true;
	}

	public boolean setContent(Element element, String content) {
		if (element == null) {
			return false;
		}

	//	element.setTextContent(content);

		return true;
	}

	public boolean update(File file) {
		if (file == null) {
			return false;
		}

		if (!file.exists() || !file.isFile()) {
			try {
				if (!file.createNewFile()) {
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource(doc), new StreamResult(
					new FileOutputStream(file)));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean update(String filename) {
		return update(new File(filename));
	}

	public boolean update() {
		if (xmlFile == null) {
			return false;
		}

		return update(xmlFile);
	}

	public static void main(String[] args) {
		XMLUtil xmlUtil = new XMLUtil("h:\\test1.xml");

		Element element = xmlUtil.getElement("abc\\(0)\\(0)");

		Element newElement = xmlUtil.addElement(element, "helloworld");// xmlUtil.insertBefore(element,
																		// "helloworld");
		xmlUtil.setAttrValue(newElement, "first", "1");
		xmlUtil.setAttrValue(newElement, "second", "2");
		xmlUtil.setAttrValue(newElement, "third", "3");
		//
		// String xpath=xmlUtil.getXPath("helloworld");
		// element=xmlUtil.getElement(xpath);
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",second="+element.getAttribute("second"));
		//		
		// xmlUtil.removeAttrValue(element, "second");
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",second="+element.getAttribute("second"));
		// xmlUtil.removeAttrValue(element, "second");
		//		
		// xmlUtil.setContent(element, "hello world");
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",content="+xmlUtil.getContent("helloworld"));
		//		
		// xmlUtil.setElement(element, "hellosmh");
		// xpath=xmlUtil.getXPath("hellosmh");
		// element=xmlUtil.getElement(xpath);
		//		
		// if (element!=null)
		// {
		// System.out.println();
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",first="+element.getAttribute("first"));
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",content="+xmlUtil.getContent("helloworld"));
		// System.out.println("tag
		// name:"+element.getTagName()+",xpath="+xpath+",content="+xmlUtil.getContent("hellosmh"));
		// }
		//		
		// element=xmlUtil.getElement("abc\\aaa");
		// xmlUtil.setElement(element, "aaaa");
		//		
		// element=xmlUtil.getElement("abc\\ccc");
		// xmlUtil.removeElement(element);

		System.out.println("update to new file:"
				+ xmlUtil.update("h:\\test2.xml"));

	}
}
