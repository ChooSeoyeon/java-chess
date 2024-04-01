package chess.controller;

import chess.dto.BoardDTO;
import chess.dto.PositionDTO;
import chess.model.board.Board;
import chess.model.piece.Color;
import chess.model.position.Movement;
import chess.service.BoardService;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;
import java.util.function.Supplier;

public class ChessGameController {
    private final OutputView outputView;
    private final InputView inputView;
    private final BoardService boardService;

    public ChessGameController(OutputView outputView, InputView inputView, BoardService boardService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.boardService = boardService;
    }

    public void run() {
        outputView.printGameIntro();
        retryOnException(inputView::askStartCommand);
        start();
    }

    private void start() {
        Board board = boardService.getRunningBoard();
        GameStatus gameStatus = new GameStatus();
        showBoard(board);
        while (gameStatus.isRunning()) {
            retryOnException(() -> playTurn(gameStatus, board));
        }
    }

    private void showBoard(Board board) {
        BoardDTO boardDTO = BoardDTO.from(board);
        outputView.printBoard(boardDTO);
    }

    private void playTurn(GameStatus gameStatus, Board board) {
        Command command = inputView.askMoveOrStatusOrEndCommand();
        if (command == Command.END) {
            gameStatus.stop();
            return;
        }
        if (command == Command.STATUS) {
            showBoardStatus(board);
            return;
        }
        if (command == Command.MOVE) {
            moveAndShowResult(board, gameStatus);
            return;
        }
        throw new IllegalArgumentException("아직 제공하지 않는 기능입니다.");
    }

    private void moveAndShowResult(Board board, GameStatus gameStatus) {
        move(board);
        showBoard(board);
        determineWinner(gameStatus, board);
    }

    private void move(Board board) {
        PositionDTO sourcePositionDTO = inputView.askPosition();
        PositionDTO targetPositionDTO = inputView.askPosition();
        Movement movement = new Movement(sourcePositionDTO.toEntity(), targetPositionDTO.toEntity());
        board.move(movement);
        boardService.updatePieceAndTurn(board, movement);
    }

    private void determineWinner(GameStatus gameStatus, Board board) {
        Color winner = board.determineWinner();
        if (winner != Color.NONE) {
            gameStatus.stop();
            outputView.printWinner(winner);
            boardService.updateWinner(winner);
        }
    }

    private void showBoardStatus(Board board) {
        Map<Color, Double> boardStatus = board.calculateScore();
        outputView.printBoardStatus(boardStatus);
    }

    private void retryOnException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printException(e.getMessage());
            retryOnException(action);
        }
    }

    private <T> T retryOnException(Supplier<T> retryOperation) {
        try {
            return retryOperation.get();
        } catch (IllegalArgumentException e) {
            outputView.printException(e.getMessage());
            return retryOnException(retryOperation);
        }
    }
}