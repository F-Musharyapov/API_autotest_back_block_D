package tests;

import config.BaseConfig;
import database.SqlConnector;
import database.SqlSteps;
import database.model.UserModelBD;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс тестирования удаления сущности User
 */
public class DeleteUserTest {

    /**
     * Экземпляра конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

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
    //private UsersDeleteResponse usersDeleteResponse;
    //private UsersDeleteRequest usersDeleteRequest;

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
     * Создание пользователя перед тестом удаления
     */
    @BeforeEach
    public void createUserTest() {

        usersCreateRequest = UsersCreateRequest.builder()
                .username(getRandomUserName())
                .name(getRandomDisplayName())
                .first_name(getRandomFirstName())
                .last_name(getRandomLastname())
                .email(getRandomEmail())
                .url(getRandomUrl())
                .description(getRandomDescription())
                .nickname(getRandomNickName())
                .slug(getRandomSlug())
                .password(getRandomPassword())
                .build();

        usersCreateResponse = given()
                .spec(requestSpecification)
                .body(usersCreateRequest)
                .when()
                .post(config.createUserEndpoint())
                .then()
                .statusCode(STATUS_CODE_CREATED)
                .extract().as(UsersCreateResponse.class);
    }

    @Test
    @DisplayName("Тестовый метод для удаления юзера и проверки в БД")
    public void userDelete() {

        UsersDeleteRequest usersDeleteRequest = UsersDeleteRequest.builder()
                .reassign(REASSIGN)
                .force(FORCE)
                .build();

        UsersDeleteResponse usersDeleteResponse = given()
                .spec(requestSpecification)
                .body(usersDeleteRequest)
                .when()
                .delete(config.deleteUserEndpoint() + usersCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersDeleteResponse.class);


        UserModelBD userBD = sqlSteps.getUsersModelBD(Integer.parseInt(usersCreateResponse.getId()));
        AssertHelper.assertUserDeleted(usersDeleteResponse.getDeleted(), userBD);

    }

    /**
     * Метод отключение от БД
     */
    @AfterEach
    @DisplayName("Отключение от базы данных")
    public void deleteUserСloseConnection() {
        sqlConnector.closeConnection(connection);
    }
}

