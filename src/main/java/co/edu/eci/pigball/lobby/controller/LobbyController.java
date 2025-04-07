package co.edu.eci.pigball.lobby.controller;

import co.edu.eci.pigball.lobby.model.DTO.LobbyDTO;
import co.edu.eci.pigball.lobby.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/lobby")
public class LobbyController {
    
    private final LobbyService lobbyService;

    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @PostMapping
    public ResponseEntity<Object> createLobby(@RequestBody LobbyDTO lobbyDTO) {
        try {
            return new ResponseEntity<>(lobbyService.createLobby(lobbyDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{lobbyId}")
    public ResponseEntity<Object> getLobby(@PathVariable String lobbyId) {
        try {
            return new ResponseEntity<>(lobbyService.getLobby(lobbyId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<Object> getAllLobbies() {
        try {
            List<LobbyDTO> allLobbies = lobbyService.getAllLobbies();
            return new ResponseEntity<>(allLobbies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Object> removeGame(@PathVariable String gameId) {
        try {
            lobbyService.removeGame(gameId);
            return new ResponseEntity<>("Juego eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
