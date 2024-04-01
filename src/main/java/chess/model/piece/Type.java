package chess.model.piece;

import java.util.function.Function;

public enum Type {
    BISHOP(Bishop::from),
    ROOK(Rook::from),
    QUEEN(Queen::from),
    KNIGHT(Knight::from),
    PAWN(Pawn::from),
    KING(King::from),
    NONE(color -> Empty.getInstance());

    private final Function<Color, Piece> creator;

    Type(Function<Color, Piece> creator) {
        this.creator = creator;
    }

    public Piece createPiece(Color color) {
        return creator.apply(color);
    }
}
