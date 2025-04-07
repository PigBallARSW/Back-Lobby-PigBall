package co.edu.eci.pigball.lobby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import co.edu.eci.pigball.lobby.java.Pair;
import co.edu.eci.pigball.lobby.model.dto.GameDTO;
import co.edu.eci.pigball.lobby.model.dto.PlayerDTO;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Document(collection = "lobby")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lobby {
    private SimpMessagingTemplate messagingTemplate;
    private String lobbyId;
    private String lobbyName;
    private String creatorName;
    private int maxPlayers;
    private LobbyStatus status;
    private boolean privateLobby;
    private Instant creationTime;
    private int borderX;
    private int borderY;
    private Pair<Team, Team> teams;
    private ConcurrentHashMap<String, PlayerDTO> players;

    public Lobby(String lobbyId, String lobbyName, String creatorName, int maxPlayers, boolean privateGame) {
        this.lobbyId = UUID.randomUUID().toString(); // Genera un UUID único
        this.lobbyName = lobbyName;
        this.creatorName = creatorName;
        this.maxPlayers = maxPlayers;
        this.status = LobbyStatus.WAITING_FOR_PLAYERS;
        this.privateLobby = privateGame;
        this.creationTime = Instant.now();
        this.borderX = 1200;
        this.borderY = 900;
        this.teams = new Pair<>(new Team(), new Team());
        this.players = new ConcurrentHashMap<>();
    }

    public Lobby(GameDTO gameDTO) {
        this.lobbyId = gameDTO.getId();
        this.lobbyName = gameDTO.getGameName();
        this.creatorName = gameDTO.getCreatorName();
        this.maxPlayers = gameDTO.getMaxPlayers();
        this.status = gameDTO.getStatus();
        this.privateLobby = gameDTO.getPrivateGame();
        this.creationTime = gameDTO.getCreationTime();
        this.borderX = gameDTO.getBorderX();
        this.borderY = gameDTO.getBorderY();
        // this.teams = gameDTO.getTeams();
        // Collection<PlayerDTO> playersDTO = gameDTO.getPlayers();
        // this.players = (List<PlayerDTO>) playersDTO;
    }
}
