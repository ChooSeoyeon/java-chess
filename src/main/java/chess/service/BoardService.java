package chess.service;

import chess.dao.BoardDao;
import chess.dao.BoardVO;
import chess.dao.PieceDao;
import chess.dao.PieceVO;
import chess.database.TransactionManager;
import chess.model.board.Board;
import chess.model.board.InitialBoardGenerator;
import chess.model.piece.Color;
import chess.model.piece.Piece;
import chess.model.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return boardDao.findLast()
                .filter(this::isRunningBoard)
                .map(this::getExistedBoard)
                .orElseGet(this::createNewBoard);
    }

    private boolean isRunningBoard(BoardVO boardVO) {
        return boardVO.winnerColor().equals(Color.NONE.name());
    }

    private Board getExistedBoard(BoardVO boardVO) {
        List<PieceVO> pieceVOs = pieceDao.findAllByBoardId(boardVO.id());
        Map<Position, Piece> squares = pieceVOs.stream()
                .collect(Collectors.toMap(PieceVO::toPosition, PieceVO::toPiece));
        return boardVO.toBoard(squares);
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
