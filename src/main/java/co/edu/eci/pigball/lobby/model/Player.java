package co.edu.eci.pigball.lobby.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
public class Player {
    private String name;
    private String sessionId;
    private Integer team;
    private AtomicReference<Double> x;
    private AtomicReference<Double> y;
    private Lobby lobby;

    public double getX() {
        return x.get();
    }

    public double getY() {
        return y.get();
    }
}
