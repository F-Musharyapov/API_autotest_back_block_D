package tests.users;

import database.UsersSqlSteps;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersCreateRequest;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersUpdateRequest;
import pojo.users.UsersUpdateResponse;
import tests.BaseTest;

import static helpers.AssertHelper.assertUserUpdateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса обновления данных сущности User
 */
public class UpdateUserTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта UsersCreateRequest
     */
    private UsersCreateRequest usersCreateRequest;

    /**
     * Переменная для хранения данных объекта UsersCreateResponse
     */
    private UsersCreateResponse usersCreateResponse;

    /**
     * Переменная для хранения данных объекта UsersUpdateRequest
     */
    private UsersUpdateRequest usersUpdateRequest;

    /**
     * Переменная для хранения данных объекта UsersUpdateRequest
     */
    private UsersUpdateResponse usersUpdateResponse;

    /**
     * Метод создания сущности user перед тестом
     */
    @BeforeEach
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
    @DisplayName("Тестовый метод для обновления данных user, сравнение данных в запросе и в БД")
    public void userUpdateTest() {

        usersUpdateRequest = UsersUpdateRequest.builder()
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

        usersUpdateResponse = given()
                .spec(requestSpecification)
                .body(usersUpdateRequest)
                .when()
                .post(config.updateUserEndpoint() + usersCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersUpdateResponse.class);

        assertUserUpdateFieldsEqual(new UsersSqlSteps().getUsersModelBD(Integer.parseInt(usersUpdateResponse.getId())), usersUpdateResponse);
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

