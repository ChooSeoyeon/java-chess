package chess.model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void 보드는_64개의_칸으로_구성한다() {
        // given
        Board board = new Board(new HashMap<>());

        // when, then
        assertThat(board.getSignatures().size()).isEqualTo(64);
    }

    @Test
    void 기물이_특정_위치로_움직일_수_있다면_움직인다() {
        // given
        Board board = new InitialBoardGenerator().create();
        board.movePiece(Position.from(2, 2), Position.from(2, 3));

        // when, then
        List<String> boardLines = board.getLines().stream()
                .map(line -> String.join("", line))
                .toList();
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
    void 기물이_특정_위치로_움직일_수_없다면_예외가_발생한다() {
        // given
        Board board = new InitialBoardGenerator().create();

        // when, then
        assertThatThrownBy(() -> board.movePiece(Position.from(2, 2), Position.from(1, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
