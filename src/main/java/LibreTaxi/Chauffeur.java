package LibreTaxi;

import LibreTaxi.commande.Commande;
import LibreTaxi.commande.Position;

public class Chauffeur extends Utilisateur{
	
	private String matricule;
	private Position position;
	private boolean disponible = false;
	Commande commande;
	boolean aCommande;
	boolean accepteCommande = false;
	private int nbCommunications = 0;
	private int nbCommandes = 0;

	public Chauffeur(String nomUtilisateur, String motDePasse, String type, String nom, String prenom, String telephone){
		super(nomUtilisateur, motDePasse, type, nom, prenom, telephone);
		matricule = nomUtilisateur;
		disponible = false;
		position = new Position(0.0,0.0);
	}
	
	public Chauffeur(Utilisateur utilisateur){
		super (utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse(), utilisateur.getType(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone());
		matricule = utilisateur.getNomUtilisateur();
		position = new Position(0.0,0.0);
		disponible = false;
	}
	
	public String getMatricule(){
		return matricule;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void setPostition(Position position){
		this.position = position;
	}
	
	public boolean isDisponible(){
		return disponible;
	}
	
	public void setDisponible(boolean disponible){
		this.disponible = disponible;
	}
	
	public Commande getCommande(){
		return commande;
	}
	
	public void setCommande(Commande commande){
		aCommande = true;
		this.commande = commande;
		nbCommandes++;
		resetNbCommunications();
	}
	
	public boolean isAcommande(){
		return aCommande;
	}
	
	public void setAcommande(boolean aCommande){
		this.aCommande = aCommande;
	}
	
	public void setNbCommunications(int nb){
		nbCommunications = nb;
	}
	
	public void resetNbCommunications(){
		nbCommunications = 0;
	}
	
	public int getNbCommunications(){
		return nbCommunications;
	}
	
	public double getIndiceNbCommandes(){
		try {
			return nbCommandes/commande.getNbTotalCommandes();
		} catch (Exception e){
			return 0;
		}
	}
}
