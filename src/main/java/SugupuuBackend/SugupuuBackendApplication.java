package SugupuuBackend;

import SugupuuBackend.model.FamilyTree;
import SugupuuBackend.model.Person;
import SugupuuBackend.repository.FamilyTreeRepository;
import SugupuuBackend.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SugupuuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SugupuuBackendApplication.class, args);
	}

}
