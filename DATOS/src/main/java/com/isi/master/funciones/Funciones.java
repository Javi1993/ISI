package com.isi.master.funciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import com.csvreader.CsvReader;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public class Funciones {
	/**
	 * Quita las tildes de una palabra/texto y la devulve en mayuscula
	 * @param input - palabra/texto a cambiar
	 * @return Resultado en mayuscula y sin tildes
	 */
	public static String quitarTildes(String input) {
		String text = Normalizer.normalize(input, Normalizer.Form.NFD);
		return text.replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	}

	/**
	 * Comprueba si el contenido de un String es un valor numerico
	 * @param s - string a evaluar
	 * @param magnitud - magnitud de la medida, la TMP(temperatura) puede ser negativa
	 * @return Booleano indicando si es numerico (true) o no (false)
	 */
	public static boolean isNumeric(String s,String... magnitud) { 
		if(s.matches("[-+]?\\d*\\.?\\d+"))
		{
			if(Double.valueOf(s)>0||magnitud.equals("TMP")){//el valor es correcto
				return true;
			}
		}
		return false;//el valor no es numerico o no es positivo(si es una magnitud)
	} 

	/**
	 * Dada unas coordenadas devuelve la ciudad, provincia y pais correspondiente
	 * @param latitud
	 * @param longitud
	 * @return
	 */
	public static String obtenerCiudad(Double latitud, Double longitud)
	{
		try {
			String ciudad;
			Geocoder geo = new Geocoder();
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(latitud),BigDecimal.valueOf(longitud))).setLanguage("es").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geo.geocode(geocoderRequest);

			if(geocoderResponse.getResults().size()<3){
				String[] formato = geocoderResponse.getResults().get(0).getFormattedAddress().split(", ");
				if(isNumeric(formato[2].split("\\s")[0]))
				{
					ciudad = formato[2].split("\\s")[1]+"_"+formato[3]+"_"+formato[4];
				}else{
					ciudad = formato[2]+"_"+formato[3]+"_"+formato[4];
				}
			}
			else if(!isNumeric(geocoderResponse.getResults().get(1).getFormattedAddress().split(", ")[0])&&geocoderResponse.getResults().get(1).getFormattedAddress().split(", ").length==3)
			{
				ciudad =  geocoderResponse.getResults().get(1).getFormattedAddress().replace(", ", "_");
			}
			else if(!isNumeric(geocoderResponse.getResults().get(2).getFormattedAddress().split(", ")[0])&&geocoderResponse.getResults().get(2).getFormattedAddress().split(", ").length==3)
			{
				ciudad =  geocoderResponse.getResults().get(2).getFormattedAddress().replace(", ", "_");
			}
			else if(geocoderResponse.getResults().get(3).getFormattedAddress().split(", ").length>2)
			{
				if(geocoderResponse.getResults().get(3).getFormattedAddress().split(", ").length>3)
				{
					String[] aux = geocoderResponse.getResults().get(3).getFormattedAddress().split(", ");
					ciudad =   aux[1]+"_"+aux[2]+"_"+aux[3];
				}else{
					ciudad =   geocoderResponse.getResults().get(3).getFormattedAddress().replace(", ", "_");
				}
			}else if(geocoderResponse.getResults().get(4).getFormattedAddress().split(", ").length>2){
				ciudad =   geocoderResponse.getResults().get(4).getFormattedAddress().replace(", ", "_");
			}else{
				ciudad =   geocoderResponse.getResults().get(2).getFormattedAddress().replace(", ", "_");
			}
			return ciudad;
		}catch (IndexOutOfBoundsException e) 
		{//error en envio de la request que devolvio vacia la response
			return obtenerCiudad(latitud, longitud);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Dadas unas coordenadas en grados, minutos y segundos las transforma en formato decimal
	 * @param longitud
	 * @param latitud
	 * @return Formato de coordenadas en decimal
	 */
	public static List<Double> pasarCoordenadasDecimal(String longitud, String latitud) {
		String[] lon = longitud.split("º",2);
		List<Double> coordenadas = new ArrayList<Double>();
		if(lon.length>1)
		{
			double grados = Double.valueOf(lon[0]);

			String[] lonAux = lon[1].split(String.valueOf(lon[1].charAt(2)),2);
			double minutos = Double.valueOf(lonAux[0]);

			String lonAux2 = lonAux[1].charAt(0)+""+lonAux[1].charAt(1);;
			double segundos = Double.valueOf(lonAux2);

			double longit = -1 * (Math.abs(grados) + (minutos / 60.0) + (segundos / 3600.0));
			coordenadas.add(longit);
		}

		String[] lat = latitud.split("º",2);
		if(lat.length>1)
		{
			double grados = Double.valueOf(lat[0]);

			String[] latAux = lat[1].split(String.valueOf(lat[1].charAt(2)),2);
			double minutos = Double.valueOf(latAux[0]);

			String latAux2 = latAux[1].charAt(0)+""+latAux[1].charAt(1);;
			double segundos = Double.valueOf(latAux2);

			double latid = Math.signum(grados) * (Math.abs(grados) + (minutos / 60.0) + (segundos / 3600.0));
			coordenadas.add(latid);
		}
		return coordenadas;
	}

	public static void main(String[] args) {
		try {
			CsvReader estaciones = new CsvReader("C:\\Users\\javie\\OneDrive\\Documentos\\Integración de sistemas de información\\Aire_ Madrid\\madrid_estaciones_red_calidad_aire-limpio.csv", ';');
			estaciones.readHeaders();


			while (estaciones.readRecord())
			{	
				List<Double> list = pasarCoordenadasDecimal(estaciones.get("Longitud"), estaciones.get("Latitud"));
				System.out.println(list.get(1));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
