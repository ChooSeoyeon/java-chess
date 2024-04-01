package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PieceDao {
    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public void create(PieceVO piece) {
        String sql = "INSERT INTO piece (board_id, type, color, file, `rank`) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, piece.boardId());
            statement.setString(2, piece.type());
            statement.setString(3, piece.color());
            statement.setInt(4, piece.file());
            statement.setInt(5, piece.rank());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Piece 생성 중 오류가 발생했습니다.");
        }
    }
}
