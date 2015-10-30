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

public class Aire {

	private SimpleDateFormat formatoFecha;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Aire(){
		formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		client = new MongoClient("localhost", 17017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("aire");//tomamos la coleccion
	}

	/**
	 * Recibe el documento con la informacion de una estacion de aire y un array con todas las medidas,
	 * Transforma todas estas medidas a decimal y las aniade al documento de la estacion
	 * @param medida - documento con la info de la estacion
	 * @param mediciones - conjunto de medidas de diversos elementos
	 * @return Documento estructurado con las medidas de esa estacion de aire
	 */
	private static Document cogerMedidasDecimal(Document medida, String[] mediciones) {
		String[] aux;
		int cnt = 0;//controlamos que haya medidas de algo para esa fecha
		for(int i = 0; i<mediciones.length; i++)
		{
			mediciones[i] = mediciones[i].trim();
			aux = mediciones[i].split(":");
			if(aux.length>1&&Funciones.isNumeric(aux[1]))
			{
				medida.append(aux[0], Float.valueOf(aux[1]));
				cnt++;
			}
		}
		if(cnt==0)
		{//la medida de esa fecha no contenia valores validos
			return null;
		}else{
			return medida;
		}
	}

	/**
	 * Almacena el valor para cada uno de las elementos medidos
	 * @param medidas - documento de medidas leido
	 * @return - Array de string con las medidas y valores que recoge ese documento
	 */
	private static String[] parametrosMedicion(CsvReader medidas)
	{
		try{
			String[] mediciones = {"SO2:"+medidas.get("SO2"),"NO:"+medidas.get("NO"),"NO2:"+medidas.get("NO2"),"NOX:"+medidas.get("NOX"),
					"NH3:"+medidas.get("NH3"),"PM10:"+medidas.get("PM10"),"PM25:"+medidas.get("PM25"),"PST:"+medidas.get("PST"),
					"CO:"+medidas.get("CO"),"CH4:"+medidas.get("CH4"),"O3:"+medidas.get("O3"),"SH2:"+medidas.get("SH2"),"BEN:"+medidas.get("BEN"),
					"EBN:"+medidas.get("EBN"),"TOL:"+medidas.get("TOL"),"XIL:"+medidas.get("XIL"),"OXL:"+medidas.get("OXL"),"dd:"+medidas.get("dd"),
					"vv:"+medidas.get("vv"),"TMP:"+medidas.get("TMP"),"HR:"+medidas.get("HR"),"PRB:"+medidas.get("PRB"),"RS:"+medidas.get("RS"),
					"RUV:"+medidas.get("RUV"),"LL:"+medidas.get("LL")};
			return mediciones;
		}catch(IOException e)
		{
			return null;
		}
	}



	/**
	 * Inserta todas las estaciones de calidad de aire con sus medidas en la DB
	 */
	//DE MOMENTO SOLO CASTILLALEON, CANARIAS, VALENCIA, MADRID, GIJON, ARAGON, BALEARES y EUSKADI
	@SuppressWarnings("serial")
	private void insertarEstaciones()
	{
		try {
			final CsvReader estaciones = new CsvReader(".\\documentos\\Aire_\\estaciones.csv", ';');
			estaciones.readHeaders();

			List<Document> registros = new ArrayList<Document>();//lista con las estaciones y sus medidas
			while (estaciones.readRecord())
			{	
				String ciudad = Funciones.obtenerCiudad(Double.valueOf(estaciones.get("Latitud")), Double.valueOf(estaciones.get("Longitud")));

				String[] cpp = ciudad.split("_");
				if(Funciones.isNumeric(cpp[0].split("\\s",2)[0]))
				{
					cpp[0] = cpp[0].split("\\s",2)[1];
				}


				Document estacion = new Document()
						.append("Estacion", Funciones.quitarTildes(estaciones.get("Nombre")))
						.append("Localizacion", new Document("type","Point")
								.append("coordinates", new ArrayList<Double>(){{add(Double.valueOf(estaciones.get("Longitud")));add(Double.valueOf(estaciones.get("Latitud")));}}))
						.append("Ciudad", Funciones.quitarTildes(cpp[0]))
						.append("Provincia", Funciones.quitarTildes(cpp[1]))
						.append("Pais", Funciones.quitarTildes(cpp[2]));

				CsvReader medidas = new CsvReader(".\\documentos\\Aire_\\datos\\"+Funciones.quitarTildes(estaciones.get("Nombre"))+".csv", ';');
				medidas.readHeaders();

				List<Document> valores = new ArrayList<Document>();//lista con las medidas de una estacion
				while (medidas.readRecord())
				{	
					String hora = medidas.get("Hora");
					Document medida = new Document();
					String[] fechaFormato = medidas.get("Fecha").split("/");//damos formato a la fecha
					if(hora.equals(""))
					{
						//creamos el documeto con la fecha y hora por defecto
						medida.append("Fecha", formatoFecha.parse(fechaFormato[2]+"-"+fechaFormato[1]+"-"+fechaFormato[0]+" 23:00:00"));
					}else{
						//creamos el documeto con la fecha y hora correcta
						medida.append("Fecha", formatoFecha.parse(fechaFormato[2]+"-"+fechaFormato[1]+"-"+fechaFormato[0]+" "+medidas.get("Hora")));
					}

					//añadimos la medida a la lista de la estacion

					medida = cogerMedidasDecimal(medida, parametrosMedicion(medidas));//completamos las medidas
					if(medida!=null){valores.add(medida);}//hay medidas reales para esa fecha
				}
				estacion.append("Medidas", valores);//aniadimos a la estacion todas sus medidas
				registros.add(estacion);//aniadimos la estacion completa a la lista de estaciones
				medidas.close();//cerramos el CSV de las medidas
			}
			collection.insertMany(registros);//insertamos las estaciones
			optimizarDB();//optimizamos la coleccion
			estaciones.close();//cerramos el CSV de las estaciones
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Anade indices, borra registros innecesarios y corregi traduce valores de atributos en otras lenguas de la DB
	 */
	private void optimizarDB(){
		//Algunas estaciones no midieorn para este tiempo u hora
		collection.deleteMany(new Document("Medidas",new Document("$size",0)));
		//añadir el que borra solo un doc de Medidas!

		//Creamos indice para agilizar consultas
		collection.createIndex(new Document("Estacion",1));
		collection.createIndex(new Document("Ciudad",1));
		collection.createIndex(new Document("Provincia",1));
		collection.createIndex(new Document("Ciudad",1).append("Estacion", 1));

		//Pasamos el nombre de ciertas provincias en lengua autonomica al castellano
		collection.updateMany(new Document("Provincia", "ARABA"), 
				new Document("$set", new Document("Provincia", "ALAVA")));
		collection.updateMany(new Document("Provincia", "BIZKAIA"), 
				new Document("$set", new Document("Provincia", "VIZCAYA")));
		collection.updateMany(new Document("Provincia", "GIPUZKOA"), 
				new Document("$set", new Document("Provincia", "GUIPUZCOA")));
		collection.updateMany(new Document("Provincia", "BALEARIC ISLANDS"), 
				new Document("$set", new Document("Provincia", "ISLAS BALEARES")));
		collection.updateMany(new Document("Provincia", "ILLES BALEARS"), 
				new Document("$set", new Document("Provincia", "ISLAS BALEARES")));
	}

	public static void main(String[] args) {
		//Años 2014-2015
		//Usar coleccion 'aireTest' para pruebas, solo usar 'aire' cuando el metodo insert bien
		Aire test = new Aire();

		test.collection.drop();//vaciamos la coleccion
		test.insertarEstaciones();		
		test.client.close();//cerramos la conexion
	}
}
