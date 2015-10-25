package LibreTaxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibreTaxiApplication {

	public static void main(String[] args) {
		
		// Fabriquer 1 client et un chauffeur
		SpringApplication.run(LibreTaxiApplication.class, args);

	}
}
