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
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersUpdateRequest;
import pojo.users.UsersUpdateResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс тестирования обновления сущности User
 */
public class UpdateUserTest {

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
    @DisplayName("Тестовый метод для обновления юзера, сравнения данных в запросе и в БД")
    public void userUpdate() {

        usersUpdateRequest = UsersUpdateRequest.builder()
                //.username(getRandomUserName())
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

        usersUpdateResponse = given()
                .spec(requestSpecification)
                .body(usersUpdateRequest)
                .when()
                .post(config.updateUserEndpoint() + usersCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                //.extract();
                .extract().as(UsersUpdateResponse.class);

        UserModelBD userBD = null;
        try {
            userBD = sqlSteps.getUsersModelBD(Integer.parseInt(usersUpdateResponse.getId()));
            System.out.print("Вывод объекта " + usersCreateResponse);
            System.out.print("Вывод объекта " + usersUpdateResponse);
            System.out.print("Вывод объекта " + userBD);

            AssertHelper.assertObjectsEqual(usersUpdateResponse, userBD);
            System.out.println("Сравнение usersUpdateResponse и userBD прошло успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод удаления соданного user из БД после завершения тестов и отключение от БД
     */
    @AfterEach
    @DisplayName("Удаление User и отключение от БД")
    public void deleteUserInDataBase() {
        try {
            sqlSteps.deleteUser(usersUpdateResponse.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlConnector.closeConnection(connection);
    }
}

