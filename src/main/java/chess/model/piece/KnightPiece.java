package chess.model.piece;

import chess.model.board.Position;

public class KnightPiece extends Piece {
    public KnightPiece(Color color) {
        super(color, Type.KNIGHT);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return (fileGap == 1 && rankGap == 2) || (fileGap == 2 && rankGap == 1)
                || (fileGap == 1 && rankGap == -2) || (fileGap == 2 && rankGap == -1)
                || (fileGap == -1 && rankGap == 2) || (fileGap == -2 && rankGap == 1)
                || (fileGap == -1 && rankGap == -2) || (fileGap == -2 && rankGap == -1);
    }
}
