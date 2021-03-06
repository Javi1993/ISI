package com.isi.master.meaningcloudAPI.topicsextraction;
/**
 * Topics Extraction 2.0 starting client for Java.
 *
 * In order to run this example, the license key must be included in the key variable.
 * If you don't know your key, check your personal area at MeaningCloud (https://www.meaningcloud.com/developer/account/licenses)
 *
 * Once you have the key, edit the parameters and call "javac *.java; java TopicsClient"
 *
 * You can find more information at http://www.meaningcloud.com/developer/topics-extraction/doc/2.0
 *
 * @author     MeaningCloud
 * @contact    http://www.meaningcloud.com 
 * @copyright  Copyright (c) 2015, MeaningCloud LLC All rights reserved.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import com.isi.master.funciones.Funciones;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
//import java.io.ByteArrayInputStream;
import org.w3c.dom.*;


/**
 * This class implements a starting client for Topics Extraction 
 */
public class TopicsClient {

	/**
	 * print the general information of a topic
	 * @param response main element of the response
	 * @param nameNode name of the node to process
	 * @return the information of the nodes
	 */
	public static String printInfoGeneral(Element response, String nameNode) {
		String output = "";
		NodeList topic_list = response.getElementsByTagName(nameNode+"_list");
		if(topic_list.getLength()>0){
			Node topics = topic_list.item(0);
			NodeList topic = topics.getChildNodes();
			for(int i=0; i<topic.getLength(); i++) {
				Node topic_item = topic.item(i);
				NodeList child_topic = topic_item.getChildNodes();
				String form = "";
				String type = "";
				for(int j=0; j<child_topic.getLength(); j++){
					Node info_topic = child_topic.item(j);
					if(info_topic.getNodeName().equals("form"))
						form = info_topic.getTextContent();
					else if(info_topic.getNodeName().equals("type")) 
						type = info_topic.getTextContent();
				}
				output += " - " + form;
				if(!type.isEmpty())
					output += " (" +  type + ")";
				output += "\n";
			}
		}
		if(output.isEmpty())
			output = "Not found\n";
		return output;
	} //printInfoGeneral

