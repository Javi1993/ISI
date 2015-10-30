package com.isi.master.DATOS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.csvreader.CsvReader;
import com.isi.master.funciones.Funciones;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Ciudadanos {

	private SimpleDateFormat formatoFecha;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Ciudadanos(){
		formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		client = new MongoClient("localhost", 17017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("ciudadanos");//tomamos la coleccion
	}
	/**
	 * Almacena en la DB las incidencias reportadas por los ciudadanos de Madrid en lo referente al medio ambiente
	 */
	//MEJORAR
	private void avisosMadrid()
	{
		String ciudad = "MADRID";//poner ciudad
		String rutaViejo = "C:\\Users\\javie\\OneDrive\\Documentos\\Integración de sistemas de información\\Avisos_ Ciudadanos-Madrid\\avisos-madrid.csv";

		//Cambio de formato fecha y se aniade la ciudad
		try {
			//abrimos para leer el fichero 'sucio'
			CsvReader viejo = new CsvReader(rutaViejo, ';');
			viejo.readHeaders();

			List<Document> avisos = new ArrayList<Document>();

			while (viejo.readRecord())
			{//leemos el registro viejo y lo adecuamos al nuevo
				Document registro = new Document()
						.append("Fecha", formatoFecha.parse(viejo.get("FECHA").replace("/", "-")+" 00:00:00"))
						.append("Aviso", Funciones.quitarTildes(viejo.get("TIPO")))
						.append("Descripcion", Funciones.quitarTildes(viejo.get("DESCRIPCION")))
						.append("Ciudad", Funciones.quitarTildes(ciudad))
						.append("Distrito", Funciones.quitarTildes(viejo.get("DISTRITO")));
				avisos.add(registro);
			}
			collection.insertMany(avisos);
			viejo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Almacena en la DB las quejas de los ciudadanos por twitter en lo referente al medio ambiente
	 */
	private void quejasTwitter(){
		//hacer
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ciudadanos test = new Ciudadanos();

		test.collection.drop();//vaciamos la coleccion
		test.avisosMadrid();
		test.quejasTwitter();
		test.client.close();//cerramos la conexion
	}
}
