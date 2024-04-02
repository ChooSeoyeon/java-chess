package study;

import chess.view.Command;
import org.junit.jupiter.api.Test;

public class EqualsTest {
    /*
     npe가 안터지게 하기 위해 타입체크와 npe 체크를 하는 equals 대신 == 을 쓰는 게 좋은 생각일까?

     반은 맞고 반은 틀림. 애초에 equals를 npe 체크를 하는 용도로 쓰는게 그 용법에 맞지 않기 때문임
     npe 검증은 다른 곳에서 되어 있단 전제가 들어가야 함
     따라서, equals를 쓸 이유가 딱히 없음

     속도는 둘이 비슷함

     equals는 내부적으로 ==을 쓰기에 굳이 상수 비교에서 쓸 필요가 없는 거 같음
    */
    @Test
    void enum에_대해_equals와_등호의_속도를_비교한다() {
        Command command1 = Command.MOVE;
        Command command2 = Command.RECORD;

        long startTime = System.nanoTime();
        for (int i = 0; i < 100_000_000; i++) {
            for (int j = 0; j < 100_000_000; j++) {
                boolean result = (command1 == command2);
            }
        }
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);

        startTime = System.nanoTime();
        for (int i = 0; i < 100_000_000; i++) {
            for (int j = 0; j < 100_000_000; j++) {
                boolean result = command1.equals(command2);
            }
        }
        endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }
}
