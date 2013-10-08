package au.org.paperminer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.*;
import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.dom4j.Node;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NamedNodeMap;

public class NetClientGet {

	public static void main(String[] args) throws Exception {

			/*URL url = new URL(
					"http://trove.nla.gov.au/ndp/del/article/124673222?searchTerm=kingkong+china");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/html");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

			/*TagNode tagNode = new HtmlCleaner().clean(conn.getInputStream());
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
			System.out.println(result);*/
			// returns html body div#myDiv.foo.bar p#tID

			//conn.disconnect();
		
		Document doc = Jsoup.connect("http://trove.nla.gov.au/ndp/del/article/124673222?searchTerm=kingkong+china").get();
		Element element = doc.getElementById("initiateCite");
		
		Document doc1 = Jsoup.connect("http://trove.nla.gov.au"+element.attr("href")).get();
		
		Elements links = doc1.getElementsByTag("a");
		
		Pattern pattern = Pattern.compile("http://nla.gov.au/nla.news-page");
		
		String pageNumber = new String();
		
		for(Element link : links){
			String result = link.attr("href");
			Matcher matcher = pattern.matcher(result);
			if(matcher.find()){
				System.out.println(result);
				System.out.println(matcher.replaceAll(new String()));
				pageNumber = matcher.replaceAll(new String());
				break;
			}
		}
		
		Document doc2 = Jsoup.connect("http://trove.nla.gov.au/ndp/del/page/" + pageNumber + "?zoomLevel=1").get();
		Elements elements1 = doc2.getElementsByAttributeValue("title", "Download a PDF containing all pages from this issue");	
		Element lookingElement = elements1.get(0);
		System.out.println(lookingElement.attr("href"));
		
		String printNumber = lookingElement.attr("href");
		System.out.println(printNumber.replaceAll("/ndp/del/printIssue/", new String()));
	}

	// Gets id and class attributes of this node
/*	private static String readPath(Node node) {
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
	}*/
}
