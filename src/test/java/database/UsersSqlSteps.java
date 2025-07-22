package database;

import config.BaseConfig;
import database.model.UserModelBD;
import database.model.UserModelBDRequest;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Метод для взаимодействия с БД для сущности User
 */
public class UsersSqlSteps {

    /**
     * Константы полей из БД
     */
    private static final String ID_FIELD = "ID";
    private static final String LOGIN_FIELD = "user_login";
    private static final String PASS_FIELD = "user_login";
    private static final String NICENAME_FIELD = "user_nicename"; //slug
    private static final String EMAIL_FIELD = "user_email";
    private static final String NICKNAME_FIELD = "nickname";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DATE_FIELD = "user_registered";
    private static final String DISPLAY_NAME_FIELD = "display_name"; //name
    private static final String URL_FIELD = "user_url";

    /**
     * Константы с запросами в БД
     */
    private static final String INSERT_USER_SQL = "INSERT INTO wp_users " +
            "(user_login, user_pass, user_nicename, user_email, user_url, " +
            "user_registered, user_activation_key, user_status, display_name) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; //отправка в бд
    private static final String INSERT_USERMETA_SQL = "INSERT INTO wp_usermeta " +
            "(user_id, meta_key, meta_value) " +
            "VALUES (?, ?, ?)";
    private static final String DELETE_SQL_REQUEST_USER = "DELETE FROM wp_users WHERE %s = %s";
    private static final String SELECT_USER_SQL = "SELECT u.ID, u.user_nicename, u.user_login, " +
            "u.user_email, u.user_url, u.user_registered, u.user_status, u.display_name, " +
            "(SELECT um1.meta_value FROM wp_usermeta um1 WHERE um1.user_id = u.ID AND um1.meta_key = 'nickname' ORDER BY um1.umeta_id DESC LIMIT 1) " +
            "AS nickname, MAX(CASE WHEN um.meta_key = 'first_name' THEN um.meta_value END) AS first_name, MAX(CASE WHEN um.meta_key = 'last_name' " +
            "THEN um.meta_value END) AS last_name, MAX(CASE WHEN um.meta_key = 'description' THEN um.meta_value END) AS description " +
            "FROM wp_users u LEFT JOIN wp_usermeta um ON u.ID = um.user_id WHERE u.%s = %s GROUP BY u.ID, u.user_login, u.user_email, u.user_registered, u.user_status, u.display_name";

    /**
     * Экземпляр конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Метод открытия подключения к базе данных
     *
     * @return экземпляр подключения
     */
    @SneakyThrows
    public static Connection getConnection() {
        Class.forName(config.driverDb());
        return DriverManager.getConnection(config.urlDb(), config.userDb(), config.passwordDb());
    }

    /**
     * Метод запроса в БД для получения данных user
     *
     * @param id идентификатор поля, которое удаляем
     * @return экзепляр с необходимыми полями
     */
    public UserModelBD getUsersModelBD(int id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_USER_SQL, ID_FIELD, id));
            if (result.next()) {
                return
                        UserModelBD.builder()
                                .id(result.getString(ID_FIELD))
                                .username(result.getString(LOGIN_FIELD))
                                .slug(result.getString(NICENAME_FIELD))
                                .email(result.getString(EMAIL_FIELD))
                                .nickname(result.getString(NICKNAME_FIELD))
                                .first_name(result.getString(FIRST_NAME_FIELD))
                                .last_name(result.getString(LAST_NAME_FIELD))
                                .description(result.getString(DESCRIPTION_FIELD))
                                .registered_date(result.getObject(DATE_FIELD, LocalDateTime.class))
                                .name(result.getString(DISPLAY_NAME_FIELD))
                                .url(result.getString(URL_FIELD))
                                .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Создание user в транзакции
     */
    public long createUserDoubleTable(UserModelBDRequest userRequest) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            long userId = createUserBD(connection, userRequest);
            createUserBDMeta(connection, userId, userRequest);
            connection.commit();

            return userId;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создания user в БД
     *
     * @param userModelBDRequest объект с параметрами для создания
     */
    private long createUserBD(Connection connection, UserModelBDRequest userModelBDRequest) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, userModelBDRequest.getUser_login());
            pstmt.setString(2, userModelBDRequest.getUser_pass());
            pstmt.setString(3, userModelBDRequest.getUser_nicename());
            pstmt.setString(4, userModelBDRequest.getUser_email());
            pstmt.setString(5, userModelBDRequest.getUser_url());
            pstmt.setTimestamp(6, Timestamp.valueOf(userModelBDRequest.getUser_registered()));
            pstmt.setString(7, userModelBDRequest.getUser_activation_key());
            pstmt.setString(8, userModelBDRequest.getUser_status());
            pstmt.setString(9, userModelBDRequest.getDisplay_name());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException();
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createUserBDMeta(Connection connection, long userId, UserModelBDRequest userRequest) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_USERMETA_SQL)) {
            if (userRequest.getFirst_name() != null) {
                pstmt.setLong(1, userId);
                pstmt.setString(2, "first_name");
                pstmt.setString(3, userRequest.getFirst_name());
                pstmt.addBatch();
            }
            if (userRequest.getLast_name() != null) {
                pstmt.setLong(1, userId);
                pstmt.setString(2, "last_name");
                pstmt.setString(3, userRequest.getLast_name());
                pstmt.addBatch();
            }
            if (userRequest.getDescription() != null) {
                pstmt.setLong(1, userId);
                pstmt.setString(2, "description");
                pstmt.setString(3, userRequest.getDescription());
                pstmt.addBatch();
            }
            if (userRequest.getNickname() != null) {
                pstmt.setLong(1, userId);
                pstmt.setString(2, "nickname");
                pstmt.setString(3, userRequest.getNickname());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод удаления user в БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public void deleteUser(String id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_USER, ID_FIELD, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}