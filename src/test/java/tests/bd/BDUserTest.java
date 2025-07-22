package tests.bd;

import database.UsersSqlSteps;
import database.model.UserModelBDRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.users.UsersReadResponse;
import tests.BaseTest;

import java.time.LocalDateTime;

import static helpers.AssertHelper.assertUserReadFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

public class BDUserTest extends BaseTest {

    private static String createdUserId;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private UsersReadResponse usersReadResponse;

    /**
     * Метод создания сущности user базе данных перед тестом  API
     */
    @BeforeEach
    public void CreateUserBD() {
        UserModelBDRequest userModelBDRequest = UserModelBDRequest.builder()
                .user_login(getUserRandomUserName())
                .user_pass(getUserRandomPassword())
                .user_nicename(getUserRandomSlug())
                .user_email(getUserRandomEmail())
                .user_url(getUserRandomUrl())
                .user_registered(LocalDateTime.now())
                .user_activation_key(ACTIVATION_KEY_USER)
                .user_status(STATUS_USER)
                .display_name(getUserRandomDisplayName())
                .nickname(getUserRandomNickName())
                .first_name(getUserRandomFirstName())
                .last_name(getUserRandomLastname())
                .description(getUserRandomDescription())
                .build();

        //System.out.print("БД1 = " + userModelBDRequest);
        this.createdUserId = String.valueOf(new UsersSqlSteps().createUserDoubleTable(userModelBDRequest));
        //System.out.println("\nCreated comment with ID: " + createdUserId);
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для проверки запроса API на получение данных, сравнение c данными в БД")
    public void testUserReadAPI() {
        UsersReadResponse usersReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readUserEndpoint() + createdUserId)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(UsersReadResponse.class);
        //System.out.println("\nАПИ = " + usersReadResponse);
        //System.out.println("\nБД2 = " + new UsersSqlSteps().getUsersModelBD(Integer.parseInt(createdUserId)));
        assertUserReadFieldsEqual(new UsersSqlSteps().getUsersModelBD(Integer.parseInt(createdUserId)), usersReadResponse);
    }

    /**
     * Метод удаления соданного user из БД после завершения тестов
     */
    @SneakyThrows
    @AfterAll
    @DisplayName("Удаление user после завершения тестов")
    public static void deleteUserInDataBase() {
        if (createdUserId != null) {
            new UsersSqlSteps().deleteUser(createdUserId);
        }
    }
}

