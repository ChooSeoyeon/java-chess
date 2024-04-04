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
import java.util.List;
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
        GameStatus gameStatus = GameStatus.createPreparingGameStatus();
        while (gameStatus.isPreparing()) {
            retryOnException(() -> playTurnInPreparing(gameStatus));
        }
    }

    private void playTurnInPreparing(GameStatus gameStatus) {
        Command command = retryOnException(inputView::askStartOrRecordCommand);
        if (command == Command.RECORD) {
            record();
            return;
        }
        if (command == Command.START) {
            gameStatus.running();
            start(gameStatus);
            return;
        }
        throw new IllegalArgumentException("아직 제공하지 않는 기능입니다.");
    }

    private void record() {
        String teamCode = retryOnException(inputView::askTeamCode);
        List<Long> boardIds = boardService.getBoardRecordsWithTransaction(teamCode);
        outputView.printBoardRecords(boardIds);
        if (boardIds.isEmpty()) {
            return;
        }
        Long boardId = retryOnException(() -> inputView.askBoardIdToShowDetail(boardIds));
        Board board = boardService.getBoardRecordWithTransaction(boardId);
        showBoardRecord(board);
    }

    private void showBoardRecord(Board board) {
        showBoard(board);
        outputView.printWinner(board.determineWinner());
    }

    private void start(GameStatus gameStatus) {
        String teamCode = retryOnException(inputView::askTeamCode);
        Board board = boardService.getRunningBoardWithTransaction(teamCode);
        showBoard(board);
        while (gameStatus.isRunning()) {
            retryOnException(() -> playTurnInRunning(gameStatus, board, teamCode));
        }
    }

    private void showBoard(Board board) {
        BoardDTO boardDTO = BoardDTO.from(board);
        outputView.printBoard(boardDTO);
    }

    private void playTurnInRunning(GameStatus gameStatus, Board board, String teamCode) {
        Command command = inputView.askMoveOrStatusOrEndCommand();
        if (command == Command.END) {
            gameStatus.ending();
            return;
        }
        if (command == Command.STATUS) {
            showBoardStatus(board);
            return;
        }
        if (command == Command.MOVE) {
            moveAndShowResult(board, gameStatus, teamCode);
            return;
        }
        throw new IllegalArgumentException("아직 제공하지 않는 기능입니다.");
    }

    private void showBoardStatus(Board board) {
        Map<Color, Double> boardStatus = board.calculateScore();
        outputView.printBoardStatus(boardStatus);
    }

    private void moveAndShowResult(Board board, GameStatus gameStatus, String teamCode) {
        move(board, teamCode);
        showBoard(board);
        determineAndShowWinner(gameStatus, board, teamCode);
    }

    private void move(Board board, String teamCode) {
        PositionDTO sourcePositionDTO = inputView.askPosition();
        PositionDTO targetPositionDTO = inputView.askPosition();
        Movement movement = new Movement(sourcePositionDTO.toEntity(), targetPositionDTO.toEntity());
        boardService.movePiecesWithTransaction(board, movement, teamCode);
    }

    private void determineAndShowWinner(GameStatus gameStatus, Board board, String teamCode) {
        Color winner = boardService.determineWinnerWithTransaction(board, teamCode);
        if (boardService.isWinnerDetermined(winner)) {
            gameStatus.ending();
            outputView.printWinner(winner);
        }
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
