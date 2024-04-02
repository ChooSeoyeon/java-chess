package chess.controller;

public enum GameStatusCommand {
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
