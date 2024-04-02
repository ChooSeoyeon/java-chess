package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) { // TODO: piece도 메모리 누수 방지 위해 ResultSet 닫아주기
                return fetchGeneratedKey(generatedKeys);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Board 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private Long fetchGeneratedKey(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        throw new SQLException("Generated key를 찾을 수 없습니다.");
    }

    public Optional<BoardVO> findLastByTeamCode(String teamCode) {
        String sql = "SELECT * FROM board WHERE team_code = ? ORDER BY id DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teamCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchBoard(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Board 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private Optional<BoardVO> fetchBoard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(new BoardVO(
                    resultSet.getLong("id"),
                    resultSet.getString("team_code"),
                    resultSet.getString("current_color"),
                    resultSet.getString("winner_color")
            ));
        }
        return Optional.empty();
    }

    public void updateCurrentColor(Long id, String currentColor) {
        String sql = "UPDATE board SET current_color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentColor);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Board 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void updateWinnerColor(Long id, String winnerColor) {
        String sql = "UPDATE board SET winner_color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, winnerColor);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Board 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
