package tests.posts;

import config.BaseConfig;
import database.PostsSqlSteps;
import database.model.PostModelBD;
import helpers.AssertHelper;
import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.convert.PostConvertPojo;
import pojo.posts.PostsCreateRequest;
import pojo.posts.PostsCreateResponse;
import pojo.posts.PostsReadResponse;

import java.io.IOException;
import java.sql.Connection;

import static helpers.TestDataHelper.*;
import static io.restassured.RestAssured.given;

/**
 * Класс тестирования API запроса чтения данных сущности Post
 */
public class ReadPostTest {

    /**
     * Экземпляра конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Экземпляр спецификации RestAssured
     */
    private RequestSpecification requestSpecification;

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
     * Метод инициализации спецификации запроса и запуска метода createPost
     *
     * @throws IOException Обработка ошибок при инициализации спецификацию запроса BaseRequests.initRequestSpecification()
     */
    @BeforeEach
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
        createPost();
    }

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

        Connection connection = PostsSqlSteps.getConnection();
        PostModelBD postBD = new PostsSqlSteps(connection).getPostModelBD(postsReadResponse.getId());
        connection.close();

        PostConvertPojo postConvertPojo = PostConvertPojo.from(postsReadResponse);

        AssertHelper.assertObjectsEqual(postConvertPojo, postBD);

    }

    /**
     * Метод удаления соданного post из БД после завершения тестов
     */
    @SneakyThrows
    @AfterEach
    @DisplayName("Удаление Posts после завершения тестов")
    public void deletePostInDataBase() {
        if (postsCreateResponse != null) {
            Connection connection = PostsSqlSteps.getConnection();
            new PostsSqlSteps(connection).deletePost(String.valueOf(postsCreateResponse.getId()));
            connection.close();
        }
    }
}