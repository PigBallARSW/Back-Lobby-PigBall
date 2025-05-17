package co.edu.eci.pigball.lobby.TestService;

import co.edu.eci.pigball.lobby.model.Lobby;
import co.edu.eci.pigball.lobby.model.dto.GameDTO;
import co.edu.eci.pigball.lobby.model.dto.LobbyDTO;
import co.edu.eci.pigball.lobby.repository.LobbyRepository;
import co.edu.eci.pigball.lobby.service.LobbyService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TestLobbyService {
    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LobbyService lobbyService;

    private final String gameServiceUrl = "http://mock-game-service";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        lobbyService = new LobbyService(restTemplate, lobbyRepository);
    }

    @Test
    void testCreateLobby() {
        LobbyDTO lobbyDTO = new LobbyDTO();
        GameDTO gameDTO = new GameDTO(lobbyDTO);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(GameDTO.class)))
                .thenReturn(new ResponseEntity<>(gameDTO, HttpStatus.OK));

        // Simulación correcta del repositorio
        when(lobbyRepository.save(any(Lobby.class))).thenReturn(null);

        LobbyDTO result = lobbyService.createLobby(lobbyDTO);

        assertNotNull(result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class),
                eq(GameDTO.class));
        verify(lobbyRepository, times(1)).save(any(Lobby.class));
    }

    @Test
    void testGetLobby() {
        String lobbyId = "test-lobby";

        // Crear un GameDTO con valores por defecto para evitar null
        GameDTO gameDTO = new GameDTO();
        gameDTO.setPrivateGame(false); // Evita el NullPointerException

        when(restTemplate.exchange(contains(lobbyId), eq(HttpMethod.GET), any(HttpEntity.class), eq(GameDTO.class)))
                .thenReturn(new ResponseEntity<>(gameDTO, HttpStatus.OK));

        LobbyDTO result = lobbyService.getLobby(lobbyId);

        assertNotNull(result);
        verify(restTemplate, times(1)).exchange(contains(lobbyId), eq(HttpMethod.GET), any(HttpEntity.class),
                eq(GameDTO.class));
    }

    @Test
    void testGetAllLobbies() {
        List<GameDTO> gameList = Collections.singletonList(new GameDTO());
        ResponseEntity<List<GameDTO>> responseEntity = new ResponseEntity<>(gameList, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<GameDTO>>>any())).thenReturn(responseEntity);

        List<LobbyDTO> result = lobbyService.getAllLobbies();

        assertNotNull(result);
        assertFalse(result.isEmpty());

    }

    
    @Test
    void testCreateLobby_Exception() {
        LobbyDTO lobbyDTO = new LobbyDTO();

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(GameDTO.class)))
                .thenThrow(new RuntimeException("Error simulado en createLobby"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lobbyService.createLobby(lobbyDTO);
        });

        assertEquals("Error al crear el lobby", exception.getMessage());
    }

    @Test
    void testGetLobby_Exception() {
        String lobbyId = "test-lobby";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(GameDTO.class)))
                .thenThrow(new RuntimeException("Error simulado en getLobby"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lobbyService.getLobby(lobbyId);
        });

        assertEquals("Error al obtener el juego", exception.getMessage());
    }

    @Test
    void testGetAllLobbies_Exception() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<GameDTO>>>any()))
                .thenThrow(new RuntimeException("Error simulado en getAllLobbies"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lobbyService.getAllLobbies();
        });

        assertEquals("Error al obtener todos los juegos", exception.getMessage());
    }

    @Test
    void testRemoveGame_Exception() {
        String gameId = "test-game";

        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class)))
                .thenThrow(new RuntimeException("Error simulado en removeGame"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lobbyService.removeLobby(gameId);
        });

        assertEquals("Error al eliminar el juego", exception.getMessage());
    }
}
