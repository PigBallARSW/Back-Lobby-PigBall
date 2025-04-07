package co.edu.eci.pigball.lobby;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
	"ALLOWED_ORIGINS_HTTP=http://test:3000,http://test.example.com",
	"ALLOWED_ORIGINS_HTTPS=https://test:3000,https://test.example.com",
	"SSL_ENABLED=false"
})
class LobbyApplicationTests {
	@Test
	void contextLoads() {
	}
	@Test
	void mainMethodTest() {
		// Se configuran variables de entorno (propiedades del sistema) para la prueba.
		System.setProperty("ALLOWED_ORIGINS_HTTP", "http://localhost:3000,http://example.com");
		System.setProperty("ALLOWED_ORIGINS_HTTPS", "https://localhost:3000,https://test.example.com,https://example.com");
		System.setProperty("SSL_ENABLED", "false");
		// Se invoca el método main para verificar que se ejecuta sin lanzar excepciones.
		LobbyApplication.main(new String[] {});
	}


}

