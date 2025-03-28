package co.edu.eci.pigball.lobby.controller;
import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.repository.LobbyRepository;
import co.edu.eci.pigball.lobby.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/lobby")

public class LobbyController {
    @Autowired
    private LobbyService lobbyService;
    @PostMapping
    public ResponseEntity<?> createLobby(@RequestBody LobbyDTO lobbyDTO) {
        try {
            return new ResponseEntity<LobbyDTO>(lobbyService.createLobby(lobbyDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{lobbyId}")
    public ResponseEntity<?> getLobby(@PathVariable String lobbyId) {
        try {
            return new ResponseEntity<LobbyDTO>(lobbyService.getLobby(lobbyId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllLobbies() {
        try {
            List<LobbyDTO> allLobbies = lobbyService.getAllLobbies();
            return new ResponseEntity<List<LobbyDTO>>(allLobbies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{gameId}")
    public ResponseEntity<?> removeGame(@PathVariable String gameId) {
        try {
            lobbyService.removeGame(gameId);
            return new ResponseEntity<String>("Juego eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
