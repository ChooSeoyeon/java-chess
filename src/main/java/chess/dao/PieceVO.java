package chess.dao;

import chess.model.piece.Color;
import chess.model.piece.Piece;
import chess.model.piece.Type;
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

    public Piece toPiece() {
        Type type = Type.valueOf(this.type);
        Color color = Color.valueOf(this.color);
        return type.createPiece(color);
    }

    public Position toPosition() {
        return Position.of(file, rank);
    }
}
