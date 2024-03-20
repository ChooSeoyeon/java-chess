package chess.model.piece;

import chess.model.board.Position;

public class QueenPiece extends Piece {
    public QueenPiece(Color color) {
        super(color, Type.QUEEN);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return Math.abs(fileGap) == Math.abs(rankGap) || fileGap == 0 || rankGap == 0;
    }
}
