package com.isi.master.visualizacion.clases;

import static java.util.Arrays.asList;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

	/**
	 * Opcion 3- Visualizacion de mapa+grafico de medias una provincia
	 * @param request - Parametros de la peticion
	 */
	public HttpServletRequest opcion3(HttpServletRequest request){
		try{
			//obtenemos los mapas para esa provincia
			Document map = collectionTweetProv.find(new Document("_id", request.getParameter("provincia"))).first();
			request.setAttribute("maps", map.get("Mapas"));
			//obtenemos las medias mensuales de los contaminantes que se miden por ug/m3
			List<Document> pipeline = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average_NO2", new Document("$avg","$Medidas.NO2")).append("average_NO", new Document("$avg","$Medidas.NO")).append("average_NOX", new Document("$avg","$Medidas.NOX"))
							.append("average_SO2", new Document("$avg","$Medidas.SO2")).append("average_PM10", new Document("$avg","$Medidas.PM10")).append("average_PM25", new Document("$avg","$Medidas.PM25"))
							.append("average_O3", new Document("$avg","$Medidas.O3")).append("average_BEN", new Document("$avg","$Medidas.BEN")).append("average_XIL", new Document("$avg","$Medidas.XIL"))
							.append("average_OXL", new Document("$avg","$Medidas.OXL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias = collectionAire.aggregate(pipeline).into(new ArrayList<Document>());
			request.setAttribute("medias",limpiarMedias(medias,""));
			//obtenemos las medias mensuales de los contaminantes que se miden por mg/m3
			List<Document> pipeline2 = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average_CO", new Document("$avg","$Medidas.CO")).append("average_SH2", new Document("$avg","$Medidas.SH2")).append("average_TOL", new Document("$avg","$Medidas.TOL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias2 = collectionAire.aggregate(pipeline2).into(new ArrayList<Document>());
			request.setAttribute("medias2",limpiarMedias2(medias2,""));
		}catch(NullPointerException e){
			System.err.println("Error: Se esta accediendo a un elemento nulo");
			e.printStackTrace();
		}
		client.close();
		return request;
	}

	/**
	 * Opcion 2- Visualizacion de provincia VS provincia
	 * @param request - Parametros de la peticion
	 */
	public HttpServletRequest opcion2(HttpServletRequest request){
		try{
			//obtenemos los mapas de las provincias
			Document map1 = collectionTweetProv.find(new Document("_id", request.getParameter("provincia"))).first();
			Document map2 = collectionTweetProv.find(new Document("_id", request.getParameter("provincia2"))).first();
			Document[] maps = new Document[2];
			maps[0] = (Document) map1.get("Mapas");
			maps[1] = (Document) map2.get("Mapas");
			request.setAttribute("maps", maps);
			//obtenemos las medias mensuales de los contaminantes que se miden por ug/m3
			List<Document> pipeline = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average_NO2", new Document("$avg","$Medidas.NO2")).append("average_NO", new Document("$avg","$Medidas.NO")).append("average_NOX", new Document("$avg","$Medidas.NOX"))
							.append("average_SO2", new Document("$avg","$Medidas.SO2")).append("average_PM10", new Document("$avg","$Medidas.PM10")).append("average_PM25", new Document("$avg","$Medidas.PM25"))
							.append("average_O3", new Document("$avg","$Medidas.O3")).append("average_BEN", new Document("$avg","$Medidas.BEN")).append("average_XIL", new Document("$avg","$Medidas.XIL"))
							.append("average_OXL", new Document("$avg","$Medidas.OXL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias = collectionAire.aggregate(pipeline).into(new ArrayList<Document>());

			//provincia2
			List<Document> pipelineB = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia2"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average2_NO2", new Document("$avg","$Medidas.NO2")).append("average2_NO", new Document("$avg","$Medidas.NO")).append("average2_NOX", new Document("$avg","$Medidas.NOX"))
							.append("average2_SO2", new Document("$avg","$Medidas.SO2")).append("average2_PM10", new Document("$avg","$Medidas.PM10")).append("average2_PM25", new Document("$avg","$Medidas.PM25"))
							.append("average2_O3", new Document("$avg","$Medidas.O3")).append("average2_BEN", new Document("$avg","$Medidas.BEN")).append("average2_XIL", new Document("$avg","$Medidas.XIL"))
							.append("average2_OXL", new Document("$avg","$Medidas.OXL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> mediasB = collectionAire.aggregate(pipelineB).into(new ArrayList<Document>());

			//obtenemos las medias mensuales de los contaminantes que se miden por mg/m3
			List<Document> pipeline2 = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average_CO", new Document("$avg","$Medidas.CO")).append("average_SH2", new Document("$avg","$Medidas.SH2")).append("average_TOL", new Document("$avg","$Medidas.TOL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias2 = collectionAire.aggregate(pipeline2).into(new ArrayList<Document>());

			//provincia2
			List<Document> pipeline2B = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia2"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average2_CO", new Document("$avg","$Medidas.CO")).append("average2_SH2", new Document("$avg","$Medidas.SH2")).append("average2_TOL", new Document("$avg","$Medidas.TOL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias2B = collectionAire.aggregate(pipeline2B).into(new ArrayList<Document>());

			request.setAttribute("medias",juntarListasOpcion2(juntarListasOpcion2(limpiarMedias(medias,""), limpiarMedias(mediasB,"2")), juntarListasOpcion2(limpiarMedias2(medias2,""), limpiarMedias2(medias2B,"2"))));
		}catch(NullPointerException e){
			System.err.println("Error: Se esta accediendo a un elemento nulo");
			e.printStackTrace();
		}
		client.close();
		return request;
	}

	/**
	 * Dada una lista de documentos de medias mensuales elimina los contaminantes sin media (ug/m3)
	 * @param sucia - lista con posibles contaminantes sin media
	 * @return lista con contaminantes con medias
	 */
	private List<Document> limpiarMedias(List<Document> sucia, String aux)
	{
		//0-NO2,1-NO,2-NOX,3-SO2,4-PM10,5-PM25,6-O3,7-BEN,8-XIL,9-OXL
		double[] contaminantes = new double[10];
		Map<Integer, String> lista = mapContaminates();
		for(Document doc:sucia)
		{//vemos los contaminantes de los que no se han tomado medidas
			contaminantes[0]+= doc.getDouble("average"+aux+"_NO2"); contaminantes[1]+= doc.getDouble("average"+aux+"_NO");
			contaminantes[2]+= doc.getDouble("average"+aux+"_NOX"); contaminantes[3]+= doc.getDouble("average"+aux+"_SO2");
			contaminantes[4]+= doc.getDouble("average"+aux+"_PM10"); contaminantes[5]+= doc.getDouble("average"+aux+"_PM25");
			contaminantes[6]+= doc.getDouble("average"+aux+"_O3"); contaminantes[7]+= doc.getDouble("average"+aux+"_BEN");
			contaminantes[8]+= doc.getDouble("average"+aux+"_XIL"); contaminantes[9]+= doc.getDouble("average"+aux+"_OXL");
		}
		Map<String, Double> listaBorrar = new HashMap<String, Double>();
		for(int i = 0; i<contaminantes.length; i++)
		{//identificamos esos contaminantes en un Map
			if(contaminantes[i]==0.0)
			{
				listaBorrar.put("average_"+lista.get(i), 0.0);
			}
		}
		List<Document> limpia = new ArrayList<Document>();
		for(Document doc:sucia)
		{//borramos de la lista esos contaminantes de los documentos
			Iterator<String> it = listaBorrar.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				doc.remove(key);
			}
			limpia.add(doc);
		}
		return limpia;
	}


	/**
	 * Dada una lista de documentos de medias mensuales elimina los contaminantes sin media (mg/m3)
	 * @param sucia - lista con posibles contaminantes sin media
	 * @return lista con contaminantes con medias
	 */
	private List<Document> limpiarMedias2(List<Document> sucia, String aux)
	{
		//0-CO,1-SH2,2-TOL
		double[] contaminantes = new double[3];
		Map<Integer, String> lista = mapContaminates2();
		for(Document doc:sucia)
		{//vemos los contaminantes de los que no se han tomado medidas
			contaminantes[0]+= doc.getDouble("average"+aux+"_CO"); contaminantes[1]+= doc.getDouble("average"+aux+"_SH2");
			contaminantes[2]+= doc.getDouble("average"+aux+"_TOL");
		}
		Map<String, Double> listaBorrar = new HashMap<String, Double>();
		for(int i = 0; i<contaminantes.length; i++)
		{//identificamos esos contaminantes en un Map
			if(contaminantes[i]==0.0)
			{
				listaBorrar.put("average_"+lista.get(i), 0.0);
			}
		}
		List<Document> limpia = new ArrayList<Document>();
		for(Document doc:sucia)
		{//borramos de la lista esos contaminantes de los documentos
			Iterator<String> it = listaBorrar.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				doc.remove(key);
			}
			limpia.add(doc);
		}
		return limpia;
	}

	/**
	 * Map de contaminantes y su ID (ug/m3)
	 * @return Map de los contaminantes y sus medias
	 */
	private Map<Integer, String> mapContaminates(){
		Map<Integer, String> lista = new HashMap<Integer, String>();
		lista.put(0, "NO2");		lista.put(5, "PM25");
		lista.put(1, "NO");			lista.put(6, "O3");
		lista.put(2, "NOX");		lista.put(7, "BEN");
		lista.put(3, "SO2");		lista.put(8, "XIL");
		lista.put(4, "PM10");		lista.put(9, "OXL");
		return lista;
	}

	/**
	 * Fusiona dos listas de medias para hacer comparacion
	 * @param medias
	 * @param mediasB
	 * @return
	 */
	private List<Document> juntarListasOpcion2(List<Document> medias, List<Document> mediasB){
		String fe1=((Document)medias.get(0).get("_id")).get("year").toString()+((Document)medias.get(0).get("_id")).get("month").toString();
		String fe2=((Document)mediasB.get(0).get("_id")).get("year").toString()+((Document)mediasB.get(0).get("_id")).get("month").toString();
		int fech1 = Integer.parseInt(fe1);
		int fech2 = Integer.parseInt(fe2);
		List<Document> buena = new ArrayList<Document>();
		if(fech1>fech2){
			for(Document med2 : mediasB)
			{
				int aux = 0;
				Document fecha2 = (Document) med2.get("_id");
				for(Document med : medias)
				{
					Document fecha = (Document) med.get("_id");
					if(fecha2.equals(fecha)){
						Document auxDoc = med2;
						String contaminantes[] = new String[med.keySet().size()];
						contaminantes = med.keySet().toArray(contaminantes);
						for(int i = 0; i<contaminantes.length-1; i++)
						{
							auxDoc.append(contaminantes[i+1], med.get(contaminantes[i+1]));
						}
						buena.add(auxDoc);//a�adimos el documento fusionado
						aux=1;
					}
				}
				if(aux==0)
				{//no hay fechas coincidentes
					buena.add(med2);
				}
			}
			for(Document med:medias)
			{
				int aux = 0;
				Document fecha = (Document) med.get("_id");
				for(Document med2 : mediasB)
				{
					Document fecha2 = (Document) med2.get("_id");
					if(fecha.equals(fecha2)){
						aux=1;
					}
				}
				if(aux==0)
				{//no hay fechas coincidentes
					buena.add(med);
				}
			}
		}else{
			for(Document med : medias)
			{
				int aux = 0;
				Document fecha2 = (Document) med.get("_id");
				for(Document med2 : mediasB)
				{
					Document fecha = (Document) med2.get("_id");
					if(fecha2.equals(fecha)){
						Document auxDoc = med;
						String contaminantes[] = new String[med2.keySet().size()];
						contaminantes = med2.keySet().toArray(contaminantes);
						for(int i = 0; i<contaminantes.length-1; i++)
						{
							auxDoc.append(contaminantes[i+1], med2.get(contaminantes[i+1]));
						}
						buena.add(auxDoc);//a�adimos el documento fusionado
						aux=1;
					}
				}
				if(aux==0)
				{//no hay fechas coincidentes
					buena.add(med);
				}
			}
			for(Document med2:mediasB)
			{
				int aux = 0;
				Document fecha = (Document) med2.get("_id");
				for(Document med : medias)
				{
					Document fecha2 = (Document) med.get("_id");
					if(fecha.equals(fecha2)){
						aux=1;
					}
				}
				if(aux==0)
				{//no hay fechas coincidentes
					buena.add(med2);
				}
			}
		}
		return buena;
	}

	/**
	 * Map de contaminantes y su ID (mg/m3)
	 * @return Map de los contaminantes y sus medias
	 */
	private Map<Integer, String> mapContaminates2(){
		Map<Integer, String> lista = new HashMap<Integer, String>();
		lista.put(0, "CO");		lista.put(1, "SH2");
		lista.put(2, "TOL");
		return lista;
	}

	/**
	 * Opcion 1- Visualizacion de tweets
	 * @param request - Parametros de la peticion
	 */
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
					palabra = quitarTildes(palabra);
					insertarPalabra(palabra, hashTag);//insertamos en el hashmap de frequencia de hashtag
					String fecha = dt1.format(collectionTweet.find(new Document("_id",tw.getString("id_tweet"))).first().getDate("fecha"));
					insertarPalabraDate(palabra,hashTagDate, fecha);//insertamos en el hashmap de evolucion por fecha
				}
			}
		}

		Map<String, Integer> sortHashtag = sortByComparator(hashTag, false);

		Map<Date, List<HashtagFreq>> newMap = new TreeMap<Date, List<HashtagFreq>>();
		newMap.putAll(hashTagDate);

		request.setAttribute("hashTagDate", newMap);
		request.setAttribute("hashTag", sortHashtag);
		request.setAttribute("tweets", tweets);
		request.setAttribute("feeling", feeling);
		client.close();
		return request;
	}

	/**
	 * Dada una palabra le elimina los caracteres raros y la pone en minusculas
	 * @param input - palabra a limpiar
	 * @return Palabra limpiada
	 */
	private String quitarTildes(String input) {
		String text = Normalizer.normalize(input, Normalizer.Form.NFD);
		return text.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	}

	/**
	 * Inserta una palabra en el hashmap de hashtags en el tiempo
	 * @param palabra
	 * @param hashDate
	 * @param fecha
	 */
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
	 * Inserta la palabra en el hashmap de frequencia de hashtags
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
	 * Ordena una lista pasada por el valor de la clave
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
