package LibreTaxi;

public class Client extends Utilisateur{
	
	private String courriel;
	
	public Client(String nomUtilisateur, String motDePasse, String type, String nom, String prenom, String telephone){
		super(nomUtilisateur, motDePasse, type, nom, prenom, telephone);
		courriel = nomUtilisateur;
	}
	
	public Client(Utilisateur utilisateur){
		super (utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse(), utilisateur.getType(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone());
		courriel = utilisateur.getNomUtilisateur();
	}
	
	public String getCourriel(){
		return courriel;
	}
}
