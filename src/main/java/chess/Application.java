package chess;

import chess.controller.ChessGameController;
import chess.dao.BoardDao;
import chess.database.DatabaseConnectionManager;
import chess.database.TransactionManager;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        Connection connection = DatabaseConnectionManager.getConnection();
        TransactionManager transactionManager = new TransactionManager(connection);
        BoardDao boardDao = new BoardDao(connection);
        BoardService boardService = new BoardService(boardDao, transactionManager);

        ChessGameController chessGameController = new ChessGameController(outputView, inputView, boardService);
        chessGameController.run();
    }
}
