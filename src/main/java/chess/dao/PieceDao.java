package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<PieceVO> findAllByBoardId(Long boardId) {
        String sql = "SELECT * FROM piece WHERE board_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            ResultSet resultSet = statement.executeQuery();
            return fetchPieces(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("Piece 조회 중 오류가 발생했습니다.");
        }
    }

    private List<PieceVO> fetchPieces(ResultSet resultSet) throws SQLException {
        List<PieceVO> pieces = new ArrayList<>();
        while (resultSet.next()) {
            pieces.add(new PieceVO(
                    resultSet.getLong("id"),
                    resultSet.getLong("board_id"),
                    resultSet.getString("type"),
                    resultSet.getString("color"),
                    resultSet.getInt("file"),
                    resultSet.getInt("rank")
            ));
        }
        return pieces;
    }
}
