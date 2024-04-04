package chess.dao;

import chess.database.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public Long create(BoardVO board) {
        String sql = "INSERT INTO board (team_code, current_color, winner_color) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, board.teamCode());
            statement.setString(2, board.currentColor());
            statement.setString(3, board.winnerColor());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                return fetchGeneratedKey(generatedKeys);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Board를 생성하는데 실패했습니다.", e);
        }
    }

    public Optional<BoardVO> findById(Long boardId) {
        String sql = "SELECT * FROM board WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchBoard(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Board를 찾는데 실패했습니다.", e);
        }
    }

    public List<BoardVO> findAllByTeamCode(String teamCode) {
        String sql = "SELECT * FROM board WHERE team_code = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teamCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchBoards(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Team code로 Board를 찾는데 실패했습니다.", e);
        }
    }

    public Optional<BoardVO> findLastByTeamCode(String teamCode) {
        String sql = "SELECT * FROM board WHERE team_code = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teamCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchBoard(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Team code로 마지막 Board를 찾는데 실패했습니다.", e);
        }
    }

    public void updateCurrentColor(Long id, String currentColor) {
        String sql = "UPDATE board SET current_color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentColor);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Board의 Current color를 업데이트하는데 실패했습니다.", e);
        }
    }

    public void updateWinnerColor(Long id, String winnerColor) {
        String sql = "UPDATE board SET winner_color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, winnerColor);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Board의 Winner color를 업데이트하는데 실패했습니다.", e);
        }
    }

    private Long fetchGeneratedKey(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        throw new SQLException("Generated key를 찾을 수 없습니다.");
    }

    private List<BoardVO> fetchBoards(ResultSet resultSet) throws SQLException {
        List<BoardVO> boards = new ArrayList<>();
        while (resultSet.next()) {
            boards.add(mapToBoardVO(resultSet));
        }
        return boards;
    }

    private Optional<BoardVO> fetchBoard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(mapToBoardVO(resultSet));
        }
        return Optional.empty();
    }

    private BoardVO mapToBoardVO(ResultSet resultSet) throws SQLException {
        return new BoardVO(
                resultSet.getLong("id"),
                resultSet.getString("team_code"),
                resultSet.getString("current_color"),
                resultSet.getString("winner_color")
        );
    }
}
