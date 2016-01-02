package com.isi.master.visualizacion.servlets;

import static java.util.Arrays.asList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Contaminante_Servlet
 */
@WebServlet("/contaminante")
public class Contaminante_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private MongoCollection<Document> collectionAire; 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Contaminante_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("contaminantes");//tomamos la coleccion de contaminantes
		collectionAire = database.getCollection("aire");//tomamos la coleccion de medidas de aire
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
		request.getRequestDispatcher("/contaminante.jsp").forward(request, response);
		client.close();//cerramos la conexion
	}
}
