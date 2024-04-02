package chess.controller;

public class GameStatus {
    private GameStatusCommand command;

    private GameStatus(GameStatusCommand command) {
        this.command = command;
    }

    public static GameStatus createPreparingGameStatus() {
        return new GameStatus(GameStatusCommand.PREPARING);
    }

    public boolean isPreparing() {
        return command.isPreparing();
    }

    public boolean isRunning() {
        return command.isRunning();
    }

    public void running() {
        command = GameStatusCommand.RUNNING;
    }

    public void ending() {
        command = GameStatusCommand.ENDING;
    }
}
