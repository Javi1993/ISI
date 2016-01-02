package com.isi.master.visualizacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Opcion {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collectionTweet;
	private MongoCollection<Document> collectionTweetProv;
	private MongoCollection<Document> collectionAire;
	
	public Opcion(){
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collectionTweet = database.getCollection("twitter");//tomamos la coleccion de tweets-prov
		collectionTweetProv = database.getCollection("tweetProv");//tomamos la coleccion de tweets-prov
		collectionAire = database.getCollection("aire");//tomamos la coleccion de mapas de aire
	}
	
	@SuppressWarnings("unchecked")
	public HttpServletRequest opcion1(HttpServletRequest request){
		Document doc = collectionTweetProv.find(new Document("_id", request.getParameter("provincia"))).first();
		List<Document> tweets = (List<Document>) doc.get("tweets");
		int[] feeling = new int[3];//0-P,1-N,2-Neu
		HashMap<String,Integer> hashTag = new HashMap<String,Integer>();
		SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
		HashMap<Date,List<HashtagFreq>> hashTagDate = new HashMap<Date,List<HashtagFreq>>();

		for (Document tw : tweets) 
		{//cogemos el feeling de los tweets y el numero de hashtags
			switch (tw.getString("feeling")) {
			case "P":
				feeling[0]++;
				break;
			case "N":
				feeling[1]++;
				break;
			default:
				feeling[2]++;
				break;
			}

			//cogemos el array de hashtags
			List<String> hashTagsList = (List<String>)collectionTweet.find(new Document("_id",tw.getString("id_tweet"))).first().get("hashtag");
			if(hashTagsList!=null && hashTagsList.size()>0)
			{//comprobamos que el tweet tenga hashtags
				for (String palabra : hashTagsList) {
					palabra = Funciones.quitarTildes(palabra);
					insertarPalabra(palabra, hashTag);//insertamos en el hashmap de frequencia de hashtag
					String fecha = dt1.format(collectionTweet.find(new Document("_id",tw.getString("id_tweet"))).first().getDate("fecha"));
					insertarPalabraDate(palabra,hashTagDate, fecha);//insertamos en el hashmap de evolucion por fecha
				}
			}
		}

		Map<String, Integer> sortHashtag = sortByComparator(hashTag, false);

		Map<Date, List<HashtagFreq>> newMap = new TreeMap<Date, List<HashtagFreq>>();
		newMap.putAll(hashTagDate);

		//		Iterator it = newMap.entrySet().iterator();
		//		while (it.hasNext()) {
		//
		//			Map.Entry<Date,List<HashtagFreq>> e = (Map.Entry<Date,List<HashtagFreq>>)it.next();
		//			System.out.println("--"+dt1.format(e.getKey())+" :");
		//			for(HashtagFreq hashtagFreq : e.getValue())
		//			{
		//				System.out.println("---- "+hashtagFreq.getHashtag()+"_"+hashtagFreq.getSize());
		//			}
		//		}
		//		System.out.println(hashTagDate.size());

		request.setAttribute("hashTagDate", newMap);
		request.setAttribute("hashTag", sortHashtag);
		request.setAttribute("tweets", tweets);
		request.setAttribute("feeling", feeling);
		client.close();
		return request;
	}
	
	@SuppressWarnings("deprecation")
	private void insertarPalabraDate(String palabra, HashMap<Date,List<HashtagFreq>> hashDate, String fecha) {
		List<HashtagFreq> hashList = new ArrayList<HashtagFreq>();
		if(hashDate.get(new Date(fecha))!=null){
			List<HashtagFreq> aux = hashDate.get(new Date(fecha));
			boolean existe = false;
			for (HashtagFreq hashtagFreq : aux) {
				if(hashtagFreq.getHashtag().equals("#"+palabra))
				{
					hashtagFreq.setSize(hashtagFreq.getSize()+1);
					existe = true;
					break;
				}
			}
			//Ese hashtag no esta para esa fecha
			if(!existe){aux.add(new HashtagFreq("#"+palabra, 1));}
			hashDate.put(new Date(fecha), aux);//actualizamos
		}else{//insertamos la nueva fecha con su hashtag
			hashList.add(new HashtagFreq("#"+palabra, 1));
			hashDate.put(new Date(fecha), hashList);
		}
	}

	/**
	 * 
	 * @param palabra
	 * @param hashTag
	 */
	private void insertarPalabra(String palabra, HashMap<String, Integer> hashTag) {
		if(hashTag.get(palabra)!=null){
			hashTag.put(palabra, hashTag.get(palabra)+1);
		}else{
			hashTag.put(palabra, 1);
		}
	}

	/**
	 * 
	 * @param unsortMap
	 * @param order
	 * @return
	 */
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
	{
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>()
		{
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
}
