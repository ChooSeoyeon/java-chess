package chess.model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.model.piece.Color;
import chess.model.piece.King;
import chess.model.piece.Pawn;
import chess.model.piece.Piece;
import chess.model.piece.PieceFixture;
import chess.model.position.Movement;
import chess.model.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    void 보드는_64개의_칸으로_구성한다() {
        // given
        Board board = new Board(new HashMap<>());

        // when, then
        int rowSize = board.getLines().size();
        int columnSize = board.getLines().get(0).size();
        assertThat(rowSize * columnSize).isEqualTo(64);
    }

    @Test
    void 기물이_특정_위치로_움직일_수_있다면_움직인다() {
        // given
        Board board = new InitialBoardGenerator().create();
        Movement movement = new Movement(Position.of(2, 2), Position.of(2, 3));

        // when, then
        board.move(movement);
        List<String> boardLines = PieceFixture.mappingBoard(board);
        assertThat(boardLines).containsExactly(
                "RNBQKBNR",
                "PPPPPPPP",
                "........",
                "........",
                "........",
                ".p......",
                "p.pppppp",
                "rnbqkbnr"
        );
    }

    @Test
    void 현재_턴에_맞지않는_기물을_움직이려하면_예외가_발생한다() {
        // given
        Board board = new InitialBoardGenerator().create();
        Movement movement = new Movement(Position.of(2, 7), Position.of(2, 6));

        // when, then
        assertThatThrownBy(() -> board.move(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("턴");
    }

    @Test
    void 기물의_행마법에_맞지않게_움직이려하면_예외가_발생한다() {
        // given
        Board board = new InitialBoardGenerator().create();
        Movement movement = new Movement(Position.of(2, 2), Position.of(2, 5));

        // when, then
        assertThatThrownBy(() -> board.move(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("행마법");
    }

    @Test
    void 슬라이딩_기물인데_이동_경로에_다른_기물이_있으면_예외가_발생한다() {
        // given
        Board board = new InitialBoardGenerator().create();
        Movement movement = new Movement(Position.of(1, 1), Position.of(1, 3));

        // when, then
        assertThatThrownBy(() -> board.move(movement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("경로");
    }

    @Test
    void 각_진영의_점수를_기물의_점수_합으로_계산한다() {
        // given
        Board board = new InitialBoardGenerator().create();

        // when
        Map<Color, Double> score = board.calculateScore();

        // then
        assertThat(score.get(Color.WHITE)).isEqualTo(38);
        assertThat(score.get(Color.BLACK)).isEqualTo(38);
    }

    @Test
    void file과_색이_같은_폰이_있는_경우_해당_폰들을_1점이_아닌_0_5점으로_계산한다() {
        // given
        Map<Position, Piece> squares = new HashMap<>();
        squares.put(Position.of(1, 2), Pawn.from(Color.WHITE));
        squares.put(Position.of(1, 3), Pawn.from(Color.WHITE));
        Board board = new Board(squares);

        // when
        Map<Color, Double> score = board.calculateScore();

        // then
        assertThat(score.get(Color.WHITE)).isEqualTo(1);
    }

    @Test
    void file과_색이_같은_폰이_없는_경우_해당_폰들을_1점으로_계산한다() {
        // given
        Map<Position, Piece> squares = new HashMap<>();
        squares.put(Position.of(1, 2), Pawn.from(Color.WHITE));
        squares.put(Position.of(2, 2), Pawn.from(Color.WHITE));
        Board board = new Board(squares);

        // when
        Map<Color, Double> score = board.calculateScore();

        // then
        assertThat(score.get(Color.WHITE)).isEqualTo(2);
    }

    @Test
    void 킹을_잡은_쪽이_승자가_된다() {
        // given
        Map<Position, Piece> squares = new HashMap<>();
        squares.put(Position.of(5, 1), King.from(Color.WHITE));
        squares.put(Position.of(5, 2), King.from(Color.BLACK));
        Board board = new Board(squares);

        // when
        Movement movement = new Movement(Position.of(5, 1), Position.of(5, 2));
        board.move(movement);

        // then
        assertThat(board.determineWinner()).isEqualTo(Color.WHITE);
    }

    @Test
    void 둘다_킹이_잡히지_않았다면_아직_승자는_없다() {
        // given
        Map<Position, Piece> squares = new HashMap<>();
        squares.put(Position.of(5, 1), King.from(Color.WHITE));
        squares.put(Position.of(5, 8), King.from(Color.BLACK));
        Board board = new Board(squares);

        // when & then
        assertThat(board.determineWinner()).isEqualTo(Color.NONE);
    }
}
