package tests.users;

import config.BaseConfig;
import database.SqlSteps;
import database.model.UserModelBD;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;

import java.io.IOException;
import java.sql.Connection;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования создания сущности User
 */
public class CreateUserTest {

    /**
     * Экземпляра конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Экземпляр спецификации RestAssured
     */
    private RequestSpecification requestSpecification;

    /**
     * Переменная для хранения данных объекта UsersCreateRequest
     */
    private UsersCreateRequest usersCreateRequest;

    /**
     * Переменная для хранения данных объекта UsersCreateResponse
     */
    private UsersCreateResponse usersCreateResponse;

    /**
     * Метод инициализации спецификации запроса
     *
     * @throws IOException Обработка ошибок при инициализации спецификацию запроса BaseRequests.initRequestSpecification()
     */
    @BeforeEach
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для создания сущности user и сравнения отправленных данных с полученными")
    public void userCreateTest() {

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

        Connection connection = SqlSteps.getConnection();
        UserModelBD userBD = new SqlSteps(connection).getUsersModelBD(Integer.parseInt(usersCreateResponse.getId()));
        connection.close();

        AssertHelper.assertObjectsEqual(usersCreateResponse, userBD);
    }

    /**
     * Метод удаления соданного user из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление User после завершения тестов")
    public void deleteUserInDataBase() {
        if (usersCreateResponse != null) {
            Connection connection = SqlSteps.getConnection();
            new SqlSteps(connection).deleteUser(usersCreateResponse.getId());
            connection.close();
        }
    }
}

