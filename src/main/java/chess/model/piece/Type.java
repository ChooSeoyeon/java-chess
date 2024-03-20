package chess.model.piece;

import chess.model.piece.movestrategy.BishopMoveStrategy;
import chess.model.piece.movestrategy.BlackPawnMoveStrategy;
import chess.model.piece.movestrategy.EmptyMoveStrategy;
import chess.model.piece.movestrategy.KingMoveStrategy;
import chess.model.piece.movestrategy.KnightMoveStrategy;
import chess.model.piece.movestrategy.MoveStrategy;
import chess.model.piece.movestrategy.QueenMoveStrategy;
import chess.model.piece.movestrategy.RookMoveStrategy;
import chess.model.piece.movestrategy.WhitePawnMoveStrategy;
import java.util.List;

public enum Type {
    BISHOP("b", List.of(new BishopMoveStrategy())),
    ROOK("r", List.of(new RookMoveStrategy())),
    QUEEN("q", List.of(new QueenMoveStrategy())),
    KNIGHT("n", List.of(new KnightMoveStrategy())),
    PAWN("p", List.of(new WhitePawnMoveStrategy(), new BlackPawnMoveStrategy())),
    KING("k", List.of(new KingMoveStrategy())),
    NONE(".", List.of(new EmptyMoveStrategy()));

    private final Signature signature;
    private final List<MoveStrategy> moveStrategies;

    Type(String signature, List<MoveStrategy> moveStrategies) {
        this.signature = new Signature(signature);
        this.moveStrategies = moveStrategies;
    }

    public Signature getSignature() {
        return signature;
    }
}
