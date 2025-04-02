package co.edu.eci.pigball.lobby.TestModel;

import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPlayer {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(); // Instancia real de Player
        player.setName("JohnDoe");
        player.setSessionId("session-123");
        player.setTeam(1);
        player.setLobby(new Lobby());
    }

    @Test
    void testSetName() {
        assertEquals("JohnDoe", player.getName());
    }

    @Test
    void testSetSessionId() {
        assertEquals("session-123", player.getSessionId());
    }

    @Test
    void testSetTeam() {
        assertEquals(1, player.getTeam());
    }

    @Test
    void testSetLobby() {
        assertNotNull(player.getLobby());
    }

    @Test
    void testSetXAndY() {
        player.setX(new java.util.concurrent.atomic.AtomicInteger(100));
        player.setY(new java.util.concurrent.atomic.AtomicInteger(200));

        assertEquals(100, player.getX());
        assertEquals(200, player.getY());
    }
}