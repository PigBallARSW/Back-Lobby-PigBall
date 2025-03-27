package co.edu.eci.pigball.lobby.model;

public enum LobbyStatus {
    WAITING_FOR_PLAYERS,  // Esperando jugadores
    STARTING,             // Juego en proceso de inicio
    IN_PROGRESS,          // Juego en curso
    FINISHED,             // Juego terminado
    ABANDONED;          // Juego abandonado

    @Override
    public String toString() {
        // Convierte el nombre del enum en un formato más legible
        return name().replace("_", " ").toLowerCase();
    }
}
