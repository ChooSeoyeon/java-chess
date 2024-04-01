package chess.dao;

import chess.model.board.Board;

public record BoardVO(Long id, String currentTurn, String winner) {
    public static BoardVO from(Board board) {
        return new BoardVO(
                null,
                board.getCurrentColor().name(),
                board.determineWinner().name()
        );
    }
}
