package chess.dao;

import chess.database.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            throw new DataAccessException("Piece를 생성하는데 실패했습니다.", e);
        }
    }

    public List<PieceVO> findAllByBoardId(Long boardId) {
        String sql = "SELECT * FROM piece WHERE board_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchPieces(resultSet);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Board ID로 Piece를 찾는데 실패했습니다.", e);
        }
    }

    public Optional<PieceVO> findByBoardIdAndFileAndRank(Long boardId, int file, int rank) {
        String sql = "SELECT * FROM piece WHERE board_id = ? AND file = ? AND `rank` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            statement.setInt(2, file);
            statement.setInt(3, rank);
            try (ResultSet resultSet = statement.executeQuery()) {
                return fetchPiece(resultSet);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Board ID, File, Rank로 Piece를 찾는데 실패했습니다.", e);
        }
    }

    public void updateTypeAndColor(Long id, String type, String color) {
        String sql = "UPDATE piece SET type = ?, color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type);
            statement.setString(2, color);
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Piece의 Type과 Color를 업데이트하는데 실패했습니다.", e);
        }
    }

    private List<PieceVO> fetchPieces(ResultSet resultSet) throws SQLException {
        List<PieceVO> pieces = new ArrayList<>();
        while (resultSet.next()) {
            pieces.add(mapToPieceVO(resultSet));
        }
        return pieces;
    }

    private Optional<PieceVO> fetchPiece(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(mapToPieceVO(resultSet));
        }
        return Optional.empty();
    }

    private PieceVO mapToPieceVO(ResultSet resultSet) throws SQLException {
        return new PieceVO(
                resultSet.getLong("id"),
                resultSet.getLong("board_id"),
                resultSet.getString("type"),
                resultSet.getString("color"),
                resultSet.getInt("file"),
                resultSet.getInt("rank")
        );
    }
}
