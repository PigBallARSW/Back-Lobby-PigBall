package co.edu.eci.pigball.lobby.model;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private AtomicInteger score;
    private AtomicInteger players;
    private LinkedList<String> events;
}
