package chess.model.piece;

import chess.model.board.Position;

public class BlackPawnPiece extends Piece {
    public BlackPawnPiece() {
        super(Color.BLACK, Type.PAWN);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        if (fileGap == 0 && rankGap == 1) {
            return true;
        }
        return fileGap == 0 && rankGap == 2 && source.getRank() == 7;
    }
}
