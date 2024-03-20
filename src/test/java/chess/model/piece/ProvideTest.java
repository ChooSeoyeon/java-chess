package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProvideTest {
    @ParameterizedTest
    @MethodSource("providePieceWithSignature")
    void 기물_타입과_색깔에_맞는_시그니처를_반환한다(Piece piece, String signature) {
        assertThat(piece.getSignature()).isEqualTo(signature);
    }

    private static Stream<Arguments> providePieceWithSignature() {
        return Stream.of(
                Arguments.of(new Piece(Color.BLACK, Type.BISHOP), "B"),
                Arguments.of(new Piece(Color.BLACK, Type.ROOK), "R"),
                Arguments.of(new Piece(Color.BLACK, Type.QUEEN), "Q"),
                Arguments.of(new Piece(Color.BLACK, Type.KNIGHT), "N"),
                Arguments.of(new Piece(Color.BLACK, Type.PAWN), "P"),
                Arguments.of(new Piece(Color.BLACK, Type.KING), "K"),
                Arguments.of(new Piece(Color.WHITE, Type.BISHOP), "b"),
                Arguments.of(new Piece(Color.WHITE, Type.ROOK), "r"),
                Arguments.of(new Piece(Color.WHITE, Type.QUEEN), "q"),
                Arguments.of(new Piece(Color.WHITE, Type.KNIGHT), "n"),
                Arguments.of(new Piece(Color.WHITE, Type.PAWN), "p"),
                Arguments.of(new Piece(Color.WHITE, Type.KING), "k")
        );
    }
}
