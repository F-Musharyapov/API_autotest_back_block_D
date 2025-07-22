package tests.bd;

import database.PostsSqlSteps;
import database.model.PostModelBDRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.convert.PostConvertPojo;
import pojo.posts.PostsReadResponse;
import tests.BaseTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static helpers.AssertHelper.assertPostReadFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

public class BDPostTest extends BaseTest {

    private static String createdPostId;

    /**
     * Переменная для хранения данных объекта commentsCreateResponse
     */
    private PostsReadResponse postsReadResponse;

    /**
     * Метод создания сущности post в базе данных перед тестом
     */
    @BeforeEach
    public void CreatePostBD() {
        PostModelBDRequest postModelBDRequest = PostModelBDRequest.builder()
                .author(AUTHOR_POST)
                .date(LocalDateTime.now())
                .date_gmt(LocalDateTime.now(ZoneOffset.UTC))
                .content(getPostRandomContent())
                .title(getPostRandomTitle())
                .excerpt(getPostRandomExcerpt())
                .status(getPostRandomStatus())
                .comment_status(getPostRandomCommentStatus())
                .ping_status(PING_STATUS_POST)
                .slug(getPostRandomSlug())
                .modified(LocalDateTime.now())
                .modified_gmt(LocalDateTime.now(ZoneOffset.UTC))
                .guid(GUID_POST)
                .type(TYPE_POST)
                .to_ping(TO_PING_POST)
                .pinged(PINGED_POST)
                .post_content_filtered(CONTENT_FILTERED_POST)
                .build();

        this.createdPostId = String.valueOf(new PostsSqlSteps().createPostBD(postModelBDRequest));
        //System.out.println("\nCreated comment with ID: " + createdPostId);
        //System.out.println("\nCreated comment with ID: " + postModelBDRequest);
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для проверки запроса API на получение данных, сравнение c данными в БД")
    public void testPostReadAPI() {
        PostsReadResponse postsReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readPostEndpoint() + createdPostId)
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(PostsReadResponse.class);

        assertPostReadFieldsEqual(new PostsSqlSteps().getPostModelBDResponse(Integer.parseInt(createdPostId)), PostConvertPojo.from(postsReadResponse));
    }

    /**
     * Метод удаления соданного comment из БД после завершения тестов
     */
    @SneakyThrows
    @AfterAll
    @DisplayName("Удаление Post после завершения тестов")
    public static void deletePostInDataBase() {
        if (createdPostId != null) {
            new PostsSqlSteps().deletePost(createdPostId);
        }
    }
}
