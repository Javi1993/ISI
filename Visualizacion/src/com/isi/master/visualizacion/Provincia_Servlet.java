package com.isi.master.visualizacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import static java.util.Arrays.asList;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Provincia_Servlet
 */
@WebServlet("/provincia")
public class Provincia_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;   

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Provincia_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		List<Document> pipeline = null;
		
		if(request.getParameter("num").equals("1"))
		{
			collection = database.getCollection("tweetProv");//tomamos la coleccion de estaciones de provicias-social
			pipeline = asList(new Document("$group", new Document("_id","$_id")), 
					new Document("$sort", new Document("_id",1)), new Document("$project", new Document("$toUpper","_id")));
		}else{
			collection = database.getCollection("aire");//tomamos la coleccion de estaciones de aire
			pipeline = asList(new Document("$group", new Document("_id","$Provincia")), 
					new Document("$sort", new Document("_id",1)));
		}
		List<Document> provincias = collection.aggregate(pipeline).into(new ArrayList<Document>());
		request.setAttribute("provincias", provincias);
		request.setAttribute("op", request.getParameter("num"));

		client.close();//cerramos la conexion
		request.getRequestDispatcher("/provincia.jsp").forward(request, response);
	}

}
