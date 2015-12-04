package com.isi.master.visualizacion;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		client = new MongoClient("localhost", 17017);//conectamos
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
					new Document("$group", new Document("_id",new Document("month", new Document("$month","$Medidas.Fecha")).append("year", new Document("$year","$Medidas.Fecha")))
							.append("average_NO2", new Document("$avg","$Medidas.NO2")).append("average_NO", new Document("$avg","$Medidas.NO")).append("average_NOX", new Document("$avg","$Medidas.NOX"))
							.append("average_SO2", new Document("$avg","$Medidas.SO2")).append("average_PM10", new Document("$avg","$Medidas.PM10")).append("average_PM25", new Document("$avg","$Medidas.PM25"))
							.append("average_O3", new Document("$avg","$Medidas.O3")).append("average_BEN", new Document("$avg","$Medidas.BEN")).append("average_XIL", new Document("$avg","$Medidas.XIL"))
							.append("average_OXL", new Document("$avg","$Medidas.OXL"))), 
					new Document("$sort", new Document("_id.year",1).append("_id.month", 1)));
			List<Document> medias = collectionAire.aggregate(pipeline).into(new ArrayList<Document>());
			request.setAttribute("medias",limpiarMedias(medias));
		}
		//EN LA AGREGACION FALTA AÑADIR LOS 3 ELEMENTOS DE MG/M3, hacer otro grafico y agregacion para ellos
		client.close();//cerramos la conexion
		
		request.setAttribute("provincia",request.getParameter("provincia"));
		request.getRequestDispatcher("/no-sidebar.jsp").forward(request, response);
	}

	/**
	 * Dada una lista de documentos de medias mensuales elimina los contaminantes sin media
	 * @param sucia - lista con posibles contaminantes sin media
	 * @return lista con contaminantes con medias
	 */
	private List<Document> limpiarMedias(List<Document> sucia)
	{
		//0-NO2,1-NO,2-NOX,3-SO2,4-PM10,5-PM25,6-O3,7-BEN,8-XIL,9-OXL
		int[] contaminantes = new int[10];
		Map<Integer, String> lista = mapContaminates();
		for(Document doc:sucia)
		{//vemos los contaminantes de los que no se han tomado medidas
			contaminantes[0]+= doc.getDouble("average_NO2"); contaminantes[1]+= doc.getDouble("average_NO");
			contaminantes[2]+= doc.getDouble("average_NOX"); contaminantes[3]+= doc.getDouble("average_SO2");
			contaminantes[4]+= doc.getDouble("average_PM10"); contaminantes[5]+= doc.getDouble("average_PM25");
			contaminantes[6]+= doc.getDouble("average_O3"); contaminantes[7]+= doc.getDouble("average_BEN");
			contaminantes[8]+= doc.getDouble("average_XIL"); contaminantes[9]+= doc.getDouble("average_OXL");
		}
		Map<String, Double> listaBorrar = new HashMap<String, Double>();
		for(int i = 0; i<contaminantes.length; i++)
		{//identificamos esos contaminantes en un Map
			if(contaminantes[i]==0.0)
			{
				listaBorrar.put("average_"+lista.get(i), 0.0);
			}
		}
		List<Document> limpia = new ArrayList<Document>();
		for(Document doc:sucia)
		{//borramos de la lista esos contaminantes de los documentos
			Iterator<String> it = listaBorrar.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				doc.remove(key,listaBorrar.get(key));

			}
			limpia.add(doc);
		}
		return limpia;
	}

	/**
	 * Map de contaminantes y su ID
	 * @return Map de los contaminantes y sus medias
	 */
	private Map<Integer, String> mapContaminates(){
		Map<Integer, String> lista = new HashMap<Integer, String>();
		lista.put(0, "NO2");		lista.put(5, "PM25");
		lista.put(1, "NO");			lista.put(6, "O3");
		lista.put(2, "NOX");		lista.put(7, "BEN");
		lista.put(3, "SO2");		lista.put(8, "XIL");
		lista.put(4, "PM10");		lista.put(9, "OXL");
		return lista;
	}
}
