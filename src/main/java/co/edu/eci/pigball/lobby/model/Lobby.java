package co.edu.eci.pigball.lobby.model;

import co.edu.eci.pigball.lobby.model.DTO.GameDTO;
import co.edu.eci.pigball.lobby.model.DTO.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import co.edu.eci.pigball.lobby.java.Pair;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
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
    private ConcurrentHashMap<String, Player> players;

    private static final int velocity = 5;
    private static final double FRAME_RATE = 60;

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
    public Lobby(GameDTO gameDTO){
        this.lobbyId = gameDTO.getId();
        this.lobbyName = gameDTO.getGameName();
        this.creatorName = gameDTO.getCreatorName();
        this.maxPlayers = gameDTO.getMaxPlayers();
        this.status = gameDTO.getStatus();
        this.privateLobby = gameDTO.getPrivateGame();
        this.creationTime = gameDTO.getCreationTime();
        this.borderX = gameDTO.getBorderX();
        this.borderY = gameDTO.getBorderY();
        //this.teams = gameDTO.getTeams();
        Collection<PlayerDTO> playersDTO = gameDTO.getPlayers();
        //this.players = (List<PlayerDTO>) playersDTO;
    }
}
