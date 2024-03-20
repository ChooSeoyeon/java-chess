package chess.model.piece;

import chess.model.board.Position;

public class KingPiece extends Piece {
    public KingPiece(Color color) {
        super(color, Type.KING);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return Math.abs(fileGap) <= 1 && Math.abs(rankGap) <= 1;
    }
}
