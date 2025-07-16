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
import pojo.UsersUpdateRequest;
import pojo.UsersUpdateResponse;
import pojo.UsersCreateRequest;
import pojo.UsersCreateResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс тестирования POST-запроса
 */
public class UpdateUserTest {

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
     * Переменная для хранения объекта pojo при создании пользователя
     */
    private UsersCreateRequest usersCreateRequest;
    private UsersCreateResponse usersCreateResponse;

    private UsersUpdateRequest usersUpdateRequest;
    private UsersUpdateResponse usersUpdateResponse;


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

    /**
     * Метод создания юзера
     */
    @BeforeEach
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
        /*
        given()
                .when()
                .get(REQUEST_USER_GET + userID)
                .then()
                .statusCode(STATUS_CODE_OK);
                //.extract().as(UsersRequest.class);

         */
    }

    @Test
    @Description("Тестовый метод для обновления юзера, сравнения данных в запросе и в БД")
    public void userUpdate() {

        usersUpdateRequest = UsersUpdateRequest.builder()
                .email(getRandomEmail())
                .build();

        usersUpdateResponse = given()
                .spec(requestSpecification)
                .body(usersUpdateRequest)
                .when()
                .post(REQUEST_USER_UPDATE_POST + userID)
                .then()
                .statusCode(STATUS_CODE_OK)
                //.extract();
                .extract().as(UsersUpdateResponse.class);
        /*
        UsersCreateResponse usersPojoResponse = given()
                .spec(requestSpecification)
                .when()
                .get(REQUEST_USER_GET + userID)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersCreateResponse.class);

         */

        // Проверка данных в БД
        UserModelBD userBD = null;
        try {
            userBD = sqlSteps.getUsersModelBD(Integer.parseInt(userID));
            // Вывод значения посмотреть перед сравнением
            System.out.println("Ожидаемый LOGIN: " + usersUpdateResponse.getEmail());
            System.out.println("Фактический LOGIN из БД: " + userBD.getUser_email());

            assertEquals(userBD.getUser_email(), usersUpdateResponse.getEmail(),
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

