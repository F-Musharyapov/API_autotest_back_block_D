package tests.users;

import database.UsersSqlSteps;
import database.model.UserModelBD;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;
import tests.BaseTest;

import static helpers.AssertHelper.assertUserCreateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса создания сущности User
 */
public class CreateUserTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта UsersCreateRequest
     */
    private UsersCreateRequest usersCreateRequest;

    /**
     * Переменная для хранения данных объекта UsersCreateResponse
     */
    private UsersCreateResponse usersCreateResponse;

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для создания сущности user и сравнения данных в запросе api и в БД")
    public void userCreateTest() {

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

        UserModelBD userBD = new UsersSqlSteps().getUsersModelBD(Integer.parseInt(usersCreateResponse.getId()));
        assertUserCreateFieldsEqual(userBD, usersCreateResponse);
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

