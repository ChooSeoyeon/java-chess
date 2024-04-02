package chess.controller;

public enum GameMode {
    PREPARING,
    RUNNING,
    ENDING;

    public boolean isPreparing() {
        return this == PREPARING;
    }

    public boolean isRunning() {
        return this == RUNNING;
    }
}
