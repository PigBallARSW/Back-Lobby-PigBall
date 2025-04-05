package co.edu.eci.pigball.lobby.TestDTO;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.LobbyStatus;
import co.edu.eci.pigball.lobby.model.DTO.GameDTO;
import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.model.DTO.PlayerDTO;

public class TestLobbyDTO {

    @Test
    void testLobbyDTOConstructorWithLobby() {
        Lobby lobby = new Lobby("1", "Test Lobby", "Alice", 6, true);
        LobbyDTO lobbyDTO = new LobbyDTO(lobby);

        assertEquals(lobby.getLobbyId(), lobbyDTO.getId());
        assertEquals(lobby.getLobbyName(), lobbyDTO.getLobbyName());
        assertEquals(lobby.getCreatorName(), lobbyDTO.getCreatorName());
        assertEquals(lobby.getMaxPlayers(), lobbyDTO.getMaxPlayers());
        assertEquals(lobby.getStatus(), lobbyDTO.getStatus());
        assertEquals(lobby.isPrivateLobby(), lobbyDTO.isPrivateGame());
        assertEquals(lobby.getCreationTime(), lobbyDTO.getCreationTime());
        assertEquals(lobby.getBorderX(), lobbyDTO.getBorderX());
        assertEquals(lobby.getBorderY(), lobbyDTO.getBorderY());
    }

    @Test
    void testDefaultMaxPlayers() {
        LobbyDTO lobbyDTO = new LobbyDTO();
        assertEquals(4, lobbyDTO.getMaxPlayers());
    }

    @Test
    void testDefaultPrivateLobby() {
        Lobby lobby = new Lobby("3", "Lobby Without Private", "Carol", 8, false);
        LobbyDTO lobbyDTO = new LobbyDTO(lobby);
        assertFalse(lobbyDTO.isPrivateGame());
    }

    @Test
    void testStaticToDTO() {
        Lobby lobby = new Lobby("4", "Static Lobby", "Dave", 10, true);
        LobbyDTO lobbyDTO = LobbyDTO.toDTO(lobby);

        assertNotNull(lobbyDTO);
        assertEquals(lobby.getLobbyId(), lobbyDTO.getId());
        assertEquals(lobby.getLobbyName(), lobbyDTO.getLobbyName());
        assertEquals(lobby.getCreatorName(), lobbyDTO.getCreatorName());
        assertEquals(lobby.getMaxPlayers(), lobbyDTO.getMaxPlayers());
        assertEquals(lobby.isPrivateLobby(), lobbyDTO.isPrivateGame());
    }

    @Test
    void testStaticToDTOCollection() {
        Lobby lobby1 = new Lobby("1", "Lobby One", "Eve", 7, true);
        Lobby lobby2 = new Lobby("1", "Lobby Two", "Frank", 0, false);
        List<Lobby> lobbies = List.of(lobby1, lobby2);

        Collection<LobbyDTO> lobbyDTOs = LobbyDTO.toDTO(lobbies);
        assertEquals(2, lobbyDTOs.size());

        List<LobbyDTO> list = lobbyDTOs.stream().toList();
        LobbyDTO lobbyDTO1 = list.get(0);
        LobbyDTO lobbyDTO2 = list.get(1);

        assertNotNull(lobbyDTO1.getId());
        assertFalse(lobbyDTO1.getId().isEmpty());
        assertEquals("Lobby One", lobbyDTO1.getLobbyName());
        assertEquals("Eve", lobbyDTO1.getCreatorName());
        assertEquals(7, lobbyDTO1.getMaxPlayers());
        assertTrue(lobbyDTO1.isPrivateGame());

        assertNotNull(lobbyDTO2.getId());
        assertFalse(lobbyDTO2.getId().isEmpty());
        assertEquals("Lobby Two", lobbyDTO2.getLobbyName());
        assertEquals("Frank", lobbyDTO2.getCreatorName());
        assertEquals(0, lobbyDTO2.getMaxPlayers());
        assertFalse(lobbyDTO2.isPrivateGame());
    }

    @Test
    void testLobbyDTOConstructorFromGameDTO() {
        // Crear un GameDTO simulado
        String gameId = "game-123";
        String gameName = "Test Game";
        String creatorName = "Alice";
        int maxPlayers = 6;
        LobbyStatus status = LobbyStatus.IN_PROGRESS;
        boolean privateGame = true;
        Instant creationTime = Instant.now();
        int borderX = 1500;
        int borderY = 1000;

        List<PlayerDTO> players = List.of(
                new PlayerDTO("Player One", "session1", 1, 100, 200, gameId),
                new PlayerDTO("Player Two", "session2", 2, 300, 400, gameId));

        GameDTO gameDTO = new GameDTO(gameId, gameName, creatorName, maxPlayers, privateGame, status, creationTime,
                borderX, borderY, players);

        // Construir el LobbyDTO usando el constructor desde GameDTO
        LobbyDTO lobbyDTO = new LobbyDTO(gameDTO);

        // Validar que los valores fueron correctamente asignados
        assertEquals(gameId, lobbyDTO.getId());
        assertEquals(gameName, lobbyDTO.getLobbyName());
        assertEquals(creatorName, lobbyDTO.getCreatorName());
        assertEquals(maxPlayers, lobbyDTO.getMaxPlayers());
        assertEquals(status, lobbyDTO.getStatus());
        assertEquals(privateGame, lobbyDTO.isPrivateGame());
        assertEquals(creationTime, lobbyDTO.getCreationTime());
        assertEquals(borderX, lobbyDTO.getBorderX());
        assertEquals(borderY, lobbyDTO.getBorderY());

        // Validar que la lista de jugadores se asignó correctamente
        assertNotNull(lobbyDTO.getPlayers());
        assertEquals(2, lobbyDTO.getPlayers().size());

        PlayerDTO player1 = lobbyDTO.getPlayers().get(0);
        PlayerDTO player2 = lobbyDTO.getPlayers().get(1);

        assertEquals("Player One", player1.getName());
        assertEquals("session1", player1.getSessionId());
        assertEquals(1, player1.getTeam());
        assertEquals(100, player1.getX());
        assertEquals(200, player1.getY());
        assertEquals(gameId, player1.getGameId());

        assertEquals("Player Two", player2.getName());
        assertEquals("session2", player2.getSessionId());
        assertEquals(2, player2.getTeam());
        assertEquals(300, player2.getX());
        assertEquals(400, player2.getY());
        assertEquals(gameId, player2.getGameId());
    }
}