package com.isi.master.visualizacion.clases;

import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Provincia {

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;   

	public Provincia() {
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
	}

	public void selectProvincia(HttpServletRequest request){
		List<Document> pipeline = null;
		if(request.getParameter("num").equals("1"))
		{
			collection = database.getCollection("tweetProv");//tomamos la coleccion de estaciones de provicias-social
			pipeline = asList(new Document("$group", new Document("_id","$_id")), 
					new Document("$sort", new Document("_id",1)));
		}else{
			collection = database.getCollection("aire");//tomamos la coleccion de estaciones de aire
			pipeline = asList(new Document("$group", new Document("_id","$Provincia")), 
					new Document("$sort", new Document("_id",1)));
		}
		List<Document> provincias = collection.aggregate(pipeline).into(new ArrayList<Document>());
		request.setAttribute("provincias", provincias);
		request.setAttribute("op", request.getParameter("num"));
		client.close();//cerramos la conexion
	}

}
