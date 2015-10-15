package LibreTaxi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LibreTaxiController {
	private static final String templateSuccess = "Bienvenue, %s!";
	private static final String templateFailure = "Malheureusement vous n'êtes pas inscrit, %s!";
	private static final String templateFailureType = "Malheureusement ce type d'utilisateur n'existe pas, %s!";
	private static final String templateExisteDeja = "L'utilisateur %s existe déjà!";
	
    private final AtomicLong counter = new AtomicLong();
    private static Map<String, String> mesUtilisateurs = new HashMap<String, String>();
    
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping("/salutations")
    public Salutations salutations(@RequestParam(value="name", defaultValue="World") String name) {
        return new Salutations(counter.incrementAndGet(),
                            String.format(templateSuccess, name));
    }
	
	@RequestMapping("/inscription")
	public Salutations inscription(@RequestParam(value="name", defaultValue="null") String name) {
		mesUtilisateurs.put(name, "motDePasse");
		return new Salutations(counter.incrementAndGet(),
                            String.format(templateSuccess, name));
	}
	
	@RequestMapping("/login")
	public Salutations login(@RequestParam(value="name", defaultValue="null") String name) {
		
		if (mesUtilisateurs.get(name) != null){
			return new Salutations(counter.incrementAndGet(),
		               		String.format(templateSuccess, name));
		} else {
			return new Salutations(counter.incrementAndGet(),
               		String.format(templateFailure, name));
		}
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
		} else if (utilisateur.getType().equalsIgnoreCase("chauffeur")){
			// sauvegarder un chauffeur
			mesUtilisateurs.put(utilisateur.getNomUtilisateur(), utilisateur.getMotDePasse());
		} else {
			return new ResponseEntity<String>(String.format(templateFailureType, utilisateur.getNomUtilisateur()), HttpStatus.BAD_REQUEST); 
		}
		
		return new ResponseEntity<String>(String.format(templateSuccess, utilisateur.getNomUtilisateur()), HttpStatus.CREATED);
	}
	
	// Authetification (login)
	/*@RequestMapping(value = "/", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Utilisateur utilisateur) {
		
		if (mesUtilisateurs.get(utilisateur.getNomUtilisateur()) != null &&
			mesUtilisateurs.get(utilisateur.getNomUtilisateur()).equals(utilisateur.getMotDePasse())){
			
			// Ouvrir une session
			return new ResponseEntity<String>(String.format(templateSuccess, utilisateur.getNomUtilisateur()), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format(templateFailure, utilisateur.getNomUtilisateur()), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
	}*/
	
	@RequestMapping(value = "/", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<String> login(@RequestBody Login login) {
		
		if (mesUtilisateurs.get(login.getNomUtilisateur()) != null &&
			mesUtilisateurs.get(login.getNomUtilisateur()).equals(login.getMotDePasse())){
			
			// Ouvrir une session
			return new ResponseEntity<String>(String.format(templateSuccess, login.getNomUtilisateur()), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(String.format(templateFailure, login.getNomUtilisateur()), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
	}
}
