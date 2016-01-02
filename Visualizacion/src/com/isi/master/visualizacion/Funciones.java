package com.isi.master.visualizacion;

import java.text.Normalizer;

public class Funciones {
	
	public static String quitarTildes(String input) {
		String text = Normalizer.normalize(input, Normalizer.Form.NFD);
		return text.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
	}
}
