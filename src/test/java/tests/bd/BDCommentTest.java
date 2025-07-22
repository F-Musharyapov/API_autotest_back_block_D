package tests.bd;

import database.CommentsSqlSteps;
import database.model.CommentModelBD;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
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

    static String createdCommentId;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private CommentsReadResponse commentsReadResponse;

    @Test
    public void testCreateCommentBD() {
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
        //System.out.print("БД1 = " + commentModelBD);
        this.createdCommentId = String.valueOf(new CommentsSqlSteps().createCommentBD(commentModelBD)); // Сохраняем в поле класса
        //System.out.println("\nCreated comment with ID: " + createdCommentId);

        CommentsReadResponse commentsReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readCommentEndpoint() + createdCommentId)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(CommentsReadResponse.class);
        //System.out.println("\nАПИ = " + commentsReadResponse);
        //CommentModelBD commentsbd = new CommentsSqlSteps().getCommentsModelBD(Integer.parseInt(createdCommentId));
        //System.out.println("\nБД2 = " + commentsbd);
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