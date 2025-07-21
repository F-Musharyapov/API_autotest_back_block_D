package tests.comments;

import database.CommentsSqlSteps;
import helpers.AssertHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsCreateRequest;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsUpdateRequest;
import pojo.comments.CommentsUpdateResponse;
import pojo.convert.CommentConvertPojo;
import tests.BaseTest;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса обновления данных сущности Comment
 */
public class UpdateCommentTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта CommentsCreateRequest
     */
    private CommentsCreateRequest commentsCreateRequest;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsCreateResponse commentsCreateResponse;

    /**
     * Переменная для хранения данных объекта commentsUpdateRequest
     */
    private CommentsUpdateRequest commentsUpdateRequest;

    /**
     * Переменная для хранения данных объекта commentsUpdateResponse
     */
    private CommentsUpdateResponse commentsUpdateResponse;

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
    @DisplayName("Тестовый метод для изменения и сравнения данных comment в запросе api и в БД")
    public void commentUpdateTest() {

        commentsUpdateRequest = CommentsUpdateRequest.builder()
                .author(AUTHOR_ID_UPDATE)
                .content(getCommentRandomContent())
                .post(POST_ID_UPDATE)
                .build();

        commentsUpdateResponse = given()
                .spec(requestSpecification)
                .body(commentsUpdateRequest)
                .when()
                .post(config.updateCommentEndpoint() + commentsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(CommentsUpdateResponse.class);

        AssertHelper.assertObjectsEqual(CommentConvertPojo.from(commentsUpdateResponse),
                new CommentsSqlSteps().getCommentsModelBD(commentsUpdateResponse.getId()));
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