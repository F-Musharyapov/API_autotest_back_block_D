package tests.posts;

import database.PostsSqlSteps;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.convert.PostConvertPojo;
import pojo.posts.PostsCreateRequest;
import pojo.posts.PostsCreateResponse;
import pojo.posts.PostsUpdateRequest;
import pojo.posts.PostsUpdateResponse;
import tests.BaseTest;

import static helpers.AssertHelper.assertPostUpdateFieldsEqual;
import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса обновления данных сущности Post
 */
public class UpdatePostTest extends BaseTest {

    /**
     * Переменная для хранения данных объекта PostsCreateRequest
     */
    private PostsCreateRequest postsCreateRequest;

    /**
     * Переменная для хранения данных объекта PostsCreateResponse
     */
    private PostsCreateResponse postsCreateResponse;

    /**
     * Переменная для хранения данных объекта PostsUpdateRequest
     */
    private PostsUpdateRequest postsUpdateRequest;

    /**
     * Переменная для хранения данных объекта PostsUpdateRequest
     */
    private PostsUpdateResponse postsUpdateResponse;

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
    @DisplayName("Тестовый метод для обновления post, сравнения данных в теле ответа api и в БД")
    public void postUpdateTest() {

        postsUpdateRequest = PostsUpdateRequest.builder()
                .slug(getPostRandomSlug())
                .status(getPostRandomStatus())
                .title(getPostRandomTitle())
                .content(getPostRandomContent())
                .excerpt(getPostRandomExcerpt())
                .comment_status(getPostRandomCommentStatus())
                .ping_status(getPostRandomPingStatus())
                .format(getPostRandomFormat())
                .sticky(getPostRandomSticky())
                .build();

        postsUpdateResponse = given()
                .spec(requestSpecification)
                .body(postsUpdateRequest)
                .when()
                .post(config.updatePostEndpoint() + postsCreateResponse.getId())
                .then()
                .statusCode(STATUS_CODE_OK)
                .extract().as(PostsUpdateResponse.class);

        assertPostUpdateFieldsEqual(new PostsSqlSteps().getPostModelBDResponse(postsUpdateResponse.getId()), PostConvertPojo.from(postsUpdateResponse));
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