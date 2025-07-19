package database;

import config.BaseConfig;
import database.model.CommentModelBD;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Метод для взаимодействия с БД для сущности Comment
 */
@AllArgsConstructor
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

    /**
     * Экземпляр конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Экземпляр connection
     */
    private final Connection connection;

    /**
     * Метод открытия подключения к базе данных с использованием try-with-resources
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
    public void deleteComment(String id) {
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
                CommentModelBD commentBD = CommentModelBD.builder()
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
                return commentBD;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}