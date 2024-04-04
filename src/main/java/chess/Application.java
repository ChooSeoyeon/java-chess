package chess;

import chess.controller.ChessGameController;
import chess.dao.BoardDao;
import chess.dao.PieceDao;
import chess.database.ProductionDatabaseConnectionManager;
import chess.database.TransactionManager;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = new ProductionDatabaseConnectionManager().getConnection()) {
            BoardDao boardDao = new BoardDao(connection);
            PieceDao pieceDao = new PieceDao(connection);
            TransactionManager transactionManager = new TransactionManager(connection);

            BoardService boardService = new BoardService(boardDao, pieceDao, transactionManager);
            OutputView outputView = new OutputView();
            InputView inputView = new InputView();

            ChessGameController chessGameController = new ChessGameController(outputView, inputView, boardService);
            chessGameController.run();
        }
    }
}
