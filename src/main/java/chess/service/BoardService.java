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

    public List<Long> getBoardRecordsWithTransaction(String teamCode) {
        return transactionManager.performTransaction(() -> getBoardRecords(teamCode));
    }

    private List<Long> getBoardRecords(String teamCode) {
        List<BoardVO> boardVOS = boardDao.findAllByTeamCode(teamCode);
        return boardVOS.stream()
                .filter(boardVO -> !isRunningBoard(boardVO))
                .map(BoardVO::id)
                .toList();
    }

    public Board getBoardRecordWithTransaction(Long boardId) {
        return transactionManager.performTransaction(() -> getBoardRecord(boardId));
    }

    private Board getBoardRecord(Long boardId) {
        BoardVO boardVO = getBoardById(boardId);
        return getExistedBoard(boardVO);
    }

    public Board getRunningBoardWithTransaction(String teamCode) {
        return transactionManager.performTransaction(() -> getRunningBoard(teamCode));
    }

    private Board getRunningBoard(String teamCode) {
        return boardDao.findLastByTeamCode(teamCode)
                .filter(this::isRunningBoard)
                .map(this::getExistedBoard)
                .orElseGet(() -> createNewBoard(teamCode));
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

    private Board createNewBoard(String teamCode) {
        Board board = new InitialBoardGenerator().create();
        BoardVO boardVO = BoardVO.of(board, teamCode);
        Long boardId = boardDao.create(boardVO);

        Map<Position, Piece> squares = board.getSquares();
        squares.forEach((position, piece) -> {
            PieceVO pieceVO = PieceVO.of(boardId, position, piece);
            pieceDao.create(pieceVO);
        });
        return board;
    }

    public void updatePieceAndTurnWithTransaction(Board board, Movement movement, String teamCode) {
        transactionManager.performTransaction(() -> updatePieceAndTurn(board, movement, teamCode));
    }

    private void updatePieceAndTurn(Board board, Movement movement, String teamCode) {
        BoardVO boardVO = getBoardByTeamCode(teamCode);
        Position source = movement.getSource();
        Position destination = movement.getDestination();

        PieceVO sourcePieceVO = getPiece(boardVO, source);
        PieceVO destinationPieceVO = getPiece(boardVO, destination);

        pieceDao.updateTypeAndColor(destinationPieceVO.id(), sourcePieceVO.type(), sourcePieceVO.color());
        pieceDao.updateTypeAndColor(sourcePieceVO.id(), Type.NONE.name(), Color.NONE.name());
        boardDao.updateCurrentColor(boardVO.id(), board.getCurrentColor().name());
    }

    public void updateWinnerWithTransaction(Color winnerColor, String teamCode) {
        transactionManager.performTransaction(() -> updateWinner(winnerColor, teamCode));
    }

    private void updateWinner(Color winnerColor, String teamCode) {
        BoardVO boardVO = getBoardByTeamCode(teamCode);
        boardDao.updateWinnerColor(boardVO.id(), winnerColor.name());
    }

    private BoardVO getBoardById(Long boardId) {
        return boardDao.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("게임을 찾을 수 없습니다."));
    }

    private BoardVO getBoardByTeamCode(String teamCode) {
        return boardDao.findLastByTeamCode(teamCode)
                .orElseThrow(() -> new IllegalStateException("게임을 찾을 수 없습니다."));
    }

    private PieceVO getPiece(BoardVO boardVO, Position position) {
        return pieceDao.findByBoardIdAndFileAndRank(boardVO.id(), position.getFile(), position.getRank())
                .orElseThrow(() -> new IllegalStateException("말을 찾을 수 없습니다."));
    }
}
