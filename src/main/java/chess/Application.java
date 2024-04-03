package chess;

import chess.controller.ChessGameController;
import chess.dao.BoardDao;
import chess.dao.PieceDao;
import chess.database.DatabaseConnectionManager;
import chess.database.TransactionManager;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    private static final String PROPERTIES_PATH = "src/main/java/chess/resource/production.yml";

    public static void main(String[] args) throws SQLException {
        DatabaseConnectionManager databaseConnectionManager = DatabaseConnectionManager.from(PROPERTIES_PATH);
        Connection connection = databaseConnectionManager.getConnection();

        BoardDao boardDao = new BoardDao(connection);
        PieceDao pieceDao = new PieceDao(connection);
        TransactionManager transactionManager = new TransactionManager(connection);

        BoardService boardService = new BoardService(boardDao, pieceDao, transactionManager);
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        ChessGameController chessGameController = new ChessGameController(outputView, inputView, boardService);
        chessGameController.run();

        connection.close();
    }
}
