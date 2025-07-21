package tests.posts;

import database.PostsSqlSteps;
import helpers.AssertHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.convert.PostConvertPojo;
import pojo.posts.PostsCreateRequest;
import pojo.posts.PostsCreateResponse;
import tests.BaseTest;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса создания сущности Post
 */
public class CreatePostTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта PostsCreateRequest
     */
    private PostsCreateRequest postsCreateRequest;

    /**
     * Переменная для хранения данных объекта PostsCreateResponse
     */
    private PostsCreateResponse postsCreateResponse;

    @SneakyThrows
    @Test
    @DisplayName("Тестовый метод для создания сущности Post и сравнения отправленных данных с полученными из БД")
    public void postCreateTest() {

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

        AssertHelper.assertObjectsEqual(PostConvertPojo.from(postsCreateResponse),
                new PostsSqlSteps().getPostModelBD(postsCreateResponse.getId()));
    }

    /**
     * Метод удаления соданного post из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление Posts после завершения тестов")
    public void deletePostInDataBase() {
        if (postsCreateResponse != null) {
            new PostsSqlSteps().deletePost(String.valueOf(postsCreateResponse.getId()));
        }
    }
}