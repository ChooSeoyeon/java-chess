package chess.model.piece;

import chess.model.board.Position;

public class RookPiece extends Piece {
    public RookPiece(Color color) {
        super(color, Type.ROOK);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return fileGap == 0 || rankGap == 0;
    }
}
