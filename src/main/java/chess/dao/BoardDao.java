package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void create(BoardVO board) {
        String sql = "INSERT INTO board (current_turn, winner) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, board.currentTurn());
            statement.setString(2, board.winner());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Board 생성 중 오류가 발생했습니다.");
        }
    }
}
