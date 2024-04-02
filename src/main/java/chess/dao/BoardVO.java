package chess.dao;

import chess.model.board.Board;
import chess.model.piece.Color;
import chess.model.piece.Piece;
import chess.model.position.Position;
import java.util.Map;

public record BoardVO(Long id, String teamCode, String currentColor, String winnerColor) {
    public static BoardVO of(Board board, String teamCode) {
        return new BoardVO(
                null,
                teamCode,
                board.getCurrentColor().name(),
                board.determineWinner().name()
        );
    }

    public Board toBoard(Map<Position, Piece> squares) {
        return new Board(
                squares,
                Color.valueOf(currentColor)
        );
    }
}
