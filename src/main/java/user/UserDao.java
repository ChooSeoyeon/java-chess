package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess-temp"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        try (final var connection = getConnection()) { // autoClosable -> try-with-resources
            final var statement = connection.prepareStatement("SELECT * FROM user");
            final var resultSet = statement.executeQuery();

            final var users = new ArrayList<User>();
            while (resultSet.next()) {
                var userId = resultSet.getString("user_id");
                var name = resultSet.getString("name");

                users.add(new User(userId, name));
            }
            return users;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(String userId) {
        try (final var connection = getConnection()) {
            final var statement = connection.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            statement.setString(1, userId);
            final var resultSet = statement.executeQuery();

            // statement.excuteUpdate(); -> update

            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(); // 예외 처리 고민해보기 -> catch 안던져도 되긴함. 어떤 레벨로 추상화할지 고민해보기
        }
        return null;
    }

    public void save(User user) {
        try (final var connection = getConnection()) {
            final var statement = connection.prepareStatement("INSERT INTO user (user_id, name) VALUES (?, ?)");
            statement.setString(1, user.userId());
            statement.setString(2, user.name());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        try (final var connection = getConnection()) {
            final var statement = connection.prepareStatement("UPDATE user SET name = ? WHERE user_id = ?");
            statement.setString(1, user.name());
            statement.setString(2, user.userId());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(String userId) {
        try (final var connection = getConnection()) {
            final var statement = connection.prepareStatement("DELETE FROM user WHERE user_id = ?");
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
