package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class QueenMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position source, Position destination) {
        int fileGap = source.getFileGap(destination);
        int rankGap = source.getRankGap(destination);
        return Math.abs(fileGap) == Math.abs(rankGap) || fileGap == 0 || rankGap == 0;
    }
}
