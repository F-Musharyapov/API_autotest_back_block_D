package tests.users;

import database.UsersSqlSteps;
import database.model.UserModelBD;
import helpers.AssertHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersReadResponse;
import tests.BaseTest;

import static helpers.AssertHelper.*;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса чтения данных сущности User
 */
public class ReadUserTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта UsersCreateRequest
     */
    private UsersCreateRequest usersCreateRequest;

    /**
     * Переменная для хранения данных объекта UsersCreateResponse
     */
    private UsersCreateResponse usersCreateResponse;

    /**
     * Переменная для хранения данных объекта UsersReadResponse
     */
    private UsersReadResponse usersReadResponse;

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
    @DisplayName("Тестовый метод для проверки запроса чтения, сравнение данных в запросе и в БД")
    public void userReadTest() {

        usersReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readUserEndpoint() + usersCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersReadResponse.class);

        UserModelBD userBD = new UsersSqlSteps().getUsersModelBD(Integer.parseInt(usersReadResponse.getId()));
        assertUserReadFieldsEqual(userBD, usersReadResponse);
    }

    /**
     * Метод удаления соданного user из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление User после завершения тестов")
    public void deleteUserInDataBase() {
        if (usersCreateResponse != null) {
            new UsersSqlSteps().deleteUser(String.valueOf(usersCreateResponse.getId()));
        }
    }
}