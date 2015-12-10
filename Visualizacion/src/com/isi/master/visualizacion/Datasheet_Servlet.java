package com.isi.master.visualizacion;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * Servlet implementation class Datashet_Servlet
 */
@WebServlet("/datasheet")
public class Datasheet_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Datasheet_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if(request.getParameter("op").equals("1"))
		{
			client = new MongoClient("localhost", 27017);//conectamos
			database = client.getDatabase("test");//elegimos bbdd
			collection = database.getCollection("aire");//tomamos la coleccion de mapas de aire
			List<Document> pipeline = asList(
					new Document("$group", new Document("_id", "$Provincia")
							.append("estaciones", new Document("$addToSet","$Estacion"))), 
					new Document("$sort", new Document("_id",1)));
			List<Document> prov_estaciones = collection.aggregate(pipeline).into(new ArrayList<Document>());

			request.setAttribute("list", prov_estaciones);
			request.getRequestDispatcher("/datasheet.jsp").forward(request, response);
		}else{
			dowmload(request, response, request.getParameter("csv"));
		}

	}

	protected void dowmload(HttpServletRequest request, HttpServletResponse response, String csvName) throws ServletException, IOException {
		response.setContentType("text/csv");//indicamos el tipo de archivo
		response.setHeader("Content-Disposition", "attachment;filename="+csvName+".csv");//dialogo de descarga

		File my_file = new File("/home/isi/git/ISI/DATOS/documentos/Aire_/datos"+File.separator+csvName+".csv");

		//Realizamos la descarga
		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
	}

}
