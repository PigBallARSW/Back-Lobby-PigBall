package co.edu.eci.pigball.lobby.model.dto;

import co.edu.eci.pigball.lobby.java.Pair;
import co.edu.eci.pigball.lobby.model.Event;
import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.LobbyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LobbyDTO {

    private String id;
    private String lobbyName;
    private String creatorName;
    private Integer maxPlayers;
    private LobbyStatus status;
    private Boolean privateLobby;
    private Instant creationTime;
    private int borderX;
    private int borderY;
    private List<PlayerDTO> players;
    private List<Pair<String, Event>> events;
    private String style;

    public LobbyDTO(Lobby lobby) {
        this.id = lobby.getLobbyId();
        this.lobbyName = lobby.getLobbyName();
        this.creatorName = lobby.getCreatorName();
        this.maxPlayers = lobby.getMaxPlayers();
        this.status = lobby.getStatus();
        this.privateLobby = lobby.isPrivateLobby();
        this.creationTime = lobby.getCreationTime();
        this.borderX = lobby.getBorderX();
        this.borderY = lobby.getBorderY();
        this.events = lobby.getEvents();
        this.players = lobby.getPlayers();
    }

    public LobbyDTO(GameDTO gameDTO) {
        this.id = gameDTO.getId();
        this.lobbyName = gameDTO.getGameName();
        this.creatorName = gameDTO.getCreatorName();
        this.maxPlayers = gameDTO.getMaxPlayers();
        this.status = gameDTO.getStatus();
        this.privateLobby = gameDTO.isPrivateGame();
        this.creationTime = gameDTO.getCreationTime();
        this.borderX = gameDTO.getBorderX();
        this.borderY = gameDTO.getBorderY();
        Collection<PlayerDTO> playersDTO = gameDTO.getPlayers();
        this.players = (List<PlayerDTO>) playersDTO;
        this.events = gameDTO.getEvents();
    }

    public int getMaxPlayers() {
        if (maxPlayers == null)
            return 4;
        return maxPlayers;
    }

    public boolean isPrivateGame() {
        if (privateLobby == null)
            return false;
        return privateLobby;
    }

    public static LobbyDTO toDTO(Lobby lobby) {
        return new LobbyDTO(lobby);
    }

    public static Collection<LobbyDTO> toDTO(Collection<Lobby> lobbies) {
        return lobbies.stream().map(LobbyDTO::toDTO).toList();
    }
}
