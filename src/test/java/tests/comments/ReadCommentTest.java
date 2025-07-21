package tests.comments;

import database.CommentsSqlSteps;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsCreateRequest;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsReadResponse;
import pojo.convert.CommentConvertPojo;
import tests.BaseTest;

import static helpers.AssertHelper.assertCommentReadFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса чтения данных сущности Comment
 */
public class ReadCommentTest extends BaseTest {

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

        assertCommentReadFieldsEqual(new CommentsSqlSteps().getCommentsModelBD(commentsReadResponse.getId()), CommentConvertPojo.from(commentsReadResponse));
    }

    /**
     * Метод удаления соданного comment из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление Comment после завершения тестов")
    public void deleteCommentInDataBase() {
        if (commentsCreateResponse != null) {
            new CommentsSqlSteps().deleteComment(String.valueOf(commentsCreateResponse.getId()));
        }
    }
}