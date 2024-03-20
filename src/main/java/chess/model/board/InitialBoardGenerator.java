package chess.model.board;

import chess.model.piece.Color;
import chess.model.piece.Piece;
import chess.model.piece.Type;
import java.util.HashMap;
import java.util.Map;

public class InitialBoardGenerator implements BoardGenerator {
    private static final Map<Position, Piece> squares = new HashMap<>();

    static {
        squares.put(Position.from(1, 8), new Piece(Color.BLACK, Type.ROOK));
        squares.put(Position.from(2, 8), new Piece(Color.BLACK, Type.KNIGHT));
        squares.put(Position.from(3, 8), new Piece(Color.BLACK, Type.BISHOP));
        squares.put(Position.from(4, 8), new Piece(Color.BLACK, Type.QUEEN));
        squares.put(Position.from(5, 8), new Piece(Color.BLACK, Type.KING));
        squares.put(Position.from(6, 8), new Piece(Color.BLACK, Type.BISHOP));
        squares.put(Position.from(7, 8), new Piece(Color.BLACK, Type.KNIGHT));
        squares.put(Position.from(8, 8), new Piece(Color.BLACK, Type.ROOK));
        for (int x = 1; x <= 8; x++) {
            squares.put(Position.from(x, 7), new Piece(Color.BLACK, Type.PAWN));
        }
        for (int x = 1; x <= 8; x++) {
            squares.put(Position.from(x, 2), new Piece(Color.WHITE, Type.PAWN));
        }
        squares.put(Position.from(1, 1), new Piece(Color.WHITE, Type.ROOK));
        squares.put(Position.from(2, 1), new Piece(Color.WHITE, Type.KNIGHT));
        squares.put(Position.from(3, 1), new Piece(Color.WHITE, Type.BISHOP));
        squares.put(Position.from(4, 1), new Piece(Color.WHITE, Type.QUEEN));
        squares.put(Position.from(5, 1), new Piece(Color.WHITE, Type.KING));
        squares.put(Position.from(6, 1), new Piece(Color.WHITE, Type.BISHOP));
        squares.put(Position.from(7, 1), new Piece(Color.WHITE, Type.KNIGHT));
        squares.put(Position.from(8, 1), new Piece(Color.WHITE, Type.ROOK));
    }

    @Override
    public Board create() {
        return new Board(squares);
    }
}
