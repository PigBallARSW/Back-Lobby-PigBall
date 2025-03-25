package co.edu.eci.pigball.lobby.service;

import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.model.Lobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import co.edu.eci.pigball.lobby.repository.LobbyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LobbyService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private LobbyRepository lobbyRepository;
    private final String gameServiceUrl = "https://localhost:8080";
    private final RestTemplate restTemplate;

    @Autowired
    public LobbyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LobbyDTO createLobby(String lobbyName) {
        String url = gameServiceUrl + "/createGame/" + lobbyName;

        try {
            // Realiza la solicitud POST al servicio
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, Object.class);

            // Procesa la respuesta
            Object responseBody = response.getBody();
            Long id = ((Integer) ((LinkedHashMap) responseBody).get("id")).longValue();  // Convierte el Integer a Long
            String name = (String) ((LinkedHashMap) responseBody).get("name");  // Extrae el nombre
            ArrayList<?> players = (ArrayList<?>) ((LinkedHashMap) responseBody).get("players");
            String status = "CREATED";  // Ajusta según el formato de la respuesta

            // Crear el Lobby con la respuesta
            Lobby lobby = new Lobby(id, lobbyName, status);
            lobbyRepository.save(lobby); // Guardar en la base de datos

            // Devolver el DTO
            return new LobbyDTO(lobby.getId(), lobby.getName(), lobby.getStatus());

        } catch (Exception e) {
            // Manejo de errores en caso de que la solicitud falle
            e.printStackTrace();
            throw new RuntimeException("Error al crear el lobby", e);
        }
    }

    public LobbyDTO getLobby(Long lobbyId) {
        String url = gameServiceUrl + "/getGame/" + lobbyId;

        try {
            // Realiza la solicitud GET al servicio
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, Object.class);

            // Procesa la respuesta
            Object responseBody = response.getBody();
            Long id = ((Integer) ((LinkedHashMap) responseBody).get("id")).longValue();  // Convierte el Integer a Long
            String name = (String) ((LinkedHashMap) responseBody).get("name");  // Extrae el nombre
            ArrayList<?> players = (ArrayList<?>) ((LinkedHashMap) responseBody).get("players");
            String status = (String) ((LinkedHashMap) responseBody).get("status"); // Ajusta según el formato de la respuesta

            // Crear el Lobby con la respuesta
            Lobby lobby = new Lobby(id, name, status);

            // Devolver el DTO
            return new LobbyDTO(lobby.getId(), lobby.getName(), lobby.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el juego", e);
        }
    }

    public List<LobbyDTO> getAllLobbies() {
        String url = gameServiceUrl + "/getAllGames";

        try {
            // Realiza la solicitud GET al servicio
            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, List.class);

            // Procesar la respuesta y convertirla en LobbyDTO
            List<Object> responseBody = response.getBody();
            List<LobbyDTO> lobbies = new ArrayList<>();

            for (Object obj : responseBody) {
                LinkedHashMap<String, Object> lobbyMap = (LinkedHashMap<String, Object>) obj;
                Long id = ((Integer) lobbyMap.get("id")).longValue();
                String name = (String) lobbyMap.get("name");
                String status = (String) lobbyMap.get("status");
                lobbies.add(new LobbyDTO(id, name, status));
            }

            return lobbies;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los juegos", e);
        }
    }


    public void lobbyGame(Long gameId) {
        String url = gameServiceUrl + "/removeGame/" + gameId;

        try {
            // Realiza la solicitud DELETE al servicio
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

            // Si necesitas más lógica después de eliminar, puedes agregarla aquí
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el juego", e);
        }
    }

}

