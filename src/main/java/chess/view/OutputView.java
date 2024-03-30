package chess.view;

import chess.dto.BoardDTO;
import chess.model.piece.Color;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    public void printGameIntro() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println("> 게임 상태 : status");
    }

    public void printBoard(BoardDTO boardDTO) {
        String board = PieceRepresentation.mappingBoard(boardDTO).stream()
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(board);
    }

    public void printException(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printBoardStatus(Map<Color, Double> boardStatus) {
        boardStatus.forEach((color, score) -> {
            System.out.println(ColorRepresentation.mappingColor(color) + " : " + score);
        });
    }
}
