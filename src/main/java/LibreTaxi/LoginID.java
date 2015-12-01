package LibreTaxi;

import com.fasterxml.jackson.annotation.JsonProperty;
	
public class LoginID {
	private String nomUtilisateur;
	private String motDePasse;

	public LoginID(@JsonProperty("nomUtilisateur") String nomUtilisateur, @JsonProperty("motDePasse") String motDePasse){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	public String getMotDePasse(){
		return motDePasse;
	}
	
	public String getNomUtilisateur(){
		return nomUtilisateur;
	}
}
