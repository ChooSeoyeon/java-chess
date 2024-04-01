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
        String sql = "INSERT INTO board (current_color, winner_color) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, board.currentColor());
            statement.setString(2, board.winnerColor());
            statement.executeUpdate();
            return fetchGeneratedKey(statement.getGeneratedKeys());
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

    public Optional<BoardVO> findLast() {
        String sql = "SELECT * FROM board ORDER BY id DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();) {
            return fetchBoard(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("Board 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private Optional<BoardVO> fetchBoard(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(new BoardVO(
                    resultSet.getLong("id"),
                    resultSet.getString("current_color"),
                    resultSet.getString("winner_color")
            ));
        }
        return Optional.empty();
    }
}
