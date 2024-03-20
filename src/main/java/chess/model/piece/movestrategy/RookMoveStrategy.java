package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class RookMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return fileGap == 0 || rankGap == 0;
    }
}
