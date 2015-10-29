package LibreTaxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import LibreTaxi.commande.Commande;
import LibreTaxi.commande.Position;
import LibreTaxi.commande.UpdateChauffeur;

@RestController
class LibreTaxiController {
	private static final String bienvenue= "Bienvenue à LibreTaxi! Cette application n'est accessible qu'avec l'application Android "
			+ "TaxiLibre.";
	private static final String templateSuccess = "Bienvenue, %s!";
	private static final String templateInscrit = "Bienvenue, %s! vous êtes le %sième inscrit sur notre service de Taxi.";
	private static final String templateFailure = "Malheureusement vous n'êtes pas inscrit, %s!";
	private static final String templateFailureType = "Malheureusement ce type d'utilisateur n'existe pas, %s!";
	private static final String templateExisteDeja = "L'utilisateur %s existe déjà!";
	
    private final AtomicLong counter = new AtomicLong();
    private static Map<String, String> mesUtilisateurs = new HashMap<String, String>();
    private static ArrayList<Client> clients = new ArrayList<Client>();
    private static ArrayList<Chauffeur> chauffeurs = new ArrayList<Chauffeur>();
    
    @PostConstruct
    public void init() {
    	
    	mesUtilisateurs.put("test1@gmail.com", "clie");
    	clients.add(new Client("test1@gmail.com","clie","client","client","cli","1234567890"));
    	counter.incrementAndGet();
    	mesUtilisateurs.put("a@b.c", "1234");
    	clients.add(new Client("a@b.c","1234","client","client","cli","1234567890"));
    	counter.incrementAndGet();
    	long mat = 1234567890;
    	for (int i = 0; i < 10 ; i++){
    		mesUtilisateurs.put(String.valueOf(mat), "chau");
	    	Chauffeur chauffeur = new Chauffeur(Long.toString(mat) ,"chauffeur","chauffeur","chauffeur","chau",String.valueOf(mat));
	    	chauffeur.setDisponible(true);
	    	chauffeurs.add(chauffeur);
	    	counter.incrementAndGet();
	    	mat++;
    	}
    	
    	// Il faut ajouter quelques chauffeurs avec position
    	// UQAM "latitude":"45.50866","longitude":"-73.56849"
    	chauffeurs.get(0).setPostition(new Position(-73.56849, 45.50866));
    	// Chez moi 45.58801	-73.67693
    	chauffeurs.get(1).setPostition(new Position(-73.67693, 45.58801));
    	// Place Desjardins 45.50737	-73.56410
    	chauffeurs.get(2).setPostition(new Position(-73.56410, 45.50737));
    	// Schwartz's Deli 45.52352	-73.57850
    	chauffeurs.get(3).setPostition(new Position(-73.57850, 45.52352));
    	// Place Versailles 45.59110	-73.53916
    	chauffeurs.get(4).setPostition(new Position(-73.53916, 45.59110));
    }
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return bienvenue;
	}
	
	// Inscription
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> update(@RequestBody Utilisateur utilisateur) {
		
		if (mesUtilisateurs.containsKey(utilisateur.getNomUtilisateur())){
			return new ResponseEntity<String>(String.format(templateExisteDeja, utilisateur.getNomUtilisateur()), HttpStatus.ALREADY_REPORTED);
		}
		if (utilisateur.getType().equalsIgnoreCase("client")){
			// sauvegarder un client
			mesUtilisateurs.put(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse());
			Client client = new Client(utilisateur);
			clients.add(client);
			
		} else if (utilisateur.getType().equalsIgnoreCase("chauffeur")){
			// sauvegarder un chauffeur
			mesUtilisateurs.put(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse());
			Chauffeur chauffeur = new Chauffeur(utilisateur);
			chauffeurs.add(chauffeur);
		} else {
			return new ResponseEntity<String>(String.format(templateFailureType, utilisateur.getNomUtilisateur()), HttpStatus.BAD_REQUEST); 
		}
		
		// Pour compter le nombre d'inscriptions
		counter.incrementAndGet();
		return new ResponseEntity<String>(String.format(templateInscrit, utilisateur.getNomUtilisateur(), counter), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Login login) {
		
		if (mesUtilisateurs.get(login.getNomUtilisateur()) != null &&
			mesUtilisateurs.get(login.getNomUtilisateur()).equals(login.getMotDePasse())){
			
			// Ouvrir une session
			return new ResponseEntity<String>(String.format(templateSuccess, login.getNomUtilisateur()), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format(templateFailure, login.getNomUtilisateur()), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
	}
	
	@RequestMapping(value="/commande", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> commande(@RequestBody Commande commande){
		
		// Traiter la commande
		//     Trouver le chauffeur le plus près
		//     Retourner le temps estimé d'arriver dans la réponse
		//     Il doit y avoir des exceptions: service non disponible dans le secteur demandé, n'est pas un client, 
		return new ResponseEntity<String>("blabla", HttpStatus.OK);
	}
	
	@RequestMapping(value="/chauffeur", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> miseAjourChauffeur(@RequestBody UpdateChauffeur update){
		
		// Retrouver le chauffeur et le mettre à jour
		if (mesUtilisateurs.containsKey(update.getNomUtilisateur()) 
				&& mesUtilisateurs.get(update.getNomUtilisateur()).equalsIgnoreCase(update.getMotDePasse())){
			for (Chauffeur chauffeur : chauffeurs){
				if (update.getNomUtilisateur().equalsIgnoreCase(chauffeur.getNomUtilisateur())){
					
					chauffeur.setDisponible(update.isDisponible());
					chauffeur.setPostition(update.getPos());
					return new ResponseEntity<String>("J'ai reçu la mise à jour!", HttpStatus.ACCEPTED);
				}
			}
			return new ResponseEntity<String>("utilisateur inconnu!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		} else {
			return new ResponseEntity<String>("utilisateur inconnu!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}
}
