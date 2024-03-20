package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class BishopMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return Math.abs(fileGap) == Math.abs(rankGap);
    }
}
