package chess.model.piece.movestrategy;

import chess.model.board.Position;

public interface MoveStrategy {
    boolean canMove(Position source, Position destination);
}
