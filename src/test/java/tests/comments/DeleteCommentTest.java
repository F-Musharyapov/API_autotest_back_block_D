package tests.comments;

import config.BaseConfig;
import database.CommentsSqlSteps;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsCreateRequest;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsDeleteResponse;
import tests.BaseTest;

import java.io.IOException;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса удаления сущности Comment
 */
public class DeleteCommentTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта CommentsCreateRequest
     */
    private CommentsCreateRequest commentsCreateRequest;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsCreateResponse commentsCreateResponse;

    /**
     * Метод создания сущности comment перед тестом
     */
    public void createComment() {

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
    @DisplayName("Тестовый метод для удаления comment, проверки статуса удаления в ответе API запроса и статуса в БД")
    public void commentDeleteTest() {

        CommentsDeleteResponse commentsDeleteResponse = given()
                .spec(requestSpecification)
                .when()
                .delete(config.deleteCommentEndpoint() + commentsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(CommentsDeleteResponse.class);

        AssertHelper.assertStatusCommentDeleted(commentsDeleteResponse.getStatus());
        AssertHelper.assertStatusCommentDeleted(new CommentsSqlSteps().getCommentsModelBD(commentsCreateResponse.getId()).getStatus());
    }
}