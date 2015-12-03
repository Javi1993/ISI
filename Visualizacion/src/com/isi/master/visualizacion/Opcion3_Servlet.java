package com.isi.master.visualizacion;

import java.io.IOException;
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
@WebServlet("/opcion3")
public class Opcion3_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
    /**
     * Default constructor. 
     */
    public Opcion3_Servlet() {
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
		
		//recuperar provincia del request.getParameter!!
		//buscar en base de datos los mapas de esa provicnia
		//devolver Document en la response con cada mapa y su elemento correspondiente
		//devolver medias anuales de todos los elementos en la resposne (agregacion collection aire)
		
		client.close();//cerramos la conexion
	}

}
