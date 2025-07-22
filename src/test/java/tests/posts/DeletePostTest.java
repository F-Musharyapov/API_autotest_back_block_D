package tests.posts;

import database.PostsSqlSteps;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.posts.PostsCreateRequest;
import pojo.posts.PostsCreateResponse;
import pojo.posts.PostsDeleteResponse;
import tests.BaseTest;

import static helpers.AssertHelper.assertPostDeleteStatusEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса удаления сущности User
 */
public class DeletePostTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта PostsCreateRequest
     */
    private PostsCreateRequest postsCreateRequest;

    /**
     * Переменная для хранения данных объекта PostsCreateResponse
     */
    private PostsCreateResponse postsCreateResponse;

    /**
     * Метод создания сущности post перед тестом
     */
    @BeforeEach
    public void createPost() {

        postsCreateRequest = PostsCreateRequest.builder()
                .slug(getPostRandomSlug())
                .status(getPostRandomStatus())
                .title(getPostRandomTitle())
                .content(getPostRandomContent())
                .author(AUTHOR_POST)
                .excerpt(getPostRandomExcerpt())
                .comment_status(getPostRandomCommentStatus())
                .ping_status(getPostRandomPingStatus())
                .format(getPostRandomFormat())
                .sticky(getPostRandomSticky())
                .build();

        postsCreateResponse = given()
                .spec(requestSpecification)
                .body(postsCreateRequest)
                .when()
                .post(config.createPostEndpoint())
                .then()
                .statusCode(STATUS_CODE_CREATED)
                .extract().as(PostsCreateResponse.class);
    }

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для удаления post и проверки в БД")
    public void postDeleteTest() {

        PostsDeleteResponse postsDeleteResponse = given()
                .spec(requestSpecification)
                .when()
                .delete(config.deletePostEndpoint() + postsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(PostsDeleteResponse.class);

        assertPostDeleteStatusEqual(new PostsSqlSteps().getPostModelBDResponse(postsCreateResponse.getId()), postsDeleteResponse);
    }
}