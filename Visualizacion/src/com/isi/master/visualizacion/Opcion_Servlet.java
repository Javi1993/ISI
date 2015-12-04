package com.isi.master.visualizacion;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class Opcion3_Servlet
 */
@WebServlet("/opcion")
public class Opcion_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private MongoCollection<Document> collectionAire;
	
    /**
     * Default constructor. 
     */
    public Opcion_Servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("cartodb");//tomamos la coleccion de mapas de cartdb
		collectionAire = database.getCollection("aire");//tomamos la coleccion de mapas de aire
		
		if(request.getParameter("num").equals("3"))
		{
			//first en este caso que solo hay un mapa, hacerlo escalar
			Document map = collection.find(new Document("_id", request.getParameter("provincia"))).first();
			request.setAttribute("NO2", ((Document)map.get("Mapas")).get("NO2").toString());
			List<Document> pipeline = asList(new Document("$match", new Document("Provincia",request.getParameter("provincia"))),
					new Document("$unwind","$Medidas"),
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha"))).append("average_NO2", new Document("$avg","$Medidas.NO2"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			request.setAttribute("medias",collectionAire.aggregate(pipeline).into(new ArrayList<Document>()));
		}
		//EN LA AGREGACION FALTA AÑADIR TODOS LOS ELEMNTOS Q PUEDEN MEDIRSE
		
		client.close();//cerramos la conexion
		request.getRequestDispatcher("/no-sidebar.jsp").forward(request, response);
	}

}
