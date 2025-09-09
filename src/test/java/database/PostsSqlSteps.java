package database;

import config.BaseConfig;
import database.model.PostModelBDRequest;
import database.model.PostModelBDResponse;
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
    private static final String INSERT_POST_SQL = "INSERT INTO wp_posts " +
            "(post_author, post_date, post_date_gmt, post_content, post_title, " +
            "post_excerpt, post_status, comment_status, ping_status, post_name, post_modified, post_modified_gmt, guid, post_type, to_ping, pinged, post_content_filtered) " +
            "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";

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
     * Метод удаления post в БД
     *
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
     *
     * @param id идентификатор поля, которое удаляем
     * @return экзепляр с необходимыми полями
     */
    public PostModelBDResponse getPostModelBDResponse(int id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_POST, ID_FIELD, id));
            if (result.next()) {
                return
                        PostModelBDResponse.builder()
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

    /**
     * Метод создания Post в БД
     *
     * @param postModelBDRequest объект с параметрами для создания
     */
    public long createPostBD(PostModelBDRequest postModelBDRequest) {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_POST_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, postModelBDRequest.getAuthor());
            pstmt.setTimestamp(2, Timestamp.valueOf(postModelBDRequest.getDate()));
            pstmt.setTimestamp(3, Timestamp.valueOf(postModelBDRequest.getDate_gmt()));
            pstmt.setString(4, postModelBDRequest.getContent());
            pstmt.setString(5, postModelBDRequest.getTitle());
            pstmt.setString(6, postModelBDRequest.getExcerpt());
            pstmt.setString(7, postModelBDRequest.getStatus());
            pstmt.setString(8, postModelBDRequest.getComment_status());
            pstmt.setString(9, postModelBDRequest.getPing_status());
            pstmt.setString(10, postModelBDRequest.getSlug());
            pstmt.setTimestamp(11, Timestamp.valueOf(postModelBDRequest.getModified()));
            pstmt.setTimestamp(12, Timestamp.valueOf(postModelBDRequest.getModified_gmt()));
            pstmt.setString(13, postModelBDRequest.getGuid());
            pstmt.setString(14, postModelBDRequest.getType());
            pstmt.setString(15, postModelBDRequest.getTo_ping());
            pstmt.setString(16, postModelBDRequest.getPinged());
            pstmt.setString(17, postModelBDRequest.getPost_content_filtered());

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
}