package tests.comments;

import database.CommentsSqlSteps;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsCreateRequest;
import pojo.comments.CommentsCreateResponse;
import pojo.convert.CommentConvertPojo;
import tests.BaseTest;

import static helpers.AssertHelper.assertCommentCreateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса создания сущности Comment
 */
public class CreateCommentTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта CommentsCreateRequest
     */
    private CommentsCreateRequest commentsCreateRequest;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsCreateResponse commentsCreateResponse;

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для создания сущности comment и сравнения отправленных данных с полученными из БД")
    public void commentCreateTest() {

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

        assertCommentCreateFieldsEqual(new CommentsSqlSteps().getCommentsModelBD(commentsCreateResponse.getId()), CommentConvertPojo.from(commentsCreateResponse));
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