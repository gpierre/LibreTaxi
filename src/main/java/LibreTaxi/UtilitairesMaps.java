package LibreTaxi;

import LibreTaxi.commande.Position;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class UtilitairesMaps {
	
	final static double LONG_MIN = -73.0;
	final static double LONG_MAX = -74.0;
	final static double LAT_MIN = 45.0;
	final static double LAT_MAX = 46.0;
	private final static GeoApiContext context = new GeoApiContext().setApiKey("");
	
	// @TODO 
	// Trouver la liste des adresses près des coordonnées
	public static GeocodingResult[] trouverListeAdressesAProximite(double lat, double lng) throws Exception{ 
		
		GeocodingResult[] results = GeocodingApi.newRequest(context)
		        .latlng(new LatLng(lat, lng)).await();
		    return results;
	}
	
	public static boolean estDansZoneDeService(Position position){
		
		if (position.getLatitude() < LAT_MIN || position.getLatitude() > LAT_MAX
				|| position.getLongitude() > LONG_MIN || position.getLongitude() < LONG_MAX){
			return false;
		}
		return true;
	}
	
	// Trouver le temps de trajet entre 2 adresses
	public static String tempsTrajet(Position pos1, Position pos2){
		DistanceMatrix result = DistanceMatrixApi.newRequest(context)
			.origins(new LatLng(pos1.getLatitude(), pos1.getLongitude()), new LatLng(pos1.getLatitude(), pos1.getLongitude()))
			.destinations(new LatLng(pos2.getLatitude(), pos2.getLongitude()), new LatLng(pos2.getLatitude(), pos2.getLongitude()))
			.awaitIgnoreError();
			// .departureTime(new DateTime().plusMinutes(2))  // this is ignored when an API key is used
		return result.rows[0].elements[0].duration.humanReadable;
	}
}
