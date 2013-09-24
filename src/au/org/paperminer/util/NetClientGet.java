package au.org.paperminer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NetClientGet {

	public static void main(String[] args) throws Exception {

		try {

			URL url = new URL(
					"http://trove.nla.gov.au/ndp/del/article/124673222?searchTerm=kingkong+china");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			/*while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

			TagNode tagNode = new HtmlCleaner().clean(conn.getInputStream());
			Document document = new DomSerializer(new CleanerProperties())
					.createDOM(tagNode);

			// use XPath to find target node
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xpath.evaluate("//*[text()='content']",
					document, XPathConstants.NODE);

			// assembles jquery/css selector
			String result = "";
			while (node != null && node.getParentNode() != null) {
				result = readPath(node) + " " + result;
				node = node.getParentNode();
			}
			System.out.println(result);
			// returns html body div#myDiv.foo.bar p#tID

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// Gets id and class attributes of this node
	private static String readPath(Node node) {
		NamedNodeMap attributes = node.getAttributes();
		String id = readAttribute(attributes.getNamedItem("id"), "#");
		String clazz = readAttribute(attributes.getNamedItem("class"), ".");
		return node.getNodeName() + id + clazz;
	}

	// Read attribute
	private static String readAttribute(Node node, String token) {
		String result = "";
		if (node != null) {
			result = token + node.getTextContent().replace(" ", token);
		}
		return result;
	}
}
