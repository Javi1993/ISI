package com.isi.master.DATOS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Personas {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collectionTwitter;
	private Twitter twitter;

	public Personas(){
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collectionTwitter = database.getCollection("twitter");//tomamos la coleccion de tweets
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
			String sCurrentLine;
			br = new BufferedReader(new FileReader("./documentos/Personas_/twitter.txt"));//cogemos los hashtags y usuarios
			List<Document> tweets = new ArrayList<Document>();

			List<String> hashId = new ArrayList<String>();
			for(Document id : collectionTwitter.find().projection(new Document("_id",1)).into(new ArrayList<Document>())){
				hashId.add(id.getString("_id"));
			}
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
							&&(hashId.contains(doc.getString("_id"))))
					{//anadimos a la lista el tweet si no esta almacenado
						tweets.add(doc);
					}
				}
			}
			if(tweets.size()>0)
			{//metemos la lista de tweets a la DB
				collectionTwitter.insertMany(tweets);
			}
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Personas test = new Personas();

		test.quejasTwitter();
		test.client.close();//cerramos la conexion
	}
}
