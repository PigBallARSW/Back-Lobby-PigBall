package co.edu.eci.pigball.lobby.service;

import co.edu.eci.pigball.lobby.model.DTO.GameDTO;
import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.model.Lobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import co.edu.eci.pigball.lobby.repository.LobbyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LobbyService {
    @Autowired
    private LobbyRepository lobbyRepository;

    @Value("${GAME_SERVICE_URL}")
    private String gameServiceUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public LobbyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LobbyDTO createLobby(LobbyDTO lobbyDTO) {
        String url = gameServiceUrl + "/createGame";
        try {
            GameDTO gameDTO = new GameDTO(lobbyDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GameDTO> entity = new HttpEntity<>(gameDTO, headers);
            ResponseEntity<GameDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity, GameDTO.class);
            GameDTO responseGameDTO = response.getBody();
            Lobby lobby = new Lobby(responseGameDTO);
            lobbyRepository.save(lobby);
            return new LobbyDTO(lobby);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el lobby", e);
        }
    }

    public LobbyDTO getLobby(String lobbyId) {
        String url = gameServiceUrl + "/getGame/" + lobbyId;
        try {
            ResponseEntity<GameDTO> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, GameDTO.class);
            GameDTO responseGameDTO = response.getBody();
            Lobby lobby = new Lobby(responseGameDTO);
            return new LobbyDTO(lobby);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el juego", e);
        }
    }

    public List<LobbyDTO> getAllLobbies() {
        String url = gameServiceUrl + "/getAllGames";

        try {
            System.out.println("URL: " + url);
            // Realiza la solicitud GET al servicio
            ResponseEntity<List<GameDTO>> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<GameDTO>>() {});

            // Procesar la respuesta y convertirla en LobbyDTO
            List<GameDTO> games = response.getBody();
            List<LobbyDTO> lobbies = new ArrayList<>();

            for (GameDTO gameDTO : games) {
                LobbyDTO lobbyDTO = new LobbyDTO(gameDTO);
                if(!lobbyDTO.isPrivateGame())lobbies.add(lobbyDTO);
            }
            return lobbies;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todos los juegos", e);
        }
    }


    public void removeGame(String gameId) {
        String url = gameServiceUrl + "/removeGame/" + gameId;

        try {
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el juego", e);
        }
    }

}

