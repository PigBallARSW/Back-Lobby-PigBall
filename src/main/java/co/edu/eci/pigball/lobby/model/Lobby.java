package co.edu.eci.pigball.lobby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lobby")
@Getter
@Setter
@AllArgsConstructor
public class Lobby {
    @Id
    private Long id;
    private String name;
    private String status;

    public Lobby(String name) {
        this.name = name;
    }
}
