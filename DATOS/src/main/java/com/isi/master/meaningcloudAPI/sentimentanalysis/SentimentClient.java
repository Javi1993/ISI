package com.isi.master.meaningcloudAPI.sentimentanalysis;
/**
 * Sentiment Analysis 2.0 starting client for Java.
 *
 * In order to run this example, the license key must be included in the key variable.
 * If you don't know your key, check your personal area at MeaningCloud (https://www.meaningcloud.com/developer/account/licenses)
 *
 * Once you have the key, edit the parameters and call "javac *.java; java SentimentClient"
 *
 * You can find more information at http://www.meaningcloud.com/developer/sentiment-analysis/doc/2.0
 *
 * @author     MeaningCloud
 * @contact    http://www.meaningcloud.com 
 * @copyright  Copyright (c) 2015, MeaningCloud LLC All rights reserved.
 */
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import org.w3c.dom.*;

/**
 * This class implements a starting client for Sentiment Analysis 
 */
public class SentimentClient {

	/**
	 * print the information of the entities and concepts
	 * @param response main element of the response
	 * @param nameNode name of the node to process (entity or concept)
	 * @return the information of the nodes
	 */
	public static String printInfoEntityConcept(Element response, String nameNode) {
		String output = "";
		NodeList topic_list = response.getElementsByTagName(nameNode+"_list");
		if(topic_list.getLength()>0) {
			int index = topic_list.getLength() - 1;
			Node topics = topic_list.item(index);
			NodeList topic = topics.getChildNodes();
			for(int i=0; i<topic.getLength(); i++) {
				Node topic_item = topic.item(i);
				NodeList child_topic = topic_item.getChildNodes();
				String form = "";
				String type = "";
				for(int j=0; j<child_topic.getLength(); j++){
					Node info_topic = child_topic.item(j);
					String name = info_topic.getNodeName();
					if(name.equals("form"))
						form = info_topic.getTextContent();
					else if(name.equals("type")) {
						type = info_topic.getTextContent();
					}
				}
				output += " - " + form;
				if(!type.isEmpty())
					output += " ("+ type + ")";
				output += "\n";
			}
		}
		if(output.isEmpty())
			output = "Not found\n";
		return output;
	} //printInfoEntityConcept

	public static String verSentimiento(org.bson.Document tweet)
	{
		String api = "http://api.meaningcloud.com/sentiment-2.0";
		String key = "e3f776dbb9ccef43818b576cf60340df";
		String txt = tweet.getString("contenido");
		String model = "general_es"; // general_es / general_en / general_fr

		String output = "none";
		try {
			Post post = new Post (api);
			post.addParameter("key", key);
			post.addParameter("txt", txt);
			post.addParameter("model", model);
			post.addParameter("uw", "y");
			post.addParameter("egp", "y");
			post.addParameter("verbose", "y");
			post.addParameter("cont", "City");
			post.addParameter("of", "xml");
			String response = post.getResponse();

			// Prints the specific fields in the response (sentiment)
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
			doc.getDocumentElement().normalize();
			Element response_node = doc.getDocumentElement();

			NodeList status_list = response_node.getElementsByTagName("status");
			Node status = status_list.item(0);
			NamedNodeMap attributes = status.getAttributes();
			Node code = attributes.item(0);
			if(!code.getTextContent().equals("0")) {
				//System.out.println("Not found");
			} else {    
				NodeList score_tags = response_node.getElementsByTagName("score_tag");

				
				Node score_tag = null;

				if(score_tags.getLength()>0)
					score_tag = score_tags.item(0);

				if(score_tag != null)
					output = score_tag.getTextContent();
			}
		} catch (Exception e) {
			//System.out.println("Not found");
		}
		return output;
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// We define the variables needed to call the API
		String api = "http://api.meaningcloud.com/sentiment-2.0";
		String key = "e3f776dbb9ccef43818b576cf60340df";
		String txt = "Que buen dia hace. Es ironía";
		String model = "general_es"; // general_es / general_en / general_fr

		Post post = new Post (api);
		post.addParameter("key", key);
		post.addParameter("txt", txt);
		post.addParameter("model", model);
		post.addParameter("uw", "y");
		post.addParameter("egp", "y");
		post.addParameter("verbose", "y");
		post.addParameter("cont", "City");
		post.addParameter("of", "xml");
		String response = post.getResponse();

		// Show response
		System.out.println("Response");
		System.out.println("============");
		System.out.println(response);

		// Prints the specific fields in the response (sentiment)
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
		doc.getDocumentElement().normalize();
		Element response_node = doc.getDocumentElement();
		System.out.println("\nSentiment:");
		System.out.println("=============");
		try {
			NodeList status_list = response_node.getElementsByTagName("status");
			Node status = status_list.item(0);
			NamedNodeMap attributes = status.getAttributes();
			Node code = attributes.item(0);
			if(!code.getTextContent().equals("0")) {
				System.out.println("Not found");
			} else {    
				NodeList score_tags = response_node.getElementsByTagName("score_tag");
				NodeList agreements = response_node.getElementsByTagName("agreement");
				NodeList subjectivities = response_node.getElementsByTagName("subjectivity");
				NodeList ironies = response_node.getElementsByTagName("irony");
				NodeList confidences = response_node.getElementsByTagName("confidence");

				String output = "";
				Node score_tag = null;
				Node agreement = null;
				Node subjectivity = null;
				Node irony = null;
				Node confidence = null;
				if(score_tags.getLength()>0)
					score_tag = score_tags.item(0);
				if(agreements.getLength()>0)
					agreement = agreements.item(0);
				if(subjectivities.getLength()>0)
					subjectivity = subjectivities.item(0);
				if(ironies.getLength()>0)
					irony = ironies.item(0);
				if(confidences.getLength()>0)
					confidence = confidences.item(0);

				if(score_tag != null)
					output += "Global sentiment: " + score_tag.getTextContent();
				if(agreement != null)
					output += " (" + agreement.getTextContent() + ")";
				output += "\n";
				if(subjectivity != null)
					output += "Subjectivity: " + subjectivity.getTextContent() + "\n";
				if(irony != null)
					output += "Irony: " + irony.getTextContent() + "\n";
				if(confidence != null)
					output += "Confidence: " + confidence.getTextContent();

				if(output.trim().isEmpty())
					System.out.println("Not found");
				else
					System.out.println(output);
				output = "";
				// Entities
				output += "\nEntities:\n";
				output += "=============\n";
				output += printInfoEntityConcept(response_node, "sentimented_entity");
				// Concepts
				output += "\nConcepts:\n";
				output += "============\n";
				output += printInfoEntityConcept(response_node, "sentimented_concept");
				output += "\n";
				System.out.println(output);
			}
		} catch (Exception e) {
			System.out.println("Not found");
		}
	}
}
