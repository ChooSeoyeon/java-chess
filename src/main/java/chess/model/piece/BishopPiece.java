package chess.model.piece;

import chess.model.board.Position;

public class BishopPiece extends Piece {
    public BishopPiece(Color color) {
        super(color, Type.BISHOP);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return Math.abs(fileGap) == Math.abs(rankGap);
    }
}
