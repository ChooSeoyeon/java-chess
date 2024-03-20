package chess.model.piece;

import chess.model.board.Position;

public abstract class Piece {
    protected final Color color;
    protected final Type type;

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public String getSignature() {
        Signature signature = type.getSignature();
        if (Color.BLACK == color) {
            return signature.getBlack();
        }
        return signature.getWhite();
    }

    public abstract boolean canMove(Position source, Position destination);
}
