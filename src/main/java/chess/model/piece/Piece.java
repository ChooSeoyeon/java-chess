package chess.model.piece;

public class Piece {
    private final Color color;
    private final Type type;

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
}
