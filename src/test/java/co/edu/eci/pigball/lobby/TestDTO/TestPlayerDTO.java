package co.edu.eci.pigball.lobby.TestDTO;

import co.edu.eci.pigball.lobby.model.DTO.PlayerDTO;
import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestPlayerDTO {

    @Test
    void testToDTO() {
        Lobby mockLobby = mock(Lobby.class);
        when(mockLobby.getLobbyId()).thenReturn("1234");

        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("John");
        when(mockPlayer.getSessionId()).thenReturn("session-1");
        when(mockPlayer.getTeam()).thenReturn(1);
        when(mockPlayer.getX()).thenReturn(100.0);
        when(mockPlayer.getY()).thenReturn(200.0);
        when(mockPlayer.getLobby()).thenReturn(mockLobby);

        PlayerDTO playerDTO = PlayerDTO.toDTO(mockPlayer);

        assertEquals("John", playerDTO.getName());
        assertEquals("session-1", playerDTO.getSessionId());
        assertEquals(1, playerDTO.getTeam());
        assertEquals(100, playerDTO.getX());
        assertEquals(200, playerDTO.getY());
        assertEquals("1234", playerDTO.getGameId());
        assertEquals(mockPlayer.getX(), 100.0);
        assertEquals(mockPlayer.getY(), 200.0);
    }

    @Test
    void testToDTOCollection() {
        Lobby mockLobby = mock(Lobby.class);
        when(mockLobby.getLobbyId()).thenReturn("5678");

        Player mockPlayer1 = mock(Player.class);
        when(mockPlayer1.getName()).thenReturn("Alice");
        when(mockPlayer1.getSessionId()).thenReturn("session-2");
        when(mockPlayer1.getTeam()).thenReturn(2);
        when(mockPlayer1.getX()).thenReturn(50.0);
        when(mockPlayer1.getY()).thenReturn(75.0);
        when(mockPlayer1.getLobby()).thenReturn(mockLobby);

        Player mockPlayer2 = mock(Player.class);
        when(mockPlayer2.getName()).thenReturn("Bob");
        when(mockPlayer2.getSessionId()).thenReturn("session-3");
        when(mockPlayer2.getTeam()).thenReturn(1);
        when(mockPlayer2.getX()).thenReturn(150.0);
        when(mockPlayer2.getY()).thenReturn(250.0);
        when(mockPlayer2.getLobby()).thenReturn(mockLobby);

        Collection<PlayerDTO> playerDTOs = PlayerDTO.toDTO(List.of(mockPlayer1, mockPlayer2));
        assertEquals(2, playerDTOs.size());

        PlayerDTO[] array = playerDTOs.toArray(new PlayerDTO[0]);
        PlayerDTO dto1 = array[0];
        PlayerDTO dto2 = array[1];

        assertEquals("Alice", dto1.getName());
        assertEquals("session-2", dto1.getSessionId());
        assertEquals(2, dto1.getTeam());
        assertEquals(50, dto1.getX());
        assertEquals(75, dto1.getY());
        assertEquals("5678", dto1.getGameId());

        assertEquals("Bob", dto2.getName());
        assertEquals("session-3", dto2.getSessionId());
        assertEquals(1, dto2.getTeam());
        assertEquals(150, dto2.getX());
        assertEquals(250, dto2.getY());
        assertEquals("5678", dto2.getGameId());
    }
}
