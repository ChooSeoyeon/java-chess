package chess.database;

import java.sql.Connection;
import java.util.function.Supplier;

public class TransactionManager {
    private final Connection connection;

    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public void performTransaction(Runnable operation) {
        try {
            begin();
            operation.run();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    public <T> T performTransaction(Supplier<T> operation) {
        try {
            begin();
            T result = operation.get();
            commit();
            return result;
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new DatabaseAccessException("트랜잭션 시작에 실패했습니다.", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (Exception e) {
            throw new DatabaseAccessException("트랜잭션 커밋에 실패했습니다.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new DatabaseAccessException("트랜잭션 롤백에 실패했습니다.", e);
        }
    }
}
