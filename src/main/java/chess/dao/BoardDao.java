package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public Long create(BoardVO board) {
        String sql = "INSERT INTO board (current_turn, winner) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, board.currentTurn());
            statement.setString(2, board.winner());
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
}
