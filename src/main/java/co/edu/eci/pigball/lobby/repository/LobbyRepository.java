package co.edu.eci.pigball.lobby.repository;

import co.edu.eci.pigball.lobby.model.Lobby;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbyRepository extends MongoRepository<Lobby, String> {
    Lobby findByLobbyName(String lobbyName);
}