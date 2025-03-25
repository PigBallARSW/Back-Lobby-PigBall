package co.edu.eci.pigball.lobby.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LobbyDTO {

    private Long id;
    private String name;
    private String status;
//    public LobbyDTO(Collection<Player> players) {
//        this.players = List.copyOf(players);
//    }

//    public static GameDTO toDTO(Game game) {
//        Collection<Player> players = game.getPlayers().values();
//        return new GameDTO(players);
//    }
//
//    public static Collection<GameDTO> toDTO(Collection<Game> games) {
//        return games.stream().map(GameDTO::toDTO).toList();
//    }
}
