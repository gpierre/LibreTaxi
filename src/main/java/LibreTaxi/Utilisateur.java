package LibreTaxi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Utilisateur {
	private String nomUtilisateur;
	private String motDePasse;
	private String type;
	private String nom;
	private String prenom;
	private String telephone;
	private String courriel;
	
	/*public Utilisateur(String nomUtilisateur, String motDePasse){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	public Utilisateur(String nomUtilisateur, String motDePasse, String type){
		this.type = type;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}*/
	
	public Utilisateur(@JsonProperty("nomUtilisateur") String nomUtilisateur,@JsonProperty("motDePasse") String motDePasse,@JsonProperty("type") String type,@JsonProperty("nom") String nom,@JsonProperty("prenom") String prenom,@JsonProperty("telephone") String telephone,@JsonProperty("courriel") String courriel){
		this.type = type;
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.courriel = courriel;
	}
	
	public String getNomUtilisateur(){
		return nomUtilisateur;
	}
	public String getMotDePasse(){
		return motDePasse;
	}
	public String getType(){
		return type;
	}
	public String getNom(){
		return nom;
	}
	public String getPrenom(){
		return prenom;
	}
	public String getTelephone(){
		return telephone;
	}
	public String getCourriel(){
		return courriel;
	}
}
