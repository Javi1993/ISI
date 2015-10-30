package com.isi.master.funciones;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Example {
	/**
	 * Ejemplo de lectura de un CSV
	 */
	@SuppressWarnings("unused")
	private static void ejemploRead()
	{
		try {

			CsvReader products = new CsvReader("ruta", ';');

			products.readHeaders();

			while (products.readRecord())
			{
				String productID = products.get("FECHA");
				String productName = products.get("TIPO");
				String supplierID = products.get("DESCRIPCION");
				String categoryID = products.get("DISTRITO");

				// perform program logic here
				System.out.println(productID + " " + productName + " " + supplierID + " " + categoryID);
			}

			products.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ejemplo de escritura en un CSV
	 */
	@SuppressWarnings("unused")
	private static void ejemploEscritura(){
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter("ruta", true), ';');

			// else assume that the file already has the correct header line

			// write out a few records
			csvOutput.write("PEPE");
			csvOutput.write("PEPA");
			csvOutput.write("PEPO");
			csvOutput.write("PEPU");
			csvOutput.endRecord();

			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ejemplo de lectura y escritura en dos CSV a la vez
	 */
	@SuppressWarnings("unused")
	private static void ejemploLecturaMasEscritura(){
		String ciudad = "MADRID";//poner ciudad
		String rutaViejo = "rutaViejo";
		String rutaNuevo = "rutaNuevo";

		//Cambio de formato fecha y se aniade la ciudad
		try {
			//abrimos para leer el fichero 'sucio'
			CsvReader viejo = new CsvReader(rutaViejo, ';');
			viejo.readHeaders();

			CsvWriter csvOutput = new CsvWriter(new FileWriter(rutaNuevo, false), ';');

			//Cabecera
			csvOutput.write("Fecha");
			csvOutput.write("Aviso");
			csvOutput.write("Descripcion");
			csvOutput.write("Ciudad");
			csvOutput.write("Distrito");
			csvOutput.endRecord();

			while (viejo.readRecord())
			{//leemos el registro viejo y lo adecuamos al nuevo
				csvOutput.write(viejo.get("FECHA").replace("/", "-"));
				csvOutput.write(viejo.get("TIPO"));
				csvOutput.write(viejo.get("DESCRIPCION"));
				csvOutput.write(ciudad);
				csvOutput.write(viejo.get("DISTRITO"));
				csvOutput.endRecord();
			}

			viejo.close();
			csvOutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
