package co.edu.eci.pigball.lobby.TestController;

import co.edu.eci.pigball.lobby.controller.LobbyController;
import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.model.LobbyStatus;
import co.edu.eci.pigball.lobby.service.LobbyService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TestLobbyController {

    private MockMvc mockMvc;

    @Mock
    private LobbyService lobbyService;

    @InjectMocks
    private LobbyController lobbyController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private LobbyDTO sampleLobbyDTO;


    @BeforeEach
    void setUp() {
        // Registramos el módulo para soportar Java 8 Date/Time (Instant)
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(lobbyController).build();
        sampleLobbyDTO = new LobbyDTO(
                "1", "TestLobby", "Creator1", 8, LobbyStatus.WAITING_FOR_PLAYERS,
                false, Instant.now(), 100, 100, Collections.emptyList()
        );
    }

    @Test
    void createLobby_ShouldReturnCreatedLobby() throws Exception {
        when(lobbyService.createLobby(any(LobbyDTO.class))).thenReturn(sampleLobbyDTO);

        mockMvc.perform(post("/lobby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleLobbyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.lobbyName").value("TestLobby"))
                .andExpect(jsonPath("$.creatorName").value("Creator1"))
                .andExpect(jsonPath("$.maxPlayers").value(8))
                // El toString del enum devuelve "waiting for players"
                .andExpect(jsonPath("$.status").value("WAITING_FOR_PLAYERS"))
                .andExpect(jsonPath("$.privateLobby").value(false))
                .andExpect(jsonPath("$.borderX").value(100))
                .andExpect(jsonPath("$.borderY").value(100));
    }

    @Test
    void createLobby_ShouldReturnBadRequestOnError() throws Exception {
        when(lobbyService.createLobby(any(LobbyDTO.class))).thenThrow(new RuntimeException("Error al crear lobby"));

        mockMvc.perform(post("/lobby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleLobbyDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al crear lobby"));
    }

    @Test
    void getLobby_ShouldReturnLobby() throws Exception {
        when(lobbyService.getLobby(anyString())).thenReturn(sampleLobbyDTO);

        mockMvc.perform(get("/lobby/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.lobbyName").value("TestLobby"))
                .andExpect(jsonPath("$.creatorName").value("Creator1"))
                .andExpect(jsonPath("$.maxPlayers").value(8))
                // El estado se representa como "waiting for players"
                .andExpect(jsonPath("$.status").value("WAITING_FOR_PLAYERS"))
                .andExpect(jsonPath("$.privateLobby").value(false))
                .andExpect(jsonPath("$.borderX").value(100))
                .andExpect(jsonPath("$.borderY").value(100));
    }

    @Test
    void getLobby_ShouldReturnNotFoundOnError() throws Exception {
        when(lobbyService.getLobby(anyString())).thenThrow(new RuntimeException("Lobby no encontrado"));

        mockMvc.perform(get("/lobby/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Lobby no encontrado"));
    }

    @Test
    void getAllLobbies_ShouldReturnListOfLobbies() throws Exception {
        // Usamos dos estados válidos: WAITING_FOR_PLAYERS y FINISHED, por ejemplo.
        List<LobbyDTO> lobbies = List.of(
                new LobbyDTO("1", "Lobby1", "CreatorA", 4, LobbyStatus.WAITING_FOR_PLAYERS, false, Instant.now(), 100, 100, Collections.emptyList()),
                new LobbyDTO("2", "Lobby2", "CreatorB", 6, LobbyStatus.FINISHED, true, Instant.now(), 200, 200, Collections.emptyList())
        );

        when(lobbyService.getAllLobbies()).thenReturn(lobbies);

        mockMvc.perform(get("/lobby"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].lobbyName").value("Lobby1"))
                // El estado de Lobby1 se representará como "waiting for players"
                .andExpect(jsonPath("$[0].status").value("WAITING_FOR_PLAYERS"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].lobbyName").value("Lobby2"))
                // El estado de Lobby2 se representará como "finished"
                .andExpect(jsonPath("$[1].status").value("FINISHED"));
    }

    @Test
    void getAllLobbies_ShouldReturnBadRequestOnError() throws Exception {
        when(lobbyService.getAllLobbies()).thenThrow(new RuntimeException("Error al obtener lobbies"));

        mockMvc.perform(get("/lobby"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al obtener lobbies"));
    }

    @Test
    void removeGame_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(lobbyService).removeGame(anyString());

        mockMvc.perform(delete("/lobby/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Juego eliminado correctamente"));
    }

    @Test
    void removeGame_ShouldReturnBadRequestOnError() throws Exception {
        doThrow(new RuntimeException("Error al eliminar el juego")).when(lobbyService).removeGame(anyString());

        mockMvc.perform(delete("/lobby/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al eliminar el juego"));
    }
}
