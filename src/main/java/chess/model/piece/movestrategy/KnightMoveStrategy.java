package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class KnightMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return (fileGap == 2 && rankGap == 1) || (fileGap == 1 && rankGap == 2);
    }
}
