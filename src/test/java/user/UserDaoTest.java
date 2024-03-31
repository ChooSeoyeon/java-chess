package user;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoTest {
    private final UserDao userDao = new UserDao();

    @BeforeEach
    void setup() {
        try (final var connection = userDao.getConnection()) {
            final var statement = connection.prepareStatement("TRUNCATE TABLE user");
            statement.executeUpdate();
            userDao.save(new User("prettydora", "dora"));
            userDao.save(new User("prettyneo", "neo"));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getConnection() {
        final var connection = userDao.getConnection();

        assertThat(connection).isNotNull();
    }

    @Test
    public void findAll() {
        final var users = userDao.findAll();

        assertThat(users).contains(
                new User("prettydora", "dora"),
                new User("prettyneo", "neo")
        );
    }

    @Test
    void findById() {
        final var user = userDao.findById("prettydora").get();

        assertThat(user).isEqualTo(new User("prettydora", "dora"));
    }

    @Test
    void save() {
        userDao.save(new User("prettypotato", "potato"));
        User user = userDao.findById("prettypotato").get();

        assertThat(user).isEqualTo(new User("prettypotato", "potato"));
    }

    @Test
    void update() {
        userDao.update(new User("prettydora", "dora2"));
        User user = userDao.findById("prettydora").get();

        assertThat(user).isEqualTo(new User("prettydora", "dora2"));
    }

    @Test
    void delete() {
        userDao.deleteById("prettydora");
        Optional<User> user = userDao.findById("prettydora");

        assertThat(user).isNull();
    }
}