	/**
	 * print the information of the entities and concepts
	 * @param response main element of the response
	 * @param nameNode name of the node to process (entity or concept)
	 * @return the information of the nodes
	 */
	public static String printInfoEntityConcept(Element response, String nameNode) {
		String output = "";
		NodeList topic_list = response.getElementsByTagName(nameNode+"_list");
		if(topic_list.getLength()>0){
			Node topics = topic_list.item(0);
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
					else if(name.equals("sementity")) { 
						NodeList child_sementity = info_topic.getChildNodes();
						for(int l=0; l<child_sementity.getLength(); l++){
							Node info_sementity = child_sementity.item(l);
							if(info_sementity.getNodeName().equals("type")) {
								type += info_sementity.getTextContent()+"|";
							}
						}
					}
				}
				output += " - " + form;
				if(!type.isEmpty())
					output += " (" +  type.substring(0, type.length()-1) + ")";
				output += "\n";
			}
		}
		if(output.isEmpty())
			output = "Not found\n";
		return output;
	} //printInfoEntityConcept

	/**
	 * print the information of the quotes
	 * @param response main element of the response
	 * @return the information of the nodes
	 */
	public static String printInfoQuotes(Element response) {
		String output = "";
		NodeList quotation_list = response.getElementsByTagName("quotation_list");
		if(quotation_list.getLength()>0){
			Node quotations = quotation_list.item(0);
			NodeList quotation = quotations.getChildNodes();
			for(int i=0; i<quotation.getLength(); i++) {
				Node quotation_item = quotation.item(i);
				NodeList child_quotation = quotation_item.getChildNodes();
				String form = "";
				String who = "";
				String who_lemma = "";
				String verb = "";
				String verb_lemma = "";
				for(int j=0; j<child_quotation.getLength(); j++){
					Node info_quotation = child_quotation.item(j);
					String name = info_quotation.getNodeName();
					if(name.equals("form"))
						form = info_quotation.getTextContent();
					else if(name.equals("who")) { 
						NodeList childWho = info_quotation.getChildNodes();
						for(int k=0; k<childWho.getLength();k++) {
							Node infoWho = childWho.item(k);
							String nameWho = infoWho.getNodeName();
							if(nameWho.equals("form"))
								who = infoWho.getTextContent();
							else if(nameWho.equals("lemma"))
								who_lemma = infoWho.getTextContent();
						}
					} else if(name.equals("verb")) {
						NodeList childVerb = info_quotation.getChildNodes();
						for(int k=0; k<childVerb.getLength();k++) {
							Node infoVerb = childVerb.item(k);
							String nameVerb = infoVerb.getNodeName();
							if(nameVerb.equals("form"))
								verb = infoVerb.getTextContent();
							else if(nameVerb.equals("lemma"))
								verb_lemma = infoVerb.getTextContent();
						}
					}
				}
				output += " - " + form;
				output += "\n";
				if(!who.isEmpty())
					output += "   + who: " + who + " (" + who_lemma + ")\n";
				if(!verb.isEmpty())
					output += "   + verb: " + verb + " (" + verb_lemma + ")\n";
			}
		}
		if(output.isEmpty())
			output = "Not found\n";
		return output;
	} //printInfoQuotes

	/**
	 * print the information of the relations
	 * @param response main element of the response
	 * @return the information of the nodes
	 */
	public static String printInfoRelation(Element response) {
		String output = "";
		NodeList relation_list = response.getElementsByTagName("relation_list");
		if(relation_list.getLength()>0){
			Node relations = relation_list.item(0);
			NodeList relation = relations.getChildNodes();
			for(int i=0; i<relation.getLength(); i++) {
				Node relation_item = relation.item(i);
				NodeList child_relation = relation_item.getChildNodes();
				String form = "";
				String subject = "";
				String subject_lemmas = "";
				String verb = "";
				String verb_lemmas = "";
				String complement_info = "";
				for(int j=0; j<child_relation.getLength(); j++){
					Node info_relation = child_relation.item(j);
					String name = info_relation.getNodeName();
					if(name.equals("form"))
						form = info_relation.getTextContent();
					else if(name.equals("subject")) {
						NodeList child_subject = info_relation.getChildNodes();
						for(int k=0; k<child_subject.getLength(); k++) {
							Node info_subject = child_subject.item(k);
							if(info_subject.getNodeName() == "form") {
								subject = info_subject.getTextContent();
							} else if (info_subject.getNodeName() == "lemma_list") {
								NodeList child_lemma_subject = info_subject.getChildNodes();
								for(int m = 0; m<child_lemma_subject.getLength(); m++){
									Node info_lemma_subject = child_lemma_subject.item(m);
									if(info_lemma_subject.getNodeName() == "lemma")
										subject_lemmas = info_lemma_subject.getTextContent()+"|";
								}
							}
						}
					} else if(name.equals("verb")) {
						NodeList child_verb = info_relation.getChildNodes();
						for(int k=0; k<child_verb.getLength(); k++) {
							Node info_verb = child_verb.item(k);
							if(info_verb.getNodeName() == "form") {
								verb = info_verb.getTextContent();
							} else if (info_verb.getNodeName() == "lemma_list") {
								NodeList child_lemma_verb = info_verb.getChildNodes();
								for(int m = 0; m<child_lemma_verb.getLength(); m++){
									Node info_lemma_verb = child_lemma_verb.item(m);
									if(info_lemma_verb.getNodeName() == "lemma")
										verb_lemmas = info_lemma_verb.getTextContent()+"|";
								}
							}
						}
					} else if(name.equals("complement_list")) {
						NodeList child_complement = info_relation.getChildNodes();
						for(int k=0; k<child_complement.getLength(); k++) {
							Node complement_item = child_complement.item(k);
							if(complement_item.getNodeName() == "complement") {
								NodeList complement = complement_item.getChildNodes();
								for(int m=0; m<complement.getLength(); m++) {
									Node info_complement = complement.item(m);
									if(info_complement.getNodeName() == "form")
										complement_info += "    * " + info_complement.getTextContent();
									if(info_complement.getNodeName() == "type")
										complement_info += " (" + info_complement.getTextContent() +")";
								}
								complement_info += "\n";
							}
						}
					}
				}
				output += " - " + form;
				output += "\n";
				if(!subject.isEmpty()) {
					output += "   + subject: " + subject;
					if(!subject_lemmas.isEmpty())              
						output += " (" + subject_lemmas.substring(0, subject_lemmas.length()-1) + ")";
					output += "\n";
				}
				if(!verb.isEmpty()) {
					output += "   + verb: " + verb;
					if(!verb_lemmas.isEmpty())
						output += " (" + verb_lemmas.substring(0, verb_lemmas.length()-1) + ")";
					output += "\n";
				}
				if(!complement_info.isEmpty())
					output += "   + complements: \n" + complement_info + "\n";
			}
		}
		if(output.isEmpty())
			output = "Not found\n";
		return output;
	} //printInfoRelation

	/**
	 * 
	 * @param contenido
	 * @param body
	 * @return
	 */
	public static List<String> recibirTweet(String contenido, boolean body){
		String api = "http://api.meaningcloud.com/topics-2.0";
		String key = "c4b916eee1c44d9d6cd670b19ce6470e";
		String txt = contenido;
		String lang = "es"; // es/en/fr/it/pt/ca
		List<String> provincia=new ArrayList<String>();
		try{
			Post post = new Post (api);
			post.addParameter("key", key);
			post.addParameter("txt", txt);
			post.addParameter("lang", lang);
			if(body){post.addParameter("tt", "ec");}else{post.addParameter("tt", "e");}
			post.addParameter("uw", "y");
			post.addParameter("cont", "City");
			post.addParameter("of", "json");

			JSONObject jsonObj =null;
			try {
				jsonObj = new JSONObject(post.getResponse());
				boolean seguir = false;
				if(body)
				{
					seguir = conceptTopics(jsonObj);
					if(!seguir){//quitamos '#' de los hashtag
						Post post2 = new Post (api);
						post2.addParameter("key", key);
						post2.addParameter("txt", txt.replaceAll("#", ""));
						post2.addParameter("lang", lang);
						post2.addParameter("tt", "ec");
						post2.addParameter("uw", "y");
						post2.addParameter("cont", "City");
						post2.addParameter("of", "json");
						JSONObject jsonObj2 = new JSONObject(post.getResponse());
						seguir = conceptTopics(jsonObj2);
					}
				}
				if(seguir||!body)
				{//la parte del tweet analizada es el cuerpo y tiene contenido relacionado con contaminacion o no es el cuerpo y se quiere buscar localizacion
					provincia = entidadTopics(jsonObj);
					if(provincia.size()==0)
					{
						if(body)
						{
							provincia = analisisRegex(txt, true);
							if(provincia.size()==0)
							{
								Post post2 = new Post (api);
								post2.addParameter("key", key);
								post2.addParameter("txt", txt.replaceAll("#", ""));
								post2.addParameter("lang", lang);
								post2.addParameter("tt", "ec");
								post2.addParameter("uw", "y");
								post2.addParameter("cont", "City");
								post2.addParameter("of", "json");
								JSONObject jsonObj2 = new JSONObject(post.getResponse());
								provincia = entidadTopics(jsonObj2);
							}
						}else{
							provincia = analisisRegex(txt, false);
						}
					}
				}else{//la parte del tweet analizada es el cuerpo y no tiene ningun contenido relacionado con contaminacion
					return null;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//				System.err.println("No es posible analizar '"+contenido+"' en base a entidades\n");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return provincia;
	}

	/**
	 * 
	 * @param provincia
	 * @param txt
	 * @param body
	 */
	private static List<String> analisisRegex(String txt, boolean body){
		String sCurrentLine;//string que almacena los paises y zonas de habla hispana de los que puede haber tweets
		BufferedReader br = null;
		List<String> provincia = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader("./documentos/Personas_/zonas.txt"));//cogemos los paises y zonas de habla hispana
			while ((sCurrentLine = br.readLine()) != null) {//borramos tweets de fuera de Espana
				if(body)
				{
					if(Funciones.quitarTildes(txt).matches(".*#"+sCurrentLine.toUpperCase().split("-")[0]))
					{
						provincia.add(sCurrentLine.split("-")[1]);
					}
				}else{
					if(Funciones.quitarTildes(txt).matches(sCurrentLine.toUpperCase().split("-")[0]))
					{
						provincia.add(sCurrentLine.split("-")[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provincia;
	}

	/**
	 * 
	 * @param jsonObj
	 * @return
	 */
	private static boolean conceptTopics(JSONObject jsonObj)
	{
		try {
			JSONArray array = jsonObj.getJSONArray("concept_list");

			for(int i = 0; i<array.length(); i++)
			{
				JSONObject doc = (JSONObject) array.getJSONObject(i);
				JSONObject doc1 = (JSONObject) doc.get("sementity");
				if(doc1.getString("type").contains("Top>"))
				{
					return true;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
		}
		return false;
	}

	private static List<String> entidadTopics(JSONObject jsonObj)
	{
		List<String> provincia=new ArrayList<String>();
		try {
			JSONArray array = jsonObj.getJSONArray("entity_list");
			for(int i = 0; i<array.length(); i++)
			{
				try{
					JSONObject doc = (JSONObject) array.getJSONObject(i);
					JSONObject doc1 = (JSONObject) doc.get("sementity");
					if(doc1.getString("id").equals("ODENTITY_CITY")&&doc1.getString("type").equals("Top>Location>GeoPoliticalEntity>City"))
					{
						JSONArray doc2 = (JSONArray) doc.get("semgeo_list");
						JSONObject doc21 = (JSONObject) doc2.get(0);
						if(((JSONObject)doc21.get("country")).getString("form").equals("España"))
						{
							try{
								provincia.add(((JSONObject)doc21.get("adm2")).getString("form"));
							}catch(JSONException e){
								provincia.add(((JSONObject)doc21.get("adm1")).getString("form"));
							}
						}else{
							//							System.err.println(((JSONObject)array.get(i)).get("form")+" en el texto se refiere a un lugar de "+((JSONObject)doc21.get("country")).getString("form"));
						}
					}
					else if(doc1.getString("id").equals("ODENTITY_ADM2")&&doc1.getString("type").equals("Top>Location>GeoPoliticalEntity>Adm2")){
						JSONArray doc2 = (JSONArray) doc.get("semgeo_list");
						JSONObject doc21 = (JSONObject) doc2.get(0);
						if(((JSONObject)doc21.get("country")).getString("form").equals("España"))
						{//insertamos la entidad
							provincia.add(doc.getString("form"));
						}else{
							//							System.err.println(((JSONObject)array.get(i)).get("form")+" en el texto se refiere a un lugar de "+((JSONObject)doc21.get("country")).getString("form"));
						}
					}
					else{
						//						System.err.println(((JSONObject)array.get(i)).get("form")+" no es una ciudad\n");
					}
				}catch(JSONException e)
				{
					//					System.err.println(((JSONObject)array.get(i)).get("form")+" no es una ciudad\n");
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			//			e1.printStackTrace();
		}
		return provincia;
	}

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// We define the variables needed to call the API
		String api = "http://api.meaningcloud.com/topics-2.0";
		String key = "67d2d31e37c2ba1d032188b1233f19bf";
		String txt = "Los vehículos AUDI con tres ocupantes podrán circular por Real Madrid, Alcobendas y Miranda de Ebro los días de contaminación porque Carmena no les deja debido al aire sucio";
		//		String txt = "Madrid está con altos niveles de contaminación, como el NO2";
		//		String txt = "La ciudad Madrid está con altos niveles de contaminación, como el NO2";
		//		String txt = "Avilés";
		String lang = "es"; // es/en/fr/it/pt/ca

		Post post = new Post (api);
		post.addParameter("key", key);
		post.addParameter("txt", txt);
		post.addParameter("lang", lang);
		post.addParameter("tt", "ec");
		post.addParameter("uw", "y");
		post.addParameter("cont", "City");
		post.addParameter("of", "json");

		//      String response = post.getResponse();
		JSONObject jsonObj =null;
		try {
			jsonObj = new JSONObject(post.getResponse());
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
			System.out.println(jsonObj.toString());
			JSONArray array2 = jsonObj.getJSONArray("concept_list");
			System.out.println(array2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Show response
		System.out.println("Response");
		System.out.println("============");
		try {
			System.out.println(jsonObj);
			JSONArray array = jsonObj.getJSONArray("entity_list");
			System.out.println(array);
			for(int i = 0; i<array.length(); i++)
			{
				try{
					//				System.out.println("_--------------------_");
					JSONObject doc = (JSONObject) array.getJSONObject(i);
					System.out.println(doc);
					//									System.out.println("_---------------------_");
					JSONObject doc1 = (JSONObject) doc.get("sementity");
					System.out.println(doc1);
					//					System.out.println("_A---------------------_");
					if(doc1.getString("id").equals("ODENTITY_CITY")&&doc1.getString("type").equals("Top>Location>GeoPoliticalEntity>City"))
					{
						JSONArray doc2 = (JSONArray) doc.get("semgeo_list");
						JSONObject doc21 = (JSONObject) doc2.get(0);
						if(((JSONObject)doc21.get("country")).getString("form").equals("España"))
						{
							//							System.out.println("Entidad_: "+((JSONObject)array.get(i)).get("form"));
							//							System.out.println("IDENTIFICADORES DE ENTIDAD CIUDAD_: "+doc1.getString("id")+" - "+doc1.getString("type"));
							//							System.out.println("PAIS_: "+((JSONObject)doc21.get("country")).get("form"));
							try{
								System.out.println("PROVINCIA_: "+((JSONObject)doc21.get("adm2")).get("form")+"\n");
							}catch(JSONException e){
								System.out.println("PROVINCIA_: "+((JSONObject)doc21.get("adm1")).get("form")+"\n");
							}
						}else{
							System.err.println(((JSONObject)array.get(i)).get("form")+" en el texto se refiere a un lugar de "+((JSONObject)doc21.get("country")).getString("form"));
						}
					}else if(doc1.getString("id").equals("ODENTITY_ADM2")&&doc1.getString("type").equals("Top>Location>GeoPoliticalEntity>Adm1")){
						System.out.println(doc.get("form"));
						JSONArray doc2 = (JSONArray) doc.get("semgeo_list");
						JSONObject doc21 = (JSONObject) doc2.get(0);
						if(((JSONObject)doc21.get("country")).getString("form").equals("España"))
						{
							System.out.println("PAIS_: "+((JSONObject)doc21.get("country")).get("form"));
						}
					}else{
						System.err.println(((JSONObject)array.get(i)).get("form")+" no es una ciudad\n");
					}
				}catch(JSONException e)
				{
					System.err.println(((JSONObject)array.get(i)).get("form")+" no es una ciudad\n");
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// Prints the specific fields in the response (topics)
		//      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		//      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		//      Document doc = docBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
		//      doc.getDocumentElement().normalize();
		//      Element response_node = doc.getDocumentElement();
		//      System.out.println("\nInformation:");
		//      System.out.println("----------------\n");
		//      try {
		//        NodeList status_list = response_node.getElementsByTagName("status");
		//        Node status = status_list.item(0);
		//        NamedNodeMap attributes = status.getAttributes();
		//        Node code = attributes.item(0);
		//        if(!code.getTextContent().equals("0")) {
		//          System.out.println("Not found");
		//        } else {
		//          String output = "";
		//          output += "Entities:\n";
		//          output += "=============\n";
		//          output += printInfoEntityConcept(response_node, "entity");
		////          output += "\n";
		////          output += "Concepts:\n";
		////          output += "============\n";
		////          output += printInfoEntityConcept(response_node, "concept");
		////          output += "\n";
		////          output += "Time expressions:\n";
		////          output += "==========\n";
		////          output += printInfoGeneral(response_node, "time_expression");
		////          output += "\n";
		////          output += "Money expressions:\n";
		////          output += "===========\n";
		////          output += printInfoGeneral(response_node, "money_expression");
		////          output += "\n";
		////          output += "Quantity expressions:\n";
		////          output += "======================\n";
		////          output += printInfoGeneral(response_node, "quantity_expression");
		////          output += "\n";
		////          output += "Other expressions:\n";
		////          output += "====================\n";
		////          output += printInfoGeneral(response_node, "other_expression");
		////          output += "\n";
		////          output += "Quotations:\n";
		////          output += "====================\n";
		////          output += printInfoQuotes(response_node);
		////          output += "\n";
		////          output += "Relations:\n";
		////          output += "====================\n";
		////          output += printInfoRelation(response_node);
		//          output += "\n";
		//          if(output.isEmpty())
		//            System.out.println("Not found");
		//          else
		//            System.out.print(output);
		//        }
		//      } catch (Exception e) {
		//        System.out.println("Not found");
		//      }
	}
}
