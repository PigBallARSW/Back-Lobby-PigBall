package co.edu.eci.pigball.lobby.TestDTO;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test; // Usa JUnit 5

import co.edu.eci.pigball.lobby.model.LobbyStatus;
import co.edu.eci.pigball.lobby.model.dto.GameDTO;
import co.edu.eci.pigball.lobby.model.dto.LobbyDTO;

class TestGameDTO {

    @Test
    void testGameDTOConstructorWithLobbyDTO() { // Hacerlo 'public' por compatibilidad
        LobbyDTO lobbyDTO = new LobbyDTO(
                "1",
                "Test Lobby",
                "Alice",
                6,
                LobbyStatus.WAITING_FOR_PLAYERS,
                true,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        GameDTO gameDTO = new GameDTO(lobbyDTO);

        assertEquals("1", gameDTO.getId());
        assertEquals("Test Lobby", gameDTO.getGameName());
        assertEquals("Alice", gameDTO.getCreatorName());
        assertEquals(6, gameDTO.getMaxPlayers());
        assertTrue(gameDTO.isPrivateGame());
    }

    @Test
    void testDefaultMaxPlayers() {
        LobbyDTO lobbyDTO = new LobbyDTO(
                "2",
                "Lobby Without Max",
                "Bob",
                null,
                LobbyStatus.STARTING,
                false,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        GameDTO gameDTO = new GameDTO(lobbyDTO);
        assertEquals(4, gameDTO.getMaxPlayers());
    }

    @Test
    void testDefaultPrivateGame() {
        LobbyDTO lobbyDTO = new LobbyDTO(
                "3",
                "Lobby Without Private",
                "Carol",
                8,
                LobbyStatus.IN_PROGRESS,
                null,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        GameDTO gameDTO = new GameDTO(lobbyDTO);
        assertFalse(gameDTO.isPrivateGame());
    }

    @Test
    void testStaticToDTO() {
        LobbyDTO lobbyDTO = new LobbyDTO(
                "4",
                "Static Lobby",
                "Dave",
                10,
                LobbyStatus.FINISHED,
                true,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        GameDTO gameDTO = GameDTO.toDTO(lobbyDTO);

        assertNotNull(gameDTO);
        assertEquals("4", gameDTO.getId());
        assertEquals("Static Lobby", gameDTO.getGameName());
        assertEquals("Dave", gameDTO.getCreatorName());
        assertEquals(10, gameDTO.getMaxPlayers());
        assertTrue(gameDTO.isPrivateGame());
    }

    @Test
    void testStaticToDTOCollection() {
        LobbyDTO lobbyDTO1 = new LobbyDTO(
                "5",
                "Lobby One",
                "Eve",
                7,
                LobbyStatus.ABANDONED,
                true,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        LobbyDTO lobbyDTO2 = new LobbyDTO(
                "6",
                "Lobby Two",
                "Frank",
                null,
                LobbyStatus.WAITING_FOR_PLAYERS,
                null,
                Instant.now(),
                100,
                100,
                new ArrayList<>(),
                new ArrayList<>(),
                "classic");
        List<LobbyDTO> lobbies = List.of(lobbyDTO1, lobbyDTO2);

        Collection<GameDTO> gameDTOs = GameDTO.toDTO(lobbies);
        assertEquals(2, gameDTOs.size());

        List<GameDTO> list = gameDTOs.stream().toList();
        GameDTO game1 = list.get(0);
        GameDTO game2 = list.get(1);

        assertEquals("5", game1.getId());
        assertEquals("Lobby One", game1.getGameName());
        assertEquals("Eve", game1.getCreatorName());
        assertEquals(7, game1.getMaxPlayers());
        assertTrue(game1.isPrivateGame());

        assertEquals("6", game2.getId());
        assertEquals("Lobby Two", game2.getGameName());
        assertEquals("Frank", game2.getCreatorName());
        assertEquals(4, game2.getMaxPlayers());
        assertFalse(game2.isPrivateGame());
    }
}
