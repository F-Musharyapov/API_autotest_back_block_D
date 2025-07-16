package database;

import database.model.UserModelBD;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


@AllArgsConstructor
public class SqlSteps {

    private static final String ID_FIELD = "ID";
    private static final String EMAIL_FIELD = "user_email";
    private static final String LOGIN_FIELD = "user_login";


    private static final String DELETE_SQL_REQUEST = "DELETE FROM wp_users WHERE %s = %s";
    private static final String SELECT_BY_ID_SQL_REQUEST = "SELECT * FROM wp_users WHERE %s = %d";

    /**
     * Переменная с подключением к БД
     */
    private final Connection connection;


    /**
     * Метод удаления в БД
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
     * Метод запроса в БД для получения данных юзера
     *
     * @param id идентификатор поля, которое удаляем
     */
    public UserModelBD getUsersModelBD(int id) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST, ID_FIELD, id));
            if (result.next()) {

                UserModelBD userBD = UserModelBD.builder()
                        .id(result.getInt(ID_FIELD))
                        .user_login(result.getString(LOGIN_FIELD))
                        .user_email(result.getString(EMAIL_FIELD))
                        .build();

                System.out.println("User from database:");
                System.out.println("ID: " + userBD.getId());
                System.out.println("Login: " + userBD.getUser_login());
                System.out.println("Email: " + userBD.getUser_email());

                return userBD;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
