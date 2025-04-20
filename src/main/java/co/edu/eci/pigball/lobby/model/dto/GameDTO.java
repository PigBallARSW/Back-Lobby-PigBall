package co.edu.eci.pigball.lobby.model.dto;

import co.edu.eci.pigball.lobby.java.Pair;
import co.edu.eci.pigball.lobby.model.Event;
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
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    private String id;
    private String gameName;
    private String creatorName;
    private Integer maxPlayers;
    private Boolean privateGame;
    private LobbyStatus status;
    private Instant creationTime;
    private int borderX;
    private int borderY;
    private List<PlayerDTO> players;
    private List<Pair<String, Event>> events;
    public GameDTO(LobbyDTO lobbyDTO) {
        this.id = lobbyDTO.getId();
        this.gameName = lobbyDTO.getLobbyName();
        this.creatorName = lobbyDTO.getCreatorName();
        this.maxPlayers = lobbyDTO.getMaxPlayers();
        this.privateGame = lobbyDTO.isPrivateGame();
        this.events = lobbyDTO.getEvents();
    }

    public int getMaxPlayers() {
        if (maxPlayers == null)
            return 4;
        return maxPlayers;
    }

    public boolean isPrivateGame() {
        if (privateGame == null)
            return false;
        return privateGame;
    }

    public static GameDTO toDTO(LobbyDTO lobbyDTO) {
        return new GameDTO(lobbyDTO);
    }

    public static Collection<GameDTO> toDTO(Collection<LobbyDTO> lobbies) {
        return lobbies.stream().map(GameDTO::toDTO).toList();
    }
}
