package chess.service;

import chess.dao.BoardDao;
import chess.dao.BoardVO;
import chess.database.TransactionManager;
import chess.model.board.Board;
import chess.model.board.InitialBoardGenerator;

public class BoardService {
    private final BoardDao boardDao;
    private final TransactionManager transactionManager;

    public BoardService(BoardDao boardDao, TransactionManager transactionManager) {
        this.boardDao = boardDao;
        this.transactionManager = transactionManager;
    }

    public Board getRunningBoard() {
        return transactionManager.performTransaction(this::getRunningBoardWithTransaction);
    }

    private Board getRunningBoardWithTransaction() {
        // TODO: 아직 안끝난 게임이 있는지 확인하고 있다면 그 게임을 반환하기
        Board newBoard = new InitialBoardGenerator().create();
        BoardVO newBoardVO = BoardVO.from(newBoard);
        // TODO: Piece와 Position도 저장하기
        boardDao.create(newBoardVO);
        return newBoard;
    }
}
