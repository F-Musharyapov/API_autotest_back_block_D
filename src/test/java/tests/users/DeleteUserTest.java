package tests.users;

import config.BaseConfig;
import database.SqlSteps;
import database.model.UserModelBD;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersDeleteRequest;
import pojo.users.UsersDeleteResponse;

import java.io.IOException;
import java.sql.Connection;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

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

    /**
     * Метод создания сущности user перед тестом
     */
    @BeforeEach
    public void createUser() {

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

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для удаления юзера и проверки в БД")
    public void userDeleteTest() {

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

        Connection connection = SqlSteps.getConnection();
        UserModelBD userBD = new SqlSteps(connection).getUsersModelBD(Integer.parseInt(usersCreateResponse.getId()));
        connection.close();

        AssertHelper.assertStatusUserDeleted(usersDeleteResponse.getDeleted());
        AssertHelper.assertUserDeletedBD(userBD);
    }
}