package database;

import config.BaseConfig;
import database.model.PostModelBD;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Метод для взаимодействия с БД для сущности Post
 */
public class PostsSqlSteps {

    /**
     * Константы полей из БД
     */
    private static final String ID_FIELD = "ID"; //id
    private static final String POST_AUTHOR_FIELD = "post_author"; //author
    private static final String POST_DATE_FIELD = "post_date"; //date
    private static final String POST_DATE_GMT_FIELD = "post_date_gmt"; //date_gmt
    private static final String POST_CONTENT_FIELD = "post_content"; //content raw
    private static final String POST_TITLE_FIELD = "post_title"; //title raw
    private static final String POST_EXCEPT_FIELD = "post_excerpt"; //excerpt raw
    private static final String POST_STATUS_FIELD = "post_status"; //status
    private static final String COMMENT_STATUS_FIELD = "comment_status"; //comment_status
    private static final String PING_STATUS_FIELD = "ping_status"; //ping_status
    private static final String POST_PASSWORD_FIELD = "post_password"; //password
    private static final String POST_NAME_FIELD = "post_name"; //slug
    private static final String POST_MODIFIED_FIELD = "post_modified"; //modified
    private static final String POST_MODIFIED_GMT_FIELD = "post_modified_gmt"; //modified_gmt
    private static final String GUID_FIELD = "guid"; //guid raw
    private static final String POST_TYPE_FIELD = "post_type"; //type

    /**
     * Константы с запросами в БД
     */
    private static final String DELETE_SQL_REQUEST_POST = "DELETE FROM wp_posts WHERE %s = %s";
    private static final String SELECT_BY_ID_SQL_REQUEST_POST = "SELECT * FROM wp_posts WHERE %s = %d";

    /**
     * Экземпляр конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Метод открытия подключения к базе данных
     * @return экземпляр подключения
     */
    @SneakyThrows
    public static Connection getConnection() {
        Class.forName(config.driverDb());
        return DriverManager.getConnection(config.urlDb(), config.userDb(), config.passwordDb());
    }

    /**
     * Метод удаления post в БД
     * @param id идентификатор поля, которое удаляем
     */
    public void deletePost(String id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_POST, ID_FIELD, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса в БД для получения данных post
     * @param id идентификатор поля, которое удаляем
     * @return экзепляр с необходимыми полями
     */
    public PostModelBD getPostModelBD(int id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_POST, ID_FIELD, id));
            if (result.next()) {
                return
                        PostModelBD.builder()
                        .id(Integer.valueOf(result.getString(ID_FIELD)))
                        .author(Integer.valueOf(result.getString(POST_AUTHOR_FIELD)))
                        .date(result.getObject(POST_DATE_FIELD, LocalDateTime.class))
                        .date_gmt(result.getObject(POST_DATE_GMT_FIELD, LocalDateTime.class))
                        .content(result.getString(POST_CONTENT_FIELD))
                        .title(result.getString(POST_TITLE_FIELD))
                        .excerpt(result.getString(POST_EXCEPT_FIELD))
                        .status(result.getString(POST_STATUS_FIELD))
                        .comment_status(result.getString(COMMENT_STATUS_FIELD))
                        .ping_status(result.getString(PING_STATUS_FIELD))
                        .slug(result.getString(POST_NAME_FIELD))
                        .modified(result.getObject(POST_MODIFIED_FIELD, LocalDateTime.class))
                        .modified_gmt(result.getObject(POST_MODIFIED_GMT_FIELD, LocalDateTime.class))
                        .guid(result.getString(GUID_FIELD))
                        .type(result.getString(POST_TYPE_FIELD))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}