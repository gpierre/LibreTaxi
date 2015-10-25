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

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private static ArrayList<Utilisateur> clients = new ArrayList<Utilisateur>();
    private static ArrayList<Utilisateur> chauffeurs = new ArrayList<Utilisateur>();
    
    @PostConstruct
    public void init() {
    	mesUtilisateurs.put("Chauffeur", "chauffeur");
    	mesUtilisateurs.put("Client", "client");
    	Utilisateur client = new Utilisateur("Client","client","client","client","cli","1234567890");
    	clients.add(client);
    	counter.incrementAndGet();
    	Utilisateur chauffeur = new Utilisateur("Chauffeur","chauffeur","chauffeur","chauffeur","chau","1234567890");
    	chauffeurs.add(chauffeur);
    	counter.incrementAndGet();
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
			clients.add(utilisateur);
			
		} else if (utilisateur.getType().equalsIgnoreCase("chauffeur")){
			// sauvegarder un chauffeur
			mesUtilisateurs.put(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse());
			chauffeurs.add(utilisateur);
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
		return new ResponseEntity<String>("blabla", HttpStatus.BAD_REQUEST);
	}
}
