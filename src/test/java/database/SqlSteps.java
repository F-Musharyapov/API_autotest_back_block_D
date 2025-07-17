package database;

import com.fasterxml.jackson.annotation.JsonProperty;
import database.model.UserModelBD;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Метод для взаимодействия с БД
 */
@AllArgsConstructor
public class SqlSteps {

    /**
     * Константы полей из БД
     */
    private static final String ID_FIELD = "ID";
    private static final String LOGIN_FIELD = "user_login";
    private static final String NICENAME_FIELD = "user_nicename"; //slug
    private static final String EMAIL_FIELD = "user_email";
    private static final String NICKNAME_FIELD = "nickname";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DATE_FIELD = "user_registered";
    private static final String DISPLAY_NAME_FIELD = "display_name";
    private static final String URL_FIELD = "user_url";


    /**
     * Константы с запросами в БД
     */
    private static final String DELETE_SQL_REQUEST = "DELETE FROM wp_users WHERE %s = %s";
    //private static final String SELECT_BY_ID_SQL_REQUEST = "SELECT * FROM wp_users WHERE %s = %d";
    private static final String SELECT_BY_ID_SQL_REQUEST = "SELECT u.ID, u.user_nicename, u.user_login, u.user_email, u.user_url, u.user_registered, u.user_status, u.display_name, MAX(CASE WHEN um.meta_key = 'nickname' THEN um.meta_value END) AS nickname, MAX(CASE WHEN um.meta_key = 'first_name' THEN um.meta_value END) AS first_name, MAX(CASE WHEN um.meta_key = 'last_name' THEN um.meta_value END) AS last_name, MAX(CASE WHEN um.meta_key = 'description' THEN um.meta_value END) AS description, MAX(CASE WHEN um.meta_key = 'rich_editing' THEN um.meta_value END) AS rich_editing, MAX(CASE WHEN um.meta_key = 'wp_capabilities' THEN um.meta_value END) AS capabilities, MAX(CASE WHEN um.meta_key = 'wp_user_level' THEN um.meta_value END) AS user_level FROM wp_users u LEFT JOIN wp_usermeta um ON u.ID = um.user_id WHERE u.%s = %s GROUP BY u.ID, u.user_login, u.user_email, u.user_registered, u.user_status, u.display_name";


    /**
     * Переменная с подключением к БД
     */
    private final Connection connection;

    /**
     * Метод удаления user в БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public void deleteUser(String id) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST, ID_FIELD, id)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса в БД для получения данных user
     *
     * @param id идентификатор поля, которое удаляем
     */
    public UserModelBD getUsersModelBD(int id) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST, ID_FIELD, id));
            if (result.next()) {
                UserModelBD userBD = UserModelBD.builder()
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
                        //id(result.getInt(ID_FIELD))
                        //                        .user_login(result.getString(LOGIN_FIELD))
                        //                        .user_nicename(result.getString(NICENAME_FIELD))
                        //                        .user_email(result.getString(EMAIL_FIELD))
                        //                        .nickname(result.getString(NICKNAME_FIELD))
                        //                        .first_name(result.getString(FIRST_NAME_FIELD))
                        //                        .last_name(result.getString(LAST_NAME_FIELD))
                        //                        .description(result.getString(DESCRIPTION_FIELD))
                        //                        .user_registered(result.getObject(DATE_FIELD, LocalDateTime.class))
                        //                        .display_name(result.getString(DISPLAY_NAME_FIELD))
                        //                        .user_url(result.getString(URL_FIELD))
                        .build();
                return userBD;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
        /*
         private final int id;//
    private final String user_login; //username
    private final String user_nicename; //slug
    private final String user_email;//
    private final String nickname;//
    private final String first_name;//
    private final String last_name;//
    private final String description;//
    private final String user_registered;//
    private final String display_name; //name
    private final String user_url;//
         */