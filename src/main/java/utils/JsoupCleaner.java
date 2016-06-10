package utils;

import org.htmlcleaner.TagNode;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;

import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
// import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

public class JsoupCleaner {

	public String cleanHtml(String html) {
		//					TagNode tagNode = new HtmlCleaner().clean(html);
		//					org.w3c.dom.Document cleanedDocument = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
		Document doc = Jsoup.parse(html);
		String parsedHtml = doc.body().text();

		return parsedHtml;
	}
}
