package org.serratec.petropet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PetropetApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetropetApplication.class, args);

	}
}
