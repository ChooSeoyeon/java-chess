package chess.model.piece.movestrategy;

import chess.model.board.Position;

public class EmptyMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position source, Position destination) {
        return false;
    }
}
