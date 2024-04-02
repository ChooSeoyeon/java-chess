package chess.view;

import chess.dto.BoardDTO;
import chess.model.piece.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    public void printGameIntro() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 기록 조회 : record");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 말 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println("> 상태 확인 : status");
        System.out.println("> 게임 종료 : end");
        System.out.println("> ");
    }

    public void printBoardRecords(List<Long> boardIds) {
        System.out.println("완료한 게임 수: " + boardIds.size());
        System.out.println("완료한 게임 id 목록: ");
        boardIds.forEach(System.out::println);
    }

    public void printBoard(BoardDTO boardDTO) {
        String board = PieceRepresentation.mappingBoard(boardDTO).stream()
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(board);
    }

    public void printBoardStatus(Map<Color, Double> boardStatus) {
        printScore(boardStatus);
        printCurrentWinner(boardStatus);
    }

    private void printScore(Map<Color, Double> boardStatus) {
        boardStatus.forEach((color, score) -> {
            System.out.println(ColorRepresentation.mappingColor(color) + " : " + score);
        });
    }

    private void printCurrentWinner(Map<Color, Double> boardStatus) {
        double whiteScore = boardStatus.get(Color.WHITE);
        double blackScore = boardStatus.get(Color.BLACK);
        if (whiteScore > blackScore) {
            System.out.println(ColorRepresentation.mappingColor(Color.WHITE) + "이 이기고 있습니다.");
            return;
        }
        if (whiteScore < blackScore) {
            System.out.println(ColorRepresentation.mappingColor(Color.BLACK) + "이 이기고 있습니다.");
            return;
        }
        System.out.println("비기고 있습니다.");
    }

    public void printWinner(Color winner) {
        System.out.println(ColorRepresentation.mappingColor(winner) + "이 승리하였습니다.");
    }

    public void printException(String message) {
        System.out.println("[ERROR] " + message);
    }
}
