package chess.model.piece;

import chess.model.board.Position;

public class EmptyPiece extends Piece {
    public EmptyPiece() {
        super(Color.NONE, Type.NONE);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        return false;
    }
}
