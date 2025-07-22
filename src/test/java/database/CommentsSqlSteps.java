package database;

import config.BaseConfig;
import database.model.CommentModelBD;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Метод для взаимодействия с БД для сущности Comment
 */
public class CommentsSqlSteps {

    /**
     * Константы полей из БД
     */
    private static final String COMMENT_ID_FIELD = "comment_ID"; //id
    private static final String COMMENT_POST_ID_FIELD = "comment_post_ID"; //post
    private static final String COMMENT_AUTHOR_FIELD = "comment_author"; //author_name
    private static final String COMMENT_AUTHOR_EMAIL_FIELD = "comment_author_email"; //author_email
    private static final String COMMENT_AUTHOR_URL_FIELD = "comment_author_url"; //author_url
    private static final String COMMENT_AUTHOR_IP_FIELD = "comment_author_IP"; //author_ip
    private static final String COMMENT_DATE_FIELD = "comment_date"; //date
    private static final String COMMENT_DATE_GMT_FIELD = "comment_date_gmt"; //date_gmt
    private static final String COMMENT_CONTENT_FIELD = "comment_content"; //content
    private static final String COMMENT_APPROVED_FIELD = "comment_approved"; //status
    private static final String COMMENT_AGENT_FIELD = "comment_agent"; //author_user_agent
    private static final String COMMENT_TYPE_FIELD = "comment_type"; //type
    private static final String USER_ID_FIELD = "user_id"; //author

    /**
     * Константы с запросами в БД
     */
    private static final String DELETE_SQL_REQUEST_COMMENT = "DELETE FROM wp_comments WHERE %s = %s";
    private static final String SELECT_BY_ID_SQL_REQUEST_COMMENT = "SELECT * FROM wp_comments WHERE %s = %d";
    private static final String INSERT_COMMENT_SQL = "INSERT INTO wp_comments " +
            "(comment_post_ID, comment_author, comment_author_email, comment_author_url, comment_author_IP, " +
            "comment_date, comment_date_gmt, comment_content, comment_approved, comment_agent, comment_type, user_id) " +
            "SELECT ?, u.user_login, u.user_email, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
            "FROM wp_users u WHERE u.ID = ?";

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
     * Метод удаления comment в БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public static void deleteComment(String id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_COMMENT, COMMENT_ID_FIELD, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса в БД для получения данных comment
     *
     * @param id идентификатор поля, которое удаляем
     * @return экзепляр с необходимыми полями
     */
    public CommentModelBD getCommentsModelBD(int id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_COMMENT, COMMENT_ID_FIELD, id));
            if (result.next()) {
                return
                        CommentModelBD.builder()
                                .id(Integer.valueOf(result.getString(COMMENT_ID_FIELD)))
                                .post(Integer.valueOf(result.getString(COMMENT_POST_ID_FIELD)))
                                .author(Integer.valueOf(result.getString(USER_ID_FIELD)))
                                .author_name(result.getString(COMMENT_AUTHOR_FIELD))
                                .author_email(result.getString(COMMENT_AUTHOR_EMAIL_FIELD))
                                .author_url(result.getString(COMMENT_AUTHOR_URL_FIELD))
                                .author_ip(result.getString(COMMENT_AUTHOR_IP_FIELD))
                                .author_user_agent(result.getString(COMMENT_AGENT_FIELD))
                                .date(result.getObject(COMMENT_DATE_FIELD, LocalDateTime.class))
                                .date_gmt(result.getObject(COMMENT_DATE_GMT_FIELD, LocalDateTime.class))
                                .content(result.getString(COMMENT_CONTENT_FIELD))
                                .status(result.getString(COMMENT_APPROVED_FIELD))
                                .type(result.getString(COMMENT_TYPE_FIELD))
                                .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Метод создания comment в БД
     *
     * @param commentModelBD объект с параметрами для создания
     */
    public long createCommentBD(CommentModelBD commentModelBD) {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_COMMENT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, commentModelBD.getPost());
            pstmt.setString(2, commentModelBD.getAuthor_url());
            pstmt.setString(3, commentModelBD.getAuthor_ip());
            pstmt.setTimestamp(4, Timestamp.valueOf(commentModelBD.getDate()));
            pstmt.setTimestamp(5, Timestamp.valueOf(commentModelBD.getDate_gmt()));
            pstmt.setString(6, commentModelBD.getContent());
            pstmt.setString(7, commentModelBD.getStatus());
            pstmt.setString(8, commentModelBD.getAuthor_user_agent());
            pstmt.setString(9, commentModelBD.getType());
            pstmt.setInt(10, commentModelBD.getAuthor());
            pstmt.setInt(11, commentModelBD.getAuthor());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating comment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);  // Получаем по индексу (первый столбец)
                }
                else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create comment", e);
        }
    }
}