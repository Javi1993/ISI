package com.isi.master.visualizacion.clases;

import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Contaminante {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private MongoCollection<Document> collectionAire; 

	public Contaminante() {
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("contaminantes");//tomamos la coleccion de contaminantes
		collectionAire = database.getCollection("aire");//tomamos la coleccion de medidas de aire
	}

	public void infoContaminantes(HttpServletRequest request){
		List<Document> contList = collection.find().into(new ArrayList<Document>());

		HashMap<String,List<Document>> topCity = new HashMap<String,List<Document>>();
		for(Document elem:contList)
		{
			List<Document> pipeline = asList(
					new Document("$unwind", "$Medidas"),
					new Document("$group", new Document("_id","$Provincia").append("average", new Document("$avg","$Medidas."+elem.getString("_id")))),
					new Document("$sort", new Document("average",-1)),
					new Document("$limit",5));
			List<Document> cities = collectionAire.aggregate(pipeline).into(new ArrayList<Document>());
			if(cities!=null){
				topCity.put(elem.getString("_id"), cities);
			}
		}

		request.setAttribute("topCity", topCity);
		request.setAttribute("cont", contList);

		client.close();//cerramos la conexion
	}

}
