package LibreTaxi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LibreTaxiController {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    private static List<Utilisateur> mesUtilisateurs = new ArrayList<Utilisateur>();
    
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@RequestMapping("/salutations")
    public Salutations salutations(@RequestParam(value="name", defaultValue="World") String name) {
        return new Salutations(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Utilisateur> update(@RequestBody Utilisateur utilisateur) {
		//if ()
	}*/
	
	// @RequestMapping(value = "/inscription", method = RequestMethod.)
}
