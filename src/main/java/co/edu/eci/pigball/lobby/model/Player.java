package co.edu.eci.pigball.lobby.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
@Getter
@Setter
public class Player {
    private String name;
    private String sessionId;
    private Integer team;
    private AtomicInteger x;
    private AtomicInteger y;
    private Lobby lobby;

    private static final int RADIUS = 20;
    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }
}
