package chess.model.board;

import chess.model.piece.BishopPiece;
import chess.model.piece.BlackPawnPiece;
import chess.model.piece.Color;
import chess.model.piece.KingPiece;
import chess.model.piece.KnightPiece;
import chess.model.piece.Piece;
import chess.model.piece.QueenPiece;
import chess.model.piece.RookPiece;
import chess.model.piece.WhitePawnPiece;
import java.util.HashMap;
import java.util.Map;

public class InitialBoardGenerator implements BoardGenerator {
    private static final Map<Position, Piece> squares = new HashMap<>();

    static {
        squares.put(Position.from(1, 8), new RookPiece(Color.BLACK));
        squares.put(Position.from(2, 8), new KnightPiece(Color.BLACK));
        squares.put(Position.from(3, 8), new BishopPiece(Color.BLACK));
        squares.put(Position.from(4, 8), new QueenPiece(Color.BLACK));
        squares.put(Position.from(5, 8), new KingPiece(Color.BLACK));
        squares.put(Position.from(6, 8), new BishopPiece(Color.BLACK));
        squares.put(Position.from(7, 8), new KnightPiece(Color.BLACK));
        squares.put(Position.from(8, 8), new RookPiece(Color.BLACK));
        for (int x = 1; x <= 8; x++) {
            squares.put(Position.from(x, 7), new BlackPawnPiece());
        }
        for (int x = 1; x <= 8; x++) {
            squares.put(Position.from(x, 2), new WhitePawnPiece());
        }
        squares.put(Position.from(1, 1), new RookPiece(Color.WHITE));
        squares.put(Position.from(2, 1), new KnightPiece(Color.WHITE));
        squares.put(Position.from(3, 1), new BishopPiece(Color.WHITE));
        squares.put(Position.from(4, 1), new QueenPiece(Color.WHITE));
        squares.put(Position.from(5, 1), new KingPiece(Color.WHITE));
        squares.put(Position.from(6, 1), new BishopPiece(Color.WHITE));
        squares.put(Position.from(7, 1), new KnightPiece(Color.WHITE));
        squares.put(Position.from(8, 1), new RookPiece(Color.WHITE));
    }

    @Override
    public Board create() {
        return new Board(squares);
    }
}
