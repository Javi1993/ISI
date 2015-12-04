package com.isi.master.DATOS;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.isi.master.funciones.Funciones;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Aire {

	private SimpleDateFormat formatoFecha;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	private MongoCollection<Document> limits;
	private List<Document> limites;

	public Aire(){
		formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		client = new MongoClient("localhost", 27017);//conectamos
		database = client.getDatabase("test");//elegimos bbdd
		collection = database.getCollection("aire");//tomamos la coleccion de estaciones de aire
		limits = database.getCollection("contaminantes");//tomamos la coleccion de contaminantes
		insertarLimites();//insertamos los limites de ciertos contaminantes en la base de datos
		limites = limits.find(new Document()).into(new ArrayList<Document>());
	}

	/**
	 * Recibe el documento JSON con la informacion de una estacion de aire y un array con todas las medidas,
	 * Transforma todas estas medidas a decimal y las aniade al documento JSON de la estacion
	 * @param medida - documento con la info de la estacion
	 * @param mediciones - conjunto de medidas de diversos elementos
	 * @return Documento estructurado con las medidas de esa estacion de aire
	 */
	private Document cogerMedidasDecimal(Document medida, String[] mediciones) {
		String[] aux;
		int cnt = 0;//controlamos que haya medidas de algo para esa fecha
		for(int i = 0; i<mediciones.length; i++)
		{
			mediciones[i] = mediciones[i].trim();
			aux = mediciones[i].split(":");
			if(aux.length>1&&Funciones.isNumeric(aux[1],aux[0]))
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
	 * Inserta en una coleccion de mongoDB los limites de los contaminantes
	 */
	private void insertarLimites()
	{
		try {
			CsvReader limites = new CsvReader("./documentos/Aire_/contaminantes-limites.csv", ';');
			limites.readHeaders();

			limits.drop();

			List<Document> lm = new ArrayList<Document>();
			while (limites.readRecord())
			{
				lm.add(new Document("_id",limites.get("Elemento"))
						.append("Nombre", limites.get("Nombre"))
						.append("MA", Float.parseFloat(limites.get("Muy Alto")))
						.append("A", Float.parseFloat(limites.get("Alto")))
						.append("M", Float.parseFloat(limites.get("Medio")))
						.append("B", Float.parseFloat(limites.get("Bajo")))
						.append("MB", Float.parseFloat(limites.get("Muy Bajo")))
						.append("Unidades", limites.get("Unidades")));
			}
			limits.insertMany(lm);
			limites.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Coloca las medidas del array en el CSV recibido para la estacion actual
	 * @param file - Archivo CSV
	 * @param mediciones -- Medidas de una estacion
	 */
	private void cogerMedidasCSV (CsvWriter file, String[] mediciones) {
		String[] aux;
		try {
			for(int i = 0; i<mediciones.length; i++)
			{
				mediciones[i] = mediciones[i].trim();
				aux = mediciones[i].split(":");
				if(aux.length>1&&Funciones.isNumeric(aux[1],aux[0]))
				{//el dato es valido
					if(!aux[1].equals("")&&aux[1]!=null)
					{
						Document doc = null;
						for(Document lm : limites)
						{
							if(lm.get("_id").equals(aux[0]))
							{
								doc = lm;//cogemos el doc con los limites para ese contaminate
								break;
							}
						}
						if(doc!=null)
						{//categorizamos los datos de los contaminantes
							if(Double.parseDouble(aux[1])>=doc.getDouble("MA")){
								file.write("1. MUY ALTO");
							}else if((Double.parseDouble(aux[1])<doc.getDouble("MA"))
									&&(Double.parseDouble(aux[1])>=doc.getDouble("A"))){
								file.write("2. ALTO");
							}else if((Double.parseDouble(aux[1])<doc.getDouble("A"))
									&&(Double.parseDouble(aux[1])>=doc.getDouble("M"))){
								file.write("3. MEDIO");
							}else if((Double.parseDouble(aux[1])<doc.getDouble("M"))
									&&(Double.parseDouble(aux[1])>=doc.getDouble("B"))){
								file.write("4. BAJO");
							}else{
								file.write("5. MUY BAJO");
							}
						}
					}
				}else
				{//si no tiene valor para ese elemnto insentamos vacio
					file.write("");		
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					"PM10:"+medidas.get("PM10"),"PM25:"+medidas.get("PM25"),"CO:"+medidas.get("CO"),"O3:"+medidas.get("O3"),
					"SH2:"+medidas.get("SH2"),"BEN:"+medidas.get("BEN"),"TOL:"+medidas.get("TOL"),"XIL:"+medidas.get("XIL"),"OXL:"+medidas.get("OXL")};
			return mediciones;
		}catch(IOException e)
		{
			return null;
		}
	}

	/**
	 * Inserta todas las estaciones de calidad de aire con sus medidas en la DB
	 */
	@SuppressWarnings("serial")
	private void insertarEstaciones()
	{
		try {
			final CsvReader estaciones = new CsvReader("./documentos/Aire_/estaciones.csv", ';');
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

				CsvReader medidas = new CsvReader("./documentos/Aire_/datos/"+Funciones.quitarTildes(estaciones.get("Nombre"))+".csv", ';');
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

					medida = cogerMedidasDecimal(medida, parametrosMedicion(medidas));//completamos las medidas
					if(medida!=null){valores.add(medida);}//añadimos la medida a la lista de la estacion
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
	 * Crea las cabeceras del fichero CSV usado para cartoDB
	 * @param file - CSV creado
	 */
	private void cogerCabeceras(CsvWriter file)
	{
		try {
			file.write("Nombre");
			file.write("Provincia");
			file.write("Longitud");
			file.write("Latitud");
			file.write("Fecha");
			file.write("SO2");
			file.write("NO");
			file.write("NO2");
			file.write("NOX");
			file.write("PM10");
			file.write("PM25");
			file.write("CO");
			file.write("O3");
			file.write("SH2");
			file.write("BEN");
			file.write("TOL");
			file.write("XIL");
			file.write("OXL");
			file.endRecord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea un unico CSV con todas las estaciones almacenadas y sus respectivas medidas
	 */
	private void CSVgrande(){
		try {
			//fichero de estaciones
			final CsvReader estaciones = new CsvReader("./documentos/Aire_/estaciones.csv", ';');
			estaciones.readHeaders();

			//fichero que recogera las estaciones y sus medidas
			CsvWriter csvOutput = new CsvWriter(new FileWriter("./documentos/Aire_/estaciones-medidas.csv", false), ';');
			cogerCabeceras(csvOutput);
			while (estaciones.readRecord())
			{	
				//fichero con las medidas de la estacion X
				CsvReader medidas = new CsvReader("./documentos/Aire_/datos/"+Funciones.quitarTildes(estaciones.get("Nombre"))+".csv", ';');
				medidas.readHeaders();

				String ciudad = Funciones.obtenerCiudad(Double.valueOf(estaciones.get("Latitud")), Double.valueOf(estaciones.get("Longitud")));
				String[] cpp = ciudad.split("_");
				if(Funciones.isNumeric(cpp[0].split("\\s",2)[0]))
				{
					cpp[0] = cpp[0].split("\\s",2)[1];
				}

				while (medidas.readRecord())
				{	
					csvOutput.write(estaciones.get("Nombre"));
					csvOutput.write(cpp[1]);
					csvOutput.write(estaciones.get("Longitud"));
					csvOutput.write(estaciones.get("Latitud"));

					String hora = medidas.get("Hora");
					String[] fechaFormato = medidas.get("Fecha").split("/");//damos formato a la fecha
					if(hora.equals(""))
					{
						csvOutput.write(fechaFormato[2]+"-"+fechaFormato[1]+"-"+fechaFormato[0]);
					}else{
						csvOutput.write(fechaFormato[2]+"-"+fechaFormato[1]+"-"+fechaFormato[0]+" "+hora);
					}

					cogerMedidasCSV(csvOutput, parametrosMedicion(medidas));
					csvOutput.endRecord();
				}
				medidas.close();//cerramos el CSV de la estacion X y sus medidas
			}
			csvOutput.close();//cerramos el CSV de las estaciones y sus medidas
			estaciones.close();//cerramos el CSV de las estaciones
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Anade indices, borra registros innecesarios y corregi traduce valores de atributos en otras lenguas de la DB
	 */
	private void optimizarDB(){
		//Algunas estaciones no midieorn para este tiempo u hora
		collection.deleteMany(new Document("Medidas",new Document("$size",0)));

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
		collection.updateMany(new Document("Provincia", "CDAD. REAL"), 
				new Document("$set", new Document("Provincia", "CIUDAD REAL")));
		collection.updateMany(new Document("Provincia", "A CORUNA"), 
				new Document("$set", new Document("Provincia", "LA CORUNA")));
	}

	public static void main(String[] args) {
		//Años 2014-2015
		//HACERSE GUI EN UNA CLASE APARTE PARA ELEGIR OPCION INDEXAR ESTACIONES, RECOLECTAR TWEETS ETC!
		Aire test = new Aire();

		test.CSVgrande();//genera un CSV con todas las estaciones y sus medidas

		test.collection.drop();//vaciamos la coleccion de estaciones de aire
		test.insertarEstaciones();//insertamos todas las estaciones con sus medidas		

		test.client.close();//cerramos la conexion
	}
}
