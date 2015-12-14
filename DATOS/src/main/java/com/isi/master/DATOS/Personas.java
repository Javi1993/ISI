package com.isi.master.DATOS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import com.isi.master.meaningcloudAPI.topicsextraction.*;

public class Personas {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collectionTwitter;
	private MongoCollection<Document> collectionTweets;
	private Twitter twitter;

	public Personas(){
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collectionTwitter = database.getCollection("twitter");//tomamos la coleccion de tweets
		collectionTweets = database.getCollection("tweetProv");//tomamos la coleccion de tweets
		//conexion a API de twitter
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("z4mU2gFl8tACEun7mzkwKaMI8", "s2srh0iCA1HoifFldrQNYutAKFKBy7xRfmpnb5gUwPEyCy6ACB");
		twitter.setOAuthAccessToken(new AccessToken("375340426-qTNlY4mtFZZ6NdhY5x67UQAkQGOeTYs51Rwu7RqM", "9p5qKgtaKSoALFu7ZCfNZ2joaYaecI96UTMu1ZqACr3LM"));
	}

	/**
	 * Almacena en la DB las quejas de los ciudadanos por twitter en lo referente al medio ambiente
	 */
	@SuppressWarnings("serial")
	private void quejasTwitter(){
		BufferedReader br = null;
		try {
			String sCurrentLine;//string que almacena los temas, hashtag y cuentas de usuario sobre los que buscar tweets
			List<String> hashId = new ArrayList<String>();//lista que almacena los IDs de los tweets guardados en la DB
			MongoCursor<Document> cursor = collectionTwitter.find().projection(new Document("_id",1)).iterator();
			try{
				while(cursor.hasNext()){
					hashId.add(cursor.next().getString("_id"));//almacenados ID
				}
			}finally{
				cursor.close();
			}

			br = new BufferedReader(new FileReader("./documentos/Personas_/twitter.txt"));//cogemos los hashtags y usuarios
			List<Document> tweets = new ArrayList<Document>();//lista con el Document del tweet
			while ((sCurrentLine = br.readLine()) != null) {
				Query query = new Query(sCurrentLine+" -filter:retweets");
				query.setSince("2015-01-01");
				query.setUntil("2016-01-01");
				QueryResult result = twitter.search(query);

				for (Status status : result.getTweets()) {
					List<String> aux = new ArrayList<String>();
					HashtagEntity[] he = status.getHashtagEntities();
					for(int i=0; i<he.length; i++)
					{//anadimos las hashtags del mensaje si los hay
						aux.add(he[i].getText());
					}

					Document doc = new Document("_id", String.valueOf(status.getId()))
							.append("usuario", status.getUser().getScreenName())
							.append("contenido", status.getText())
							.append("hashtag", aux)
							.append("fecha", status.getCreatedAt())
							.append("lang", status.getLang());

					//insertamos valores de localizacion si no son nulos
					if(status.getPlace()!=null){//si hay place se añade
						doc.append("place",new Document()
								.append("ciudad", status.getPlace().getName())
								.append("pais", status.getPlace().getCountry()));
					}
					//si hay localizacion se añade
					if(!status.getUser().getLocation().equals("")){doc.append("localizacion", status.getUser().getLocation());}
					if(status.getGeoLocation()!=null){//si hay geolocation la añadimos
						final double lon = status.getGeoLocation().getLongitude();
						final double lat = status.getGeoLocation().getLatitude();
						doc.append("geo",new Document("type","Point")
								.append("coordinates",new ArrayList<Double>(){{add(lon);add(lat);}}));
					}
					if((!tweets.contains(doc))&&(status.getLang().equals("es"))
							&&(!hashId.contains(doc.getString("_id"))))
					{//anadimos a la lista el tweet si no esta almacenado
						tweets.add(doc);
					}
				}
			}
			if(tweets.size()>0)
			{//metemos la lista de tweets a la DB
				collectionTwitter.insertMany(tweets);
			}
			limpiarTweets();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Elimina los tweets que no sean de España o no estén en español
	 */
	private void limpiarTweets()
	{
		collectionTwitter.deleteMany(Filters.and(Filters.ne("place", null), Filters.exists("place", true), Filters.ne("place.pais", "España")));
		collectionTwitter.deleteMany(Filters.and(Filters.ne("lang", "es"), Filters.exists("lang", true)));

		String sCurrentLine;//string que almacena los paises y zonas de habla hispana de los que puede haber tweets
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./documentos/Personas_/paises.txt"));//cogemos los paises y zonas de habla hispana
			while ((sCurrentLine = br.readLine()) != null) {//borramos tweets de fuera de Espana
				collectionTwitter.deleteMany(Filters.regex("localizacion", sCurrentLine, "i"));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clasifica los tweets segun la provincia, en caso no saber la localizacion se descarta
	 */
	private void clasificarTweets(){
		MongoCursor<Document> cursor = collectionTwitter.find().iterator();
		try{
			while(cursor.hasNext()){
				Document tweet = cursor.next();
				if(tweet.get("contenido")!=null){
					pasarContenidoTweet(tweet.getString("contenido").replaceAll("#", ""), tweet);
				}
				else if(tweet.get("place")!=null)
				{
					pasarContenidoTweet(((Document)tweet.get("place")).getString("ciudad"), tweet);

				}else if(tweet.get("localizacion")!=null){
					pasarContenidoTweet(tweet.getString("localizacion"), tweet);
				}
			}
		}finally{
			cursor.close();
		}
	}

	/**
	 * 
	 * @param txt
	 * @param tweet
	 * @return
	 */
	private boolean  pasarContenidoTweet(String txt, Document tweet)
	{
		List<String> provincias = TopicsClient.recibirTweet(txt);
		if(provincias.size()>0)
		{
			for(String prov:provincias)
			{
				collectionTweets.updateOne(new Document("_id", prov), new Document("$addToSet", new Document("tweets", new Document("id_tweet", tweet.getString("_id")).append("user", tweet.getString("usuario")))), new UpdateOptions().upsert(true));	
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Personas test = new Personas();

		//		test.quejasTwitter();//guardamos tweets en base a unas consultas
		test.clasificarTweets();//clasificamos los tweets guardados por provincia

		test.client.close();//cerramos la conexion
	}
}
