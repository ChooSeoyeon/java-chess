package chess.dao;

import chess.model.piece.Piece;
import chess.model.position.Position;

public record PieceVO(Long id, Long boardId, String type, String color, Integer file, Integer rank) {
    public static PieceVO of(Long boardId, Position position, Piece piece) {
        return new PieceVO(
                null,
                boardId,
                piece.getType().name(),
                piece.getColor().name(),
                position.getFile(),
                position.getRank()
        );
    }
}
