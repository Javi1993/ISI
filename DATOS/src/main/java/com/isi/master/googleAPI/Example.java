package com.isi.master.googleAPI;

import java.io.IOException;
import java.math.BigDecimal;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public class Example {

	public static void main( String[] args )
	{	
	    	try {
				Geocoder geo =  new Geocoder();
				
				
				GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(40.53944444444444),BigDecimal.valueOf(-3.6463888888888887))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse = geo.geocode(geocoderRequest);
				
				for(int i = 0; i<geocoderResponse.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse.getResults().get(i).getFormattedAddress());
				}
				GeocoderRequest geocoderRequest2 = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(40.664722222222224),BigDecimal.valueOf(-4.700555555555556))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse2 = geo.geocode(geocoderRequest2);
				
				System.out.println("-----------------------------------");
				for(int i = 0; i<geocoderResponse2.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse2.getResults().get(i).getFormattedAddress());
				}
				GeocoderRequest geocoderRequest3 = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(42.87516709),BigDecimal.valueOf(-3.231732505))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse3 = geo.geocode(geocoderRequest3);
				System.out.println("-----------------------------------");
				for(int i = 0; i<geocoderResponse3.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse3.getResults().get(i).getFormattedAddress());
				}
				GeocoderRequest geocoderRequest4 = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(40.42361111111111),BigDecimal.valueOf(-3.7122222222222225))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse4 = geo.geocode(geocoderRequest4);
				System.out.println("-----------------------------------");
				for(int i = 0; i<geocoderResponse4.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse4.getResults().get(i).getFormattedAddress());
				}
				GeocoderRequest geocoderRequest5 = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(28.1222437),BigDecimal.valueOf(-16.5862538))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse5 = geo.geocode(geocoderRequest5);
				System.out.println("-----------------------------------");
				for(int i = 0; i<geocoderResponse5.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse5.getResults().get(i).getFormattedAddress());
				}
				GeocoderRequest geocoderRequest6 = new GeocoderRequestBuilder().setLocation(new LatLng(BigDecimal.valueOf(42.85604797),BigDecimal.valueOf(-2.667784995))).setLanguage("es").getGeocoderRequest();
				GeocodeResponse geocoderResponse6 = geo.geocode(geocoderRequest6);
				System.out.println("-----------------------------------");
				for(int i = 0; i<geocoderResponse6.getResults().size(); i++)
				{
					System.out.println(i+" - "+geocoderResponse6.getResults().get(i).getFormattedAddress());
				}
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
}