package chess.view;

import chess.dto.PositionDTO;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int TEAM_CODE_MIN_LENGTH = 2;
    private static final int TEAM_CODE_MAX_LENGTH = 5;
    private static final int POSITION_LENGTH = 2;
    private static final int FILE_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final int FILE_START_ASCII = 'a' - 1;
    private static final int RANK_START_ASCII = '1' - 1;

    public Command askStartOrRecordCommand() {
        System.out.print("> 명령어를 입력하세요: ");
        String input = SCANNER.next();
        Command command = Command.findBy(input);
        validateStartOrRecordCommand(command);
        return command;
    }

    private void validateStartOrRecordCommand(Command command) {
        if (command != Command.START && command != Command.RECORD) {
            throw new IllegalArgumentException("게임을 시작하지 않은 상태에선 start를 혹은 record를 입력해야 합니다.");
        }
    }

    public String askTeamCode() {
        System.out.print("> 팀 코드를 입력하세요: ");
        String teamCode = SCANNER.next();
        validateTeamCode(teamCode);
        return teamCode;
    }

    private void validateTeamCode(String teamCode) {
        if (teamCode.length() < TEAM_CODE_MIN_LENGTH || teamCode.length() > TEAM_CODE_MAX_LENGTH) {
            throw new IllegalArgumentException("팀 코드는 2글자 이상 5글자 이하로 입력해야 합니다.");
        }
    }

    public Command askMoveOrStatusOrEndCommand() {
        System.out.print("> 명령어를 입력하세요: ");
        String input = SCANNER.next();
        Command command = Command.findBy(input);
        validateMoveOrStatusOrEndCommand(command);
        return command;
    }

    private void validateMoveOrStatusOrEndCommand(Command command) {
        if (command != Command.MOVE && command != Command.STATUS && command != Command.END) {
            throw new IllegalArgumentException("게임을 시작한 상태에선 move 혹은 status 혹은 end를 입력해야 합니다.");
        }
    }

    public PositionDTO askPosition() {
        String input = SCANNER.next();
        validatePositionLength(input);
        return convertToPosition(input);
    }

    private void validatePositionLength(String input) {
        if (input.length() != POSITION_LENGTH) {
            throw new IllegalArgumentException("이동할 위치는 b2 꼴로 입력해야 합니다.");
        }
    }

    private PositionDTO convertToPosition(String input) {
        int file = input.charAt(FILE_INDEX) - FILE_START_ASCII;
        int rank = input.charAt(RANK_INDEX) - RANK_START_ASCII;
        return new PositionDTO(file, rank);
    }
}
