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
@WebServlet("/opcion")
public class Opcion_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
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
		
		if(request.getParameter("num").equals("3"))
		{
			//first en este caso que solo hay un mapa, hacerlo escalar
			Document map = collection.find(new Document("_id", request.getParameter("provincia"))).first();
			request.setAttribute("NO2", ((Document)map.get("Mapas")).get("NO2").toString());
		}
		//devolver Document en la response con cada mapa y su elemento correspondiente
		//devolver medias mensuales de todos los elementos en la resposne (agregacion collection aire)
		
		client.close();//cerramos la conexion
		request.getRequestDispatcher("/no-sidebar.jsp").forward(request, response);
	}

}
