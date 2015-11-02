package com.isi.master.DATOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.csvreader.CsvReader;
import com.isi.master.funciones.Funciones;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Personas {

	private SimpleDateFormat formatoFecha;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private MongoCollection<Document> collectionTwitter;
	private Twitter twitter;

	public Personas(){
		formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		client = new MongoClient("localhost", 17017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("ciudadanos");//tomamos la coleccion de avisos
		collectionTwitter = database.getCollection("twitter");//tomamos la coleccion de tweets
		//conexion a API de twitter
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("z4mU2gFl8tACEun7mzkwKaMI8", "s2srh0iCA1HoifFldrQNYutAKFKBy7xRfmpnb5gUwPEyCy6ACB");
		twitter.setOAuthAccessToken(new AccessToken("375340426-qTNlY4mtFZZ6NdhY5x67UQAkQGOeTYs51Rwu7RqM", "9p5qKgtaKSoALFu7ZCfNZ2joaYaecI96UTMu1ZqACr3LM"));
	}
	/**
	 * Almacena en la DB las incidencias reportadas por los ciudadanos de Madrid en lo referente al medio ambiente
	 */
	//MEJORAR
	private void avisosMadrid()
	{
		String ciudad = "MADRID";//poner ciudad
		String rutaViejo = ".\\documentos\\Personas_\\avisos-madrid.csv";

		//Cambio de formato fecha y se aniade la ciudad
		try {
			//abrimos para leer el fichero 'sucio'
			CsvReader viejo = new CsvReader(rutaViejo, ';');
			viejo.readHeaders();

			List<Document> avisos = new ArrayList<Document>();

			while (viejo.readRecord())
			{//leemos el registro viejo y lo adecuamos al nuevo
				String[] fecha = viejo.get("FECHA").split("/");
				Document registro = new Document()
						.append("Fecha", formatoFecha.parse(fecha[2]+"-"+fecha[1]+"-"+fecha[0]+" 20:00:00"))
						.append("Aviso", Funciones.quitarTildes(viejo.get("TIPO")))
						.append("Descripcion", Funciones.quitarTildes(viejo.get("DESCRIPCION")))
						.append("Ciudad", Funciones.quitarTildes(ciudad))
						.append("Distrito", Funciones.quitarTildes(viejo.get("DISTRITO")));
				avisos.add(registro);
			}
			collection.insertMany(avisos);
			viejo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Almacena en la DB las quejas de los ciudadanos por twitter en lo referente al medio ambiente
	 * True: User, False: hashtag
	 */
	private void quejasTwitter(String hashtagUser){
		try {
			Query query = new Query(hashtagUser);
			query.setSince("2015-01-01");
			query.setUntil("2016-01-01");
			QueryResult result = twitter.search(query);
			
			String hashtag="";
			if(hashtagUser.startsWith("#"))
			{
				hashtag = hashtagUser;
			}
			
			List<Document> tweets = new ArrayList<Document>();
			for (Status status : result.getTweets()) {
				Document doc = new Document("_id", String.valueOf(status.getId()))
						.append("usuario", status.getUser().getScreenName())
						.append("contenido", status.getText())
						.append("localizacion", status.getUser().getLocation())
						.append("hashtag", hashtag)
						.append("fecha", status.getCreatedAt());

				if(collectionTwitter.find(new Document("_id",doc.getString("_id"))).first()==null)
				{
					tweets.add(doc);
				}
			}
			if(tweets.size()>0)
			{
				collectionTwitter.insertMany(tweets);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> getListHashUsers(){
		File twitter = null;
		FileReader fr = null;  
		BufferedReader br = null;
		List<String> hashUser = null;//lista con los usuarios y hashtags de twitter
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			twitter = new File(".\\documentos\\Personas_\\twitter.txt");
			fr = new FileReader (twitter);
			br = new BufferedReader(fr);
			hashUser = new ArrayList<String>();

			// Lectura del fichero
			String linea;
			while((linea=br.readLine())!=null)
				hashUser.add(linea);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{                    
				if( null != fr ){  
					fr.close();
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
		return hashUser;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Personas test = new Personas();

//		test.collection.drop();//vaciamos la coleccion de quejas ciudadanas AYU MADRID
//		test.avisosMadrid();//insertamos los avisos del AYU MADRID

		test.quejasTwitter("#contaminacionmadrid");
		test.quejasTwitter("#contaminacion");
		test.quejasTwitter("#calidadaire");
		test.quejasTwitter("#ruidoMadrid");
		test.quejasTwitter("#ruido");
		test.quejasTwitter("ASURCAI");
		test.quejasTwitter("FilterQueenES");
		test.client.close();//cerramos la conexion
	}
}
