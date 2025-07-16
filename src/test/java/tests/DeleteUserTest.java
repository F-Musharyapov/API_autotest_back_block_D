package tests;

import database.SqlConnector;
import database.SqlSteps;
import database.model.UserModelBD;
import helpers.BaseRequests;
import io.qameta.allure.Description;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Класс тестирования POST-запроса
 */
public class DeleteUserTest {

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

    private UserModelBD userBD;

    /**
     * Переменная для хранения объекта pojo при создании пользователя
     */
    private UsersCreateRequest usersCreateRequest;
    private UsersCreateResponse usersCreateResponse;
    private UsersUpdateRequest usersUpdateRequest;
    private UsersUpdateResponse usersUpdateResponse;
    private UsersDeleteResponse usersDeleteResponse;
    private UsersDeleteRequest usersDeleteRequest;


    /**
     * Переменная для хранения user ID
     */
    private String userID;

    /**
     * Метод инициализации спецификации запроса и создания пользователя
     *
     * @throws IOException если не удается инициализировать спецификацию запроса
     */
    @BeforeEach
    public void setup() throws IOException, SQLException {
        requestSpecification = BaseRequests.initRequestSpecification();
        sqlConnector = new SqlConnector();
        connection = sqlConnector.openConnection();
        sqlSteps = new SqlSteps(connection);

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
    }

    @Test
    @Description("Тестовый метод для удаления юзера и проверки в БД")
    public void userDelete() {

        UsersDeleteRequest usersDeleteRequest = UsersDeleteRequest.builder()
                .reassign(REASSIGN)
                .force(FORCE)
                .build();

        UsersDeleteResponse usersDeleteResponse = given()
                .spec(requestSpecification)
                .body(usersDeleteRequest)
                .when()
                .delete(REQUEST_USER_DELETE + userID)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersDeleteResponse.class);

        assertEquals(DELETE_STATUS, usersDeleteResponse.getDeleted(),
                "DELETE_STATUS пользователя в БД не true");

        UserModelBD userBD = sqlSteps.getUsersModelBD(Integer.parseInt(userID));
        assertThat(userBD)
                .withFailMessage("Пользователь не удалился из БД, тест не пройден")
                .isNull();

    }
}

