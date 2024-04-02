package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dao.BoardDao;
import chess.dao.PieceDao;
import chess.database.DatabaseConnectionManager;
import chess.database.TransactionManager;
import chess.model.board.Board;
import chess.model.piece.Color;
import chess.model.piece.PieceFixture;
import chess.model.position.Movement;
import chess.model.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardServiceIntegrationTest {
    private static final String TEST_PROPERTIES_PATH = "src/main/java/chess/resource/test.yml";

    BoardService boardService;

    @BeforeEach
    void setUp() throws Exception {
        DatabaseConnectionManager databaseConnectionManager = DatabaseConnectionManager.from(TEST_PROPERTIES_PATH);
        Connection connection = databaseConnectionManager.getConnection();
        initializeDatabase(connection);
        BoardDao boardDao = new BoardDao(connection);
        PieceDao pieceDao = new PieceDao(connection);
        TransactionManager transactionManager = new TransactionManager(connection);
        boardService = new BoardService(boardDao, pieceDao, transactionManager);
    }

    private void initializeDatabase(Connection connection) throws SQLException {
        connection.prepareStatement("DELETE FROM piece").execute();
        connection.prepareStatement("DELETE FROM board").execute();
        connection.prepareStatement(
                        "INSERT INTO board (id, team_code, current_color, winner_color) VALUES (1, 'dora', 'WHITE', 'NONE')")
                .execute();
        connection.prepareStatement(
                        "INSERT INTO piece (id, board_id, type, color, file, `rank`) VALUES (1, 1, 'KING', 'WHITE', 1, 1)")
                .execute();
        connection.prepareStatement(
                        "INSERT INTO piece (id, board_id, type, color, file, `rank`) VALUES (2, 1, 'KING', 'BLACK', 1, 2)")
                .execute();
    }

    @Test
    void 팀의_마지막_보드를_조회해_우승자가_결정되지_않았다면_기존_보드를_사용한다() {
        // when
        Board board = boardService.getRunningBoard("dora");

        // then
        List<String> boardLines = PieceFixture.mappingBoard(board);
        assertThat(boardLines).containsExactly(
                "........",
                "........",
                "........",
                "........",
                "........",
                "........",
                "K.......",
                "k......."
        );
    }

    @Test
    void 팀의_마지막_보드를_조회해_우승자가_결정되었다면_새_보드를_생성한다() {
        // given
        boardService.updateWinner(Color.WHITE, "dora");

        // when
        Board board = boardService.getRunningBoard("dora");

        // then
        List<String> boardLines = PieceFixture.mappingBoard(board);
        assertThat(boardLines).containsExactly(
                "RNBQKBNR",
                "PPPPPPPP",
                "........",
                "........",
                "........",
                "........",
                "pppppppp",
                "rnbqkbnr"
        );
    }

    @Test
    void 팀의_기물이_움직일_수_있다면_기물의_위치와_턴을_업데이트한다() {
        // given
        Board board = boardService.getRunningBoard("dora");
        Movement movement = new Movement(Position.of(1, 1), Position.of(1, 2));

        // when
        boardService.updatePieceAndTurn(board, movement, "dora");

        // then
        Board updatedBoard = boardService.getRunningBoard("dora");
        List<String> boardLines = PieceFixture.mappingBoard(updatedBoard);
        assertThat(boardLines).containsExactly(
                "........",
                "........",
                "........",
                "........",
                "........",
                "........",
                "k.......",
                "........"
        );
    }
}
