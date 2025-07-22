package tests.bd;

import database.CommentsSqlSteps;
import database.model.CommentModelBD;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.comments.CommentsReadResponse;
import pojo.convert.CommentConvertPojo;
import tests.BaseTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static helpers.AssertHelper.assertCommentReadFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

public class BDCommentTest extends BaseTest {

    /**
     * Переменная для хранения id из базы данных
     */
    static String createdCommentId;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsReadResponse commentsReadResponse;

    /**
     * Метод создания сущности comment базе данных перед тестом  API
     */
    @BeforeEach
    public void CreateCommentBD() {
        CommentModelBD commentModelBD = CommentModelBD.builder()
                .post(POST_ID)
                .author_url(AUTHOR_URL_BD)
                .author_ip(AUTHOR_IP_BD)
                .date(LocalDateTime.now())
                .date_gmt(LocalDateTime.now(ZoneOffset.UTC))
                .content(getCommentRandomContent())
                .status(COMMENT_APPROVED_BD)
                .author_user_agent(COMMENT_USER_AGENT_BD)
                .type(COMMENT_TYPE_BD)
                .author(AUTHOR_ID)
                .build();
        this.createdCommentId = String.valueOf(new CommentsSqlSteps().createCommentBD(commentModelBD)); // Сохраняем в поле класса
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для проверки запроса API на получение данных, сравнение c данными в БД")
    public void testCommentReadAPI() {
        CommentsReadResponse commentsReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readCommentEndpoint() + createdCommentId)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(CommentsReadResponse.class);
        assertCommentReadFieldsEqual(new CommentsSqlSteps().getCommentsModelBD(Integer.parseInt(createdCommentId)), CommentConvertPojo.from(commentsReadResponse));
    }

    /**
     * Метод удаления соданного comment из БД после завершения тестов
     */
    @SneakyThrows
    @AfterAll
    @DisplayName("Удаление Comment после завершения тестов")
    public static void deleteCommentInDataBase() {
        if (createdCommentId != null) {
            new CommentsSqlSteps().deleteComment(createdCommentId);
        }
    }
}