package LibreTaxi;

public class Utilisateur {
	private String nomUtilisateur;
	private String motDePasse;
	
	public Utilisateur(String nomUtilisateur, String motDePasse){
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}
	
	public String getNomUtilisateur(){
		return nomUtilisateur;
	}
	public String getMotDePasse(){
		return motDePasse;
	}
}
