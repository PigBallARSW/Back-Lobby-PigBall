package co.edu.eci.pigball.lobby.TestModel;

import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.LobbyStatus;
import co.edu.eci.pigball.lobby.model.dto.GameDTO;
import co.edu.eci.pigball.lobby.model.dto.PlayerDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestLobby {

    private Lobby lobby;

    @BeforeEach
    void setUp() {
        lobby = new Lobby("1234", "Test Lobby", "Creator", 4, false, "classic");
    }

    @Test
    void testConstructorWithParameters() {
        assertNotNull(lobby.getLobbyId());
        assertEquals("Test Lobby", lobby.getLobbyName());
        assertEquals("Creator", lobby.getCreatorName());
        assertEquals(4, lobby.getMaxPlayers());
        assertEquals(LobbyStatus.WAITING_FOR_PLAYERS, lobby.getStatus());
        assertFalse(lobby.isPrivateLobby());
        assertNotNull(lobby.getCreationTime());
        assertEquals(1200, lobby.getBorderX());
        assertEquals(900, lobby.getBorderY());
        assertNotNull(lobby.getTeams());
        assertNotNull(lobby.getPlayers());
    }

    @Test
    void testConstructorWithGameDTO() {
        GameDTO mockGameDTO = mock(GameDTO.class);
        when(mockGameDTO.getId()).thenReturn("5678");
        when(mockGameDTO.getGameName()).thenReturn("Mocked Game");
        when(mockGameDTO.getCreatorName()).thenReturn("Mocked Creator");
        when(mockGameDTO.getMaxPlayers()).thenReturn(6);
        when(mockGameDTO.getStatus()).thenReturn(LobbyStatus.IN_PROGRESS);
        when(mockGameDTO.getPrivateGame()).thenReturn(true);
        when(mockGameDTO.getCreationTime()).thenReturn(Instant.now());
        when(mockGameDTO.getBorderX()).thenReturn(1400);
        when(mockGameDTO.getBorderY()).thenReturn(1000);

        Lobby newLobby = new Lobby(mockGameDTO);

        assertEquals("5678", newLobby.getLobbyId());
        assertEquals("Mocked Game", newLobby.getLobbyName());
        assertEquals("Mocked Creator", newLobby.getCreatorName());
        assertEquals(6, newLobby.getMaxPlayers());
        assertEquals(LobbyStatus.IN_PROGRESS, newLobby.getStatus());
        assertTrue(newLobby.isPrivateLobby());
        assertNotNull(newLobby.getCreationTime());
        assertEquals(1400, newLobby.getBorderX());
        assertEquals(1000, newLobby.getBorderY());
    }

// @Test
// void testPlayerManagement() {
//     // 1. Configura el mock del jugador
//     PlayerDTO mockPlayerDTO = mock(PlayerDTO.class);
//     when(mockPlayerDTO.getSessionId()).thenReturn("player-1");


//     lobby.addPlayer(mockPlayerDTO); // Asegúrate de que este método exista en tu clase Lobby

//     // 3. Verifica que el jugador fue agregado
//     assertEquals(1, lobby.getPlayers().size());
// }

    @Test
    void testLobbyStatusToString() {
        assertEquals("waiting for players", LobbyStatus.WAITING_FOR_PLAYERS.toString());
        assertEquals("starting", LobbyStatus.STARTING.toString());
        assertEquals("in progress", LobbyStatus.IN_PROGRESS.toString());
        assertEquals("finished", LobbyStatus.FINISHED.toString());
        assertEquals("abandoned", LobbyStatus.ABANDONED.toString());
    }
}
