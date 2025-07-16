package tests;

import database.SqlConnector;
import database.SqlSteps;
import database.model.UserModelBD;
import helpers.BaseRequests;
import io.qameta.allure.Description;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.UsersCreateRequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс тестирования POST-запроса
 */
public class CreateUserTest {

    /**
     * Экземпляр спецификации RestAssured
     */
    private RequestSpecification requestSpecification;

    /**
     * Экземпляр класса для работы с БД
     */
    private SqlSteps sqlSteps;

    /**
     * Экземпляр класса для подключения к БД
     */
    private SqlConnector sqlConnector;

    /**
     * Экземпляр интерфейса для соединения с mySQL
     */
    private Connection connection;

    /**
     * Переменная для хранения объекта pojo при post запросе
     */
    private UsersCreateRequest usersCreateRequest;

    /**
     * Переменная для хранения user ID
     */
    private String userID;

    /**
     * Метод инициализации спецификации запроса
     *
     * @throws IOException если не удается инициализировать спецификацию запроса
     */
    @BeforeEach
    public void setup() throws IOException, SQLException {
        requestSpecification = BaseRequests.initRequestSpecification();
        sqlConnector = new SqlConnector();
        connection = sqlConnector.openConnection();
        sqlSteps = new SqlSteps(connection);
    }

    @Test
    @Description("Тестовый метод для создания юзера и сравнения отправленных данных с полученными")
    public void createUserTest() {

        usersCreateRequest = UsersCreateRequest.builder()
                .username(getRandomUserName())
                .email(getRandomEmail())
                .password(getRandomPassword())
                .build();

        userID = given()
                .spec(requestSpecification)
                .body(usersCreateRequest)
                .when()
                .post(REQUEST_USER_CREATE_POST)
                .then()
                .statusCode(STATUS_CODE_CREATED)
                .extract()
                .path("id").toString();

        // Проверка данных в БД
        UserModelBD userBD = null;
        try {
            userBD = sqlSteps.getUsersModelBD(Integer.parseInt(userID));
            // посмотреть перед сравнением
            System.out.println("Ожидаемый LOGIN: " + usersCreateRequest.getUsername());
            System.out.println("Фактический LOGIN из БД: " + userBD.getUser_login());

            // Проверяем соответствие данных
            assertEquals(userBD.getUser_login(), usersCreateRequest.getUsername(),
                    "LOGIN пользователя в БД не соответствует отправленным данным");
            System.out.println("Сравнение LOGIN прошло успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод удаления соданного user из базы после всех запросов
     */
    @AfterEach
    @DisplayName("Удаление User из базы")
    public void deleteUserInDataBase() {
        System.out.print(userID);
        try {
            sqlSteps.deleteUser(userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

