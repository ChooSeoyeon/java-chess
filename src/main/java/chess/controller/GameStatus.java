package chess.controller;

public class GameStatus {
    private GameMode mode;

    private GameStatus(GameMode mode) {
        this.mode = mode;
    }

    public static GameStatus createPreparingGameStatus() {
        return new GameStatus(GameMode.PREPARING);
    }

    public boolean isPreparing() {
        return mode.isPreparing();
    }

    public boolean isRunning() {
        return mode.isRunning();
    }

    public void running() {
        mode = GameMode.RUNNING;
    }

    public void ending() {
        mode = GameMode.ENDING;
    }
}
