package tests.users;

import config.BaseConfig;
import database.UsersSqlSteps;
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
 * Класс тестирования API запроса удаления сущности User
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
     * Метод инициализации спецификации запроса и запуск метода createUser
     *
     * @throws IOException Обработка ошибок при инициализации спецификацию запроса BaseRequests.initRequestSpecification()
     */
    @BeforeEach
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
        createUser();
    }

    /**
     * Метод создания сущности user перед тестом
     */
    public void createUser() {

        usersCreateRequest = UsersCreateRequest.builder()
                .username(getUserRandomUserName())
                .name(getUserRandomDisplayName())
                .first_name(getUserRandomFirstName())
                .last_name(getUserRandomLastname())
                .email(getUserRandomEmail())
                .url(getUserRandomUrl())
                .description(getUserRandomDescription())
                .nickname(getUserRandomNickName())
                .slug(getUserRandomSlug())
                .password(getUserRandomPassword())
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
    @DisplayName("Тестовый метод для удаления user, проверка в запросе api и в БД")
    public void userDeleteTest() {

        UsersDeleteRequest usersDeleteRequest = UsersDeleteRequest.builder()
                .reassign(REASSIGN_USER)
                .force(FORCE_USER)
                .build();

        UsersDeleteResponse usersDeleteResponse = given()
                .spec(requestSpecification)
                .body(usersDeleteRequest)
                .when()
                .delete(config.deleteUserEndpoint() + usersCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersDeleteResponse.class);

        Connection connection = UsersSqlSteps.getConnection();
        UserModelBD userBD = new UsersSqlSteps(connection).getUsersModelBD(Integer.parseInt(usersCreateResponse.getId()));
        connection.close();

        AssertHelper.assertStatusUserDeleted(usersDeleteResponse.getDeleted());
        AssertHelper.assertUserDeletedBD(userBD);
    }
}