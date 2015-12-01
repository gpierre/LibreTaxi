package LibreTaxi.commande;

import java.util.ArrayList;

import LibreTaxi.Chauffeur;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.GeocodingResult;

public class Commande {
	private static int nbTotalCommandes = 0;
	private float longitude;
	private float latitude;
	private String nomUtilisateur;
	private String motDePasse;
	private Position position;
	private String commentaire;
	private ArrayList<Chauffeur> listeChauffeurPresClient;
	private int indiceChauffeur = 0;
	
	public Commande(@JsonProperty("nomUtilisateur") String nomUtilisateur,@JsonProperty("motDePasse") String motDePasse,@JsonProperty("longitude") String longitude, @JsonProperty("latitude") String latitude, @JsonProperty("commentaireClient") String commentaire){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		this.latitude = Float.valueOf(latitude);
		this.longitude = Float.valueOf(longitude);
		this.position = new Position(this.longitude, this.latitude);
		this.commentaire = commentaire;
		this.position.adressePresDesCoordonnees();
		nbTotalCommandes++;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public String getMotDePasse() {
		return motDePasse;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public String getAdresse(int rang){
		return position.getAdresse(rang);
	}
	
	public String getCommentaire(){
		return commentaire;
	}
	public int getNbTotalCommandes(){
		return nbTotalCommandes;
	}
	
	public void setListeChauffeurs(ArrayList<Chauffeur> chauffeursPresClient){
		this.listeChauffeurPresClient = chauffeursPresClient;
	}
	
	public Chauffeur getChauffeur() throws Exception{
		
		indiceChauffeur++;
		if (indiceChauffeur >= listeChauffeurPresClient.size()){
			throw new Exception("Il n'y a plus de chauffeurs");
		}
		Chauffeur chauffeur = listeChauffeurPresClient.get(indiceChauffeur);
		return chauffeur;
	}
}
