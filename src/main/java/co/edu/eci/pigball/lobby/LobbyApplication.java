package co.edu.eci.pigball.lobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LobbyApplication {

	public static void main(String[] args) {

		// Load environment variables from .env file if it exists
		try {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			// Set system properties from .env file
			dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		} catch (Exception e) {
			// Continue without .env file
		}

		// If SSL is disabled, ensure we're using HTTP
		if (Boolean.parseBoolean(System.getProperty("server.ssl.enabled", "false"))) {
			System.setProperty("server.port", "8444");
		} else {
			System.setProperty("server.port", "8081");
		}
		
		SpringApplication.run(LobbyApplication.class, args);
	}
}