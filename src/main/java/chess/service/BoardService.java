package chess.service;

import chess.dao.BoardDao;
import chess.dao.BoardVO;
import chess.dao.PieceDao;
import chess.dao.PieceVO;
import chess.database.TransactionManager;
import chess.model.board.Board;
import chess.model.board.InitialBoardGenerator;
import chess.model.piece.Piece;
import chess.model.position.Position;
import java.util.Map;

public class BoardService {
    private final BoardDao boardDao;
    private final PieceDao pieceDao;
    private final TransactionManager transactionManager;

    public BoardService(BoardDao boardDao, PieceDao pieceDao, TransactionManager transactionManager) {
        this.boardDao = boardDao;
        this.pieceDao = pieceDao;
        this.transactionManager = transactionManager;
    }

    public Board getRunningBoard() {
        return transactionManager.performTransaction(this::getRunningBoardWithTransaction);
    }

    private Board getRunningBoardWithTransaction() {
        // TODO: 아직 안끝난 게임이 있는지 확인하고 있다면 그 게임을 반환하기
        return createNewBoard();
    }

    private Board createNewBoard() {
        Board board = new InitialBoardGenerator().create();
        BoardVO boardVO = BoardVO.from(board);
        Long boardId = boardDao.create(boardVO);

        Map<Position, Piece> squares = board.getSquares();
        squares.forEach((position, piece) -> {
            PieceVO pieceVO = PieceVO.of(boardId, position, piece);
            pieceDao.create(pieceVO);
        });
        return board;
    }
}
