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
import chess.model.piece.Type;
import chess.model.position.Movement;
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

    public void updatePieceAndTurn(Board board, Movement movement) {
        transactionManager.performTransaction(() -> updatePieceAndTurnWithTransaction(board, movement));
    }

    private void updatePieceAndTurnWithTransaction(Board board, Movement movement) {
        BoardVO boardVO = boardDao.findLast()
                .orElseThrow(() -> new IllegalStateException("게임을 찾을 수 없습니다."));
        Long boardId = boardVO.id();

        Position source = movement.getSource();
        Position destination = movement.getDestination();
        PieceVO sourcePieceVO = pieceDao.findByBoardIdAndFileAndRank(boardId, source.getFile(), source.getRank())
                .orElseThrow(() -> new IllegalStateException("출발지 말을 찾을 수 없습니다."));
        PieceVO destinationPieceVO = pieceDao.findByBoardIdAndFileAndRank(boardId, destination.getFile(),
                        destination.getRank())
                .orElseThrow(() -> new IllegalStateException("도착지 말을 찾을 수 없습니다."));

        pieceDao.updateTypeAndColor(destinationPieceVO.id(), sourcePieceVO.type(), sourcePieceVO.color());
        pieceDao.updateTypeAndColor(sourcePieceVO.id(), Type.NONE.name(), Color.NONE.name());
        boardDao.updateCurrentColor(boardId, board.getCurrentColor().name());
    }

    public void updateWinner(Color winnerColor) {
        transactionManager.performTransaction(() -> updateWinnerWithTransaction(winnerColor));
    }

    private void updateWinnerWithTransaction(Color winnerColor) {
        BoardVO boardVO = boardDao.findLast()
                .orElseThrow(() -> new IllegalStateException("게임을 찾을 수 없습니다."));
        boardDao.updateWinnerColor(boardVO.id(), winnerColor.name());
    }
}
