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
		List<Document> cont = collection.find().into(new ArrayList<Document>());
		request.setAttribute("cont", cont);
		client.close();//cerramos la conexion
		request.getRequestDispatcher("/contaminante.jsp").forward(request, response);
	}

}
