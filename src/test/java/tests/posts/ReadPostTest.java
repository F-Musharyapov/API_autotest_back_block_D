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
import pojo.posts.PostsReadResponse;
import tests.BaseTest;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса чтения данных сущности Post
 */
public class ReadPostTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта PostsCreateRequest
     */
    private PostsCreateRequest postsCreateRequest;

    /**
     * Переменная для хранения данных объекта PostsCreateResponse
     */
    private PostsCreateResponse postsCreateResponse;

    /**
     * Переменная для хранения данных объекта PostsReadResponse
     */
    private PostsReadResponse postsReadResponse;

    /**
     * Метод создания сущности post перед тестом
     */
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
    @DisplayName("Тестовый метод для сравнения данных post в запросе api и в БД")
    public void postReadTest() {

        postsReadResponse = given()
                .spec(requestSpecification)
                .when()
                .get(config.readPostEndpoint() + postsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(PostsReadResponse.class);

        AssertHelper.assertObjectsEqual(PostConvertPojo.from(postsReadResponse),
                new PostsSqlSteps().getPostModelBD(postsReadResponse.getId()));
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