package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.model.board.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {
    @ParameterizedTest
    @MethodSource("providePieceWithSignature")
    void 기물_타입과_색깔에_맞는_시그니처를_반환한다(Piece piece, String signature) {
        assertThat(piece.getSignature()).isEqualTo(signature);
    }

    private static Stream<Arguments> providePieceWithSignature() {
        return Stream.of(
                Arguments.of(new BishopPiece(Color.BLACK), "B"),
                Arguments.of(new RookPiece(Color.BLACK), "R"),
                Arguments.of(new QueenPiece(Color.BLACK), "Q"),
                Arguments.of(new KnightPiece(Color.BLACK), "N"),
                Arguments.of(new BlackPawnPiece(), "P"),
                Arguments.of(new KingPiece(Color.BLACK), "K"),
                Arguments.of(new BishopPiece(Color.WHITE), "b"),
                Arguments.of(new RookPiece(Color.WHITE), "r"),
                Arguments.of(new QueenPiece(Color.WHITE), "q"),
                Arguments.of(new KnightPiece(Color.WHITE), "n"),
                Arguments.of(new WhitePawnPiece(), "p"),
                Arguments.of(new KingPiece(Color.WHITE), "k")
        );
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,2", "2,2,3,1", "2,2,1,3", "4,5,5,4"})
    void 비숍은_대각선으로_원하는_만큼_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BishopPiece(Color.BLACK).canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,3", "1,1,3,2", "2,2,1,2", "4,5,5,5"})
    void 비숏은_대각선이_아닌_방향으로_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BishopPiece(Color.BLACK).canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,2", "1,1,1,3", "2,2,2,3", "4,5,4,6"})
    void 록은_직선으로_원하는_만큼_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new RookPiece(Color.BLACK).canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,2", "1,1,3,3", "2,2,3,3", "4,5,5,6"})
    void 록은_직선이_아닌_방향으로_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new RookPiece(Color.BLACK).canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,2", "1,1,3,3", "2,2,3,3", "4,5,5,6"})
    void 퀸은_직선과_대각선으로_원하는_만큼_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new QueenPiece(Color.BLACK).canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,3", "1,1,3,2", "2,2,1,4", "4,5,3,3"})
    void 나이트는_옆으로_한칸_앞뒤로_두칸_움직일_수_있다(
            int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new KnightPiece(Color.BLACK).canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,2", "1,1,1,3", "2,2,2,3", "4,5,4,6"})
    void 나이트는_옆으로_한칸_앞뒤로_두칸_꼴이_아니게_움직일_수_없다(
            int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new KnightPiece(Color.BLACK).canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,2", "1,2,1,3", "2,3,2,4", "6,7,6,8"})
    void 화이트_폰은_한_칸_앞으로_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new WhitePawnPiece().canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,2,1", "2,2,1,1", "2,3,2,2", "6,7,6,6"})
    void 화이트_폰은_옆이나_뒤로는_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new WhitePawnPiece().canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,2,1,4", "2,2,2,4", "5,2,5,4", "8,2,8,4"})
    void 화이트_폰은_시작_위치에서_두_칸_앞으로_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new WhitePawnPiece().canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"3,3,3,5", "3,4,3,6", "4,3,4,5", "7,3,7,5"})
    void 화이트_폰은_시작_위치가_아닐_땐_두_칸_앞으로_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new WhitePawnPiece().canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"2,4,2,3", "2,5,2,4", "7,8,7,7", "7,7,7,6"})
    void 블랙_폰은_한_칸_앞으로_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BlackPawnPiece().canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,1,1,2", "1,1,1,2", "2,2,2,3", "6,5,6,6"})
    void 블랙_폰은_옆이나_뒤로는_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BlackPawnPiece().canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,7,1,5", "2,7,2,5", "5,7,5,5", "8,7,8,5"})
    void 블랙_폰은_시작_위치에서_두_칸_앞으로_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BlackPawnPiece().canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1,5,1,3", "2,6,2,4", "5,5,5,3", "8,3,8,1"})
    void 블랙_폰은_시작_위치가_아닐_땐_두_칸_앞으로_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new BlackPawnPiece().canMove(source, destination)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"2,2,1,2", "2,2,2,1", "2,2,3,3", "2,2,3,1"})
    void 킹은_어느_방향이로든_한_칸_움직일_수_있다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new KingPiece(Color.BLACK).canMove(source, destination)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"2,2,1,4", "2,2,2,4", "2,2,3,4", "2,2,4,1"})
    void 킹은_한_칸_이상_움직일_수_없다(int sourceFile, int sourceRank, int destinationFile, int destinationRank) {
        Position source = Position.from(sourceFile, sourceRank);
        Position destination = Position.from(destinationFile, destinationRank);
        assertThat(new KingPiece(Color.BLACK).canMove(source, destination)).isFalse();
    }
}
