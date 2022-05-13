package org.uhn.loader;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.uhn.entities.Patient;
import org.uhn.repository.PatientRepository;

@Component
public class Seeder implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(Seeder.class);
	private final PatientRepository repository;

	@Autowired
	public Seeder(PatientRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Preloading Repository with a default patient.");
		this.repository.save(new Patient("Marco", "Polo",new Timestamp(System.currentTimeMillis())));
	}

}
