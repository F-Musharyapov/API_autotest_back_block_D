package tests.comments;

import config.BaseConfig;
import database.CommentsSqlSteps;
import database.model.CommentModelBD;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsCreateRequest;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsReadResponse;
import pojo.convert.CommentConvertPojo;

import java.io.IOException;
import java.sql.Connection;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса чтения данных сущности Comment
 */
public class ReadCommentTest {

    /**
     * Экземпляра конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Экземпляр спецификации RestAssured
     */
    private RequestSpecification requestSpecification;

    /**
     * Переменная для хранения данных объекта CommentsCreateRequest
     */
    private CommentsCreateRequest commentsCreateRequest;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsCreateResponse commentsCreateResponse;

    /**
     * Переменная для хранения данных объекта commentsReadResponse
     */
    private CommentsReadResponse commentsReadResponse;

    /**
     * Метод инициализации спецификации запроса и запуск метода commentCreate
     *
     * @throws IOException Обработка ошибок при инициализации спецификацию запроса BaseRequests.initRequestSpecification()
     */
    @BeforeEach
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
        commentCreate();
    }

    /**
     * Метод создания сущности comment перед тестом
     */
    public void commentCreate() {

        commentsCreateRequest = CommentsCreateRequest.builder()
                .author(AUTHOR_ID)
                .content(getCommentRandomContent())
                .post(POST_ID)
                .build();

        commentsCreateResponse = given()
                .spec(requestSpecification)
                .body(commentsCreateRequest)
                .when()
                .post(config.createCommentEndpoint())
                .then()
                .statusCode(STATUS_CODE_CREATED)
                .extract().as(CommentsCreateResponse.class);
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для сравнения данных comment в запросе api и в БД")
    public void commentReadTest() {

        commentsReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readCommentEndpoint() + commentsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(CommentsReadResponse.class);

        Connection connection = CommentsSqlSteps.getConnection();
        CommentModelBD commentBD = new CommentsSqlSteps(connection).getCommentsModelBD(commentsReadResponse.getId());
        connection.close();

        CommentConvertPojo commentConvertPojo = CommentConvertPojo.from(commentsReadResponse);

        AssertHelper.assertCommentFieldsEqual(commentConvertPojo, commentBD);
    }

    /**
     * Метод удаления соданного comment из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление Comment после завершения тестов")
    public void deleteCommentInDataBase() {
        if (commentsCreateResponse != null) {
            Connection connection = CommentsSqlSteps.getConnection();
            new CommentsSqlSteps(connection).deleteComment(String.valueOf(commentsCreateResponse.getId()));
            connection.close();
        }
    }
}