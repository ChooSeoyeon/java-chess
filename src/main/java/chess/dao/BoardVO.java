package chess.dao;

import chess.model.board.Board;
import chess.model.piece.Color;
import chess.model.piece.Piece;
import chess.model.position.Position;
import java.util.Map;

public record BoardVO(Long id, String currentTurn, String winner) {
    public static BoardVO from(Board board) {
        return new BoardVO(
                null,
                board.getCurrentColor().name(),
                board.determineWinner().name()
        );
    }

    public Board toBoard(Map<Position, Piece> squares) {
        return new Board(
                squares,
                Color.valueOf(currentTurn)
        );
    }
}
