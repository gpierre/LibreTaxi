package LibreTaxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import LibreTaxi.commande.Commande;
import LibreTaxi.commande.Position;
import LibreTaxi.commande.UpdateChauffeur;

@Configuration
@ComponentScan
@EnableAutoConfiguration
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
    
    @Bean
    public View jsonTemplate() {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }
     
    @Bean
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }
    
    @PostConstruct
    public void init() {
    	
    	mesUtilisateurs.put("test1@gmail.com", "clie");
    	clients.add(new Client("test1@gmail.com","clie","client","client","cli","1234567890"));
    	counter.incrementAndGet();
    	mesUtilisateurs.put("fabrice", "fab123");
    	clients.add(new Client("fabrice","fab123","client","client","fab123","1234567890"));
    	counter.incrementAndGet();
    	mesUtilisateurs.put("a@b.c", "1234");
    	clients.add(new Client("a@b.c","1234","client","client","cli","1234567890"));
    	counter.incrementAndGet();
    	long mat = 1234567890;
    	for (int i = 0; i < 10 ; i++){
    		mesUtilisateurs.put(String.valueOf(mat), "chau");
	    	Chauffeur chauffeur = new Chauffeur(Long.toString(mat) ,"chauffeur","chauffeur","chauffeur","chau",String.valueOf(mat));
	    	chauffeur.setDisponible(false);
	    	chauffeur.setPostition(new Position(0.0,0.0));
	    	chauffeurs.add(chauffeur);
	    	counter.incrementAndGet();
	    	mat++;
    	}
    	
    	mesUtilisateurs.put(String.valueOf(1212121212), "1234");
    	Chauffeur chauffeur = new Chauffeur("121212121212" ,"chauffeur","chauffeur","chauffeur","1234",String.valueOf(1212121212));
    	chauffeur.setDisponible(false);
    	chauffeur.setPostition(new Position(0.0,0.0));
    	chauffeurs.add(chauffeur);
    	counter.incrementAndGet();
    	
    	mesUtilisateurs.put("paulson", "bond");
    	Chauffeur chauf = new Chauffeur("paulson" ,"bond","chauffeur","chauffeur","bond","1234567890");
    	chauf.setDisponible(false);
    	chauf.setPostition(new Position(0.0,0.0));
    	chauffeurs.add(chauf);
    	counter.incrementAndGet();
    	
    	// Il faut ajouter quelques chauffeurs avec position
    	// UQAM "latitude":"45.50866","longitude":"-73.56849"
    	chauffeurs.get(0).setPostition(new Position(-73.58849, 45.50866));
    	chauffeurs.get(0).setDisponible(true);
    	// Chez moi 45.58801	-73.67693
    	chauffeurs.get(1).setPostition(new Position(-73.67693, 45.58801));
    	chauffeurs.get(1).setDisponible(true);
    	// Place Desjardins 45.50737	-73.56410
    	chauffeurs.get(2).setPostition(new Position(-73.56410, 45.50737));
    	chauffeurs.get(2).setDisponible(true);
    	// Schwartz's Deli 45.52352	-73.57850
    	chauffeurs.get(3).setPostition(new Position(-73.57850, 45.52352));
    	chauffeurs.get(3).setDisponible(true);
    	// Place Versailles 45.59110	-73.53916
    	chauffeurs.get(4).setPostition(new Position(-73.53916, 45.59110));
    	chauffeurs.get(4).setDisponible(true);
    }
    
    @Autowired
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
	public ResponseEntity<String> login(@RequestBody LoginID login) {
		
		if (estUtilisateur(login.getNomUtilisateur(), login.getMotDePasse())){
			
			// Ouvrir une session
			
			return new ResponseEntity<String>(String.format(templateSuccess, login.getNomUtilisateur()), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format(templateFailure, login.getNomUtilisateur()), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
	}
	
	@RequestMapping(value="/commande", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> commande(@RequestBody Commande commande){
		
		// Traiter la commande
		
		// Trouver le client
		// Position posClient = null;
		if (estUtilisateur(commande.getNomUtilisateur(), commande.getMotDePasse())){
			// posClient = new Position(commande.getLongitude(), commande.getLatitude());
			ArrayList<Chauffeur> chauffeursPresClient = new ArrayList<Chauffeur>();
			
			// Retrouver la liste des adresses près des coordonnées
			// Le client est-il dans la zone de service
			
			if (!UtilitairesMaps.estDansZoneDeService(commande.getPosition())){
				return new ResponseEntity<String>("Malheureusement vous êtes à l'extérieur de la zone de service!", HttpStatus.EXPECTATION_FAILED);
			}
			
			// Ici il faudrait confirmer l'adresse du client
			
			//	Trouver le chauffeur le plus près
			chauffeursPresClient = listeChauffeursDisponibles();
			listeChauffeursLesPlusPresEnOrdre(commande.getPosition(), chauffeursPresClient);
			commande.setListeChauffeurs(chauffeursPresClient);
			
			//  Il doit y avoir des exceptions: service non disponible dans le secteur demandé, etc 
			if (chauffeursPresClient.size() == 0){
				return new ResponseEntity<String>("Aucun chauffeur disonible pour " +  commande.getAdresse(0) ,HttpStatus.SERVICE_UNAVAILABLE);
			}
			// Demande aux chauffeurs!!!
			// Placer la commande dans le chauffeur en attendant qu'il réponde.
			chauffeursPresClient.get(0).setCommande(commande);
			
			// Trouver la distance entre le chauffeur et le client
			// Devrait-être placer APRÈS l'acceptation par le chauffeur DANS miseAjourChauffeur()
			String temps = UtilitairesMaps.tempsTrajet(commande.getPosition(), chauffeursPresClient.get(0).getPosition());
				
			
			//  Retourner le temps estimé d'arriver dans la réponse
			return new ResponseEntity<String>("Il y a " + chauffeursPresClient.size() + " chauffeurs dans votre secteur." + "Un taxi " + chauffeursPresClient.get(0).getMatricule() + " est en route pour " +  commande.getAdresse(0) + ". Il devrait arriver dans " + temps + ".",HttpStatus.OK);
		}
		 
		return new ResponseEntity<String>("Usager inconnu!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	}
	
	@RequestMapping(value="/chauffeur", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> miseAjourChauffeur(@RequestBody UpdateChauffeur update){
		
		// Retrouver le chauffeur et le mettre à jour
		if (estUtilisateur(update.getNomUtilisateur(), update.getMotDePasse())){
			for (Chauffeur chauffeur : chauffeurs){
				if (update.getNomUtilisateur().equalsIgnoreCase(chauffeur.getNomUtilisateur())){
					if (chauffeur.isAcommande()){
						if (update.isAccepteCommande()){
							chauffeur.setAcommande(false);
							chauffeur.setDisponible(false);
							return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
						} else if (chauffeur.getNbCommunications() < 1){
							chauffeur.setNbCommunications(1);
							return new ResponseEntity<String>("Vous avez une commande! - longitude " + chauffeur.getCommande().getLongitude() 
									+ " - Latitude " + chauffeur.getCommande().getLatitude() + " - " + chauffeur.getCommande().getAdresse(0) 
									+ " - commentaireClient " + chauffeur.getCommande().getCommentaire(), HttpStatus.ACCEPTED);
						} else if (chauffeur.getNbCommunications() <= 1){
							chauffeur.setAcommande(false);
							chauffeur.setDisponible(false);
							try {
								Chauffeur prochainChauffeur = chauffeur.getCommande().getChauffeur();
								prochainChauffeur.setCommande(chauffeur.getCommande());
							} catch (Exception e) {
								// Il faudrait avertir le client qu'il n'y a plus de chauffeur disponible ou en trouver un dans une zone élargi
								System.out.println("Pourquoi je serais ici!");
							}
							return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
						}
					}
					chauffeur.setDisponible(update.isDisponible());
					chauffeur.setPostition(update.getPos());
					// Il faudrait confirmer la disponibilité et la position dans le message
					return new ResponseEntity<String>("J'ai reçu la mise à jour!", HttpStatus.ACCEPTED);
				}
			}
			return new ResponseEntity<String>("utilisateur inconnu!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		} else {
			return new ResponseEntity<String>("utilisateur inconnu!", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}
    
	private boolean estUtilisateur(String nomUtilisateur, String motDePasse){
		if (mesUtilisateurs.get(nomUtilisateur) != null &&
					mesUtilisateurs.get(nomUtilisateur).equals(motDePasse)){
			return true;
		} else {
			return false;
		}
	}
	
	private ArrayList<Chauffeur> listeChauffeursDisponibles(){
		ArrayList<Chauffeur> listeDisponibles = new ArrayList<Chauffeur>();
		for (Chauffeur chauffeur : chauffeurs){
			if (chauffeur.isDisponible()){
				listeDisponibles.add(chauffeur);
			}
		}
		return listeDisponibles;
	}
	
	@RequestMapping(value = "/liste", method = RequestMethod.GET)
	public String liste() {
		String utilisateurs = "";
		for (Chauffeur chau : chauffeurs){
			utilisateurs += "\n </BR>";
			utilisateurs += "matricule : " + chau.getMatricule();
			utilisateurs += " longitude : " + chau.getPosition().getLongitude();
			utilisateurs += " latitude : " + chau.getPosition().getLatitude();
			utilisateurs += " disponible : " + chau.isDisponible();
			utilisateurs += " a une commande : " + chau.isAcommande();
		}
		for (Client cli : clients){
			utilisateurs += "\n </BR>";
			utilisateurs += "Client : " + cli.getCourriel();
		}
		return utilisateurs;
	}
	
	private void listeChauffeursLesPlusPresEnOrdre(Position posClient, ArrayList<Chauffeur> chauffeurs){
		Map<Chauffeur, Double> distanceMap = new HashMap<Chauffeur, Double>();
		// délimiter par un cercle la zone pres du client
		// Donc ((|longClient - longChauffeur|) + (|latClient-latChauffeur|)) < 0.03 (arbitraire)
		for (Chauffeur chauffeur : chauffeurs){
			
			double distance = (Math.abs(posClient.getLongitude() - chauffeur.getPosition().getLongitude()) 
					+ Math.abs(posClient.getLatitude() - chauffeur.getPosition().getLatitude()));
			if (distance < 0.03){
				// ICI on pourrait utliser l'indice que contient le chauffeur 
				// pour mieux répartir les appels entre les chauffeurs
				distanceMap.put(chauffeur, distance);
			}
		}
		
		// Trie en ordre croissant de distance
		chauffeurs.clear();
		for (Map.Entry<Chauffeur, Double> curseur1 : distanceMap.entrySet()){
			double min = curseur1.getValue();
			Chauffeur chauffeur = curseur1.getKey();
			for (Map.Entry<Chauffeur, Double> curseur2 : distanceMap.entrySet()){
				if (curseur2.getValue() < min){
					min = curseur2.getValue();
					chauffeur = curseur2.getKey();
				}
			}
			chauffeurs.add(chauffeur);
			distanceMap.replace(chauffeur, 10.0);
		}
	}
}
