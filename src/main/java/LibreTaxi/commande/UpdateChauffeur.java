package LibreTaxi.commande;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateChauffeur {
	private String nomUtilisateur;
	private String motDePasse;
	private Position pos;
	private boolean disponible;
	private boolean accepteCommande;
	
	public UpdateChauffeur(@JsonProperty("nomUtilisateur") String nomUtilisateur,@JsonProperty("motDePasse") String motDePasse,@JsonProperty("longitude") String  longitude,
			@JsonProperty("latitude") String latitude, @JsonProperty("disponible") String disponible, @JsonProperty("accepteCommande") String accepteCommande){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		
		if (disponible.equalsIgnoreCase("o")){
			this.disponible = true;
		} else if (disponible.equalsIgnoreCase("n")){
			this.disponible = false;
		} else {
			// envoyer une exception mauvaise disponibilité
		}
		
		pos = new Position(Double.valueOf(longitude), Double.valueOf(latitude));
		
		if (accepteCommande.equalsIgnoreCase("o")){
			this.accepteCommande = true;
		} else if (accepteCommande.equalsIgnoreCase("n")){
			this.accepteCommande = false;
		} else {
			// envoyer une exception mauvaise acceptation
		}
	}
	
	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public Position getPos() {
		return pos;
	}

	public boolean isDisponible() {
		return disponible;
	}
	public boolean isAccepteCommande(){
		return accepteCommande;
	}
}
