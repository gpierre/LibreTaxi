package LibreTaxi.commande;

import LibreTaxi.UtilitairesMaps;

import com.google.maps.model.GeocodingResult;

public class Position {
	private double longitude;
	private double latitude;
	private GeocodingResult[] adressePresDesCoordonnees = null;
	
	public Position(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public void adressePresDesCoordonnees(){
		try{
			adressePresDesCoordonnees = UtilitairesMaps.trouverListeAdressesAProximite(latitude, longitude);
			
		// @TODO Il faut mieux gérer les ecxceptions 
		} catch (Exception e){
			
		}
		// return adressePresDesCoordonnees;
	}
	
	public String getAdresse(int rang){
		try{
			return adressePresDesCoordonnees[rang].formattedAddress;
		} catch (Exception e){
			return "";
		}
	}
	
	@Override
	public String toString(){
		return ("Longitude : " + Double.toString(this.longitude)
				+ " Latitude : " + Double.toString(this.latitude));
	}
}
