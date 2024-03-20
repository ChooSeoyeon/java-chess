package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class WhitePawnMoveStrategy implements MoveStrategy {
    private static final int startRank = 2;

    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return true;
    }
}
