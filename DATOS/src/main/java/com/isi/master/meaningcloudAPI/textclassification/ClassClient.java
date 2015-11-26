package com.isi.master.meaningcloudAPI.textclassification;

/**
 * Text Classification 1.1 starting client for Java.
 *
 * In order to run this example, the license key must be included in the key variable.
 * If you don't know your key, check your personal area at MeaningCloud (https://www.meaningcloud.com/developer/account/licenses)
 *
 * Once you have the key, edit the parameters and call "javac *.java; java ClassClient"
 *
 * You can find more information at http://www.meaningcloud.com/developer/text-classification/doc/1.1
 *
 * @author     MeaningCloud
 * @contact    http://www.meaningcloud.com (http://www.daedalus.es)
 * @copyright  Copyright (c) 2015, DAEDALUS S.A. All rights reserved.
 */
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;

import org.w3c.dom.*;


/**
 * This class implements a starting client for Text Classification  
 */
public class ClassClient {

	/**
	 * Lee un fichero y pasa su contenido a un String
	 * @param ruta
	 * @return String con el contenido
	 */
	private static String leerFichero(String ruta)
	{
		String txt="";
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(ruta));
			while ((sCurrentLine = br.readLine()) != null) {
				txt+=sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return txt;
	}
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// We define the variables needed to call the API
		String api = "http://api.meaningcloud.com/class-1.1";
		String key = "67d2d31e37c2ba1d032188b1233f19bf";
		String txt = leerFichero(".\\documentos\\Extras_\\ejemplo1.txt");

		String model = "IPTC_es";  // IPTC_es/IPTC_en/IPTC_fr/IPTC_it/IPTC_ca/EUROVOC_es_ca/BusinessRep_es/BusinessRepShort_es

		Post post = new Post (api);
		post.addParameter("key", key);
		post.addParameter("txt", txt);
		post.addParameter("model", model);
		post.addParameter("of", "xml");
		String response = post.getResponse();

		// Show response
		System.out.println("Response");
		System.out.println("============");
		System.out.println(response);

		// Prints the specific fields in the response (categories)
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
		doc.getDocumentElement().normalize();
		Element response_node = doc.getDocumentElement();
		System.out.println("\nCategories:");
		System.out.println("==============");
		try {
			NodeList status_list = response_node.getElementsByTagName("status");
			Node status = status_list.item(0);
			NamedNodeMap attributes = status.getAttributes();
			Node code = attributes.item(0);
			if(!code.getTextContent().equals("0")) {
				System.out.println("Not found");
			} else {
				NodeList category_list = response_node.getElementsByTagName("category_list");
				if(category_list.getLength()>0){
					Node categories = category_list.item(0);          
					NodeList category = categories.getChildNodes();
					String output = "";
					for(int i=0; i<category.getLength(); i++) {
						Node info_category = category.item(i);  
						NodeList child_category = info_category.getChildNodes();
						String label = "";
						String code_cat = "";
						String relevance = "";
						for(int j=0; j<child_category.getLength(); j++){
							Node n = child_category.item(j);
							String name = n.getNodeName();
							if(name.equals("code"))
								code_cat = n.getTextContent();
							else if(name.equals("label"))
								label = n.getTextContent();
							else if(name.equals("relevance"))
								relevance = n.getTextContent();
						}
						output += " + " + label + " (" +  code_cat + ")\n";
						output += "   -> relevance: " + relevance + "\n";
					}
					if(output.isEmpty())
						System.out.println("Not found");
					else
						System.out.print(output);
				}
			}
		} catch (Exception e) {
			System.out.println("Not found");
		}
	}
}
