package helpers;

import database.model.CommentModelBD;
import database.model.PostModelBDResponse;
import database.model.UserModelBD;
import org.assertj.core.api.Assertions;
import pojo.comments.CommentsDeleteResponse;
import pojo.convert.CommentConvertPojo;
import pojo.convert.PostConvertPojo;
import pojo.posts.PostsDeleteResponse;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersDeleteResponse;
import pojo.users.UsersReadResponse;
import pojo.users.UsersUpdateResponse;

import java.util.Objects;

/**
 * Набор Assert
 */
public class AssertHelper extends Assertions {

    /**
     * Сравнение данных для класса CreateCommentTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertCommentCreateFieldsEqual(CommentModelBD expected, CommentConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Post", expected.getPost(), actual.getPost());
        assertCheckField("Author_name", expected.getAuthor_name(), actual.getAuthor_name());
        assertCheckField("Author_email", expected.getAuthor_email(), actual.getAuthor_email());
        assertCheckField("Author_url", expected.getAuthor_url(), actual.getAuthor_url());
        assertCheckField("Author_ip", expected.getAuthor_ip(), actual.getAuthor_ip());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Author_user_agent", expected.getAuthor_user_agent(), actual.getAuthor_user_agent());
        assertCheckField("Type", expected.getType(), actual.getType());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
    }

    /**
     * Сравнение данных для класса ReadCommentTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertCommentReadFieldsEqual(CommentModelBD expected, CommentConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Post", expected.getPost(), actual.getPost());
        assertCheckField("Author_name", expected.getAuthor_name(), actual.getAuthor_name());
        assertCheckField("Author_url", expected.getAuthor_url(), actual.getAuthor_url());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Type", expected.getType(), actual.getType());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
    }

    /**
     * Сравнение данных для класса UpdateCommentTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertCommentUpdateFieldsEqual(CommentModelBD expected, CommentConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Post", expected.getPost(), actual.getPost());
        assertCheckField("Author_name", expected.getAuthor_name(), actual.getAuthor_name());
        assertCheckField("Author_email", expected.getAuthor_email(), actual.getAuthor_email());
        assertCheckField("Author_url", expected.getAuthor_url(), actual.getAuthor_url());
        assertCheckField("Author_ip", expected.getAuthor_ip(), actual.getAuthor_ip());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Author_user_agent", expected.getAuthor_user_agent(), actual.getAuthor_user_agent());
        assertCheckField("Type", expected.getType(), actual.getType());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
    }

    /**
     * Сравнение данных для класса DeleteCommentTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertCommentDeleteStatusEqual(CommentModelBD expected, CommentsDeleteResponse actual) {
        assertCheckField("status", "trash", expected.getStatus());
        assertCheckField("status", "trash", actual.getStatus());
    }

    /**
     * Сравнение данных для класса CreatePostTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertPostCreateFieldsEqual(PostModelBDResponse expected, PostConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Title", expected.getTitle(), actual.getTitle());
        assertCheckField("Excerpt", expected.getExcerpt(), actual.getExcerpt());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Comment_status", expected.getComment_status(), actual.getComment_status());
        assertCheckField("Ping_status", expected.getPing_status(), actual.getPing_status());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Modified", expected.getModified(), actual.getModified());
        assertCheckField("Modified_gmt", expected.getModified_gmt(), actual.getModified_gmt());
        assertCheckField("Guid", expected.getGuid(), actual.getGuid());
        assertCheckField("Type", expected.getType(), actual.getType());
    }

    /**
     * Сравнение данных для класса ReadPostTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertPostReadFieldsEqual(PostModelBDResponse expected, PostConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Title", expected.getTitle(), actual.getTitle());
        assertCheckField("Excerpt", expected.getExcerpt(), actual.getExcerpt());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Comment_status", expected.getComment_status(), actual.getComment_status());
        assertCheckField("Ping_status", expected.getPing_status(), actual.getPing_status());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Modified", expected.getModified(), actual.getModified());
        assertCheckField("Modified_gmt", expected.getModified_gmt(), actual.getModified_gmt());
        assertCheckField("Guid", expected.getGuid(), actual.getGuid());
    }

    /**
     * Сравнение данных для класса UpdatePostTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertPostUpdateFieldsEqual(PostModelBDResponse expected, PostConvertPojo actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Author", expected.getAuthor(), actual.getAuthor());
        assertCheckField("Date", expected.getDate(), actual.getDate());
        assertCheckField("Date_gmt", expected.getDate_gmt(), actual.getDate_gmt());
        assertCheckField("Content", expected.getContent(), actual.getContent());
        assertCheckField("Title", expected.getTitle(), actual.getTitle());
        assertCheckField("Excerpt", expected.getExcerpt(), actual.getExcerpt());
        assertCheckField("Status", expected.getStatus(), actual.getStatus());
        assertCheckField("Comment_status", expected.getComment_status(), actual.getComment_status());
        assertCheckField("Ping_status", expected.getPing_status(), actual.getPing_status());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Modified", expected.getModified(), actual.getModified());
        assertCheckField("Modified_gmt", expected.getModified_gmt(), actual.getModified_gmt());
        assertCheckField("Guid", expected.getGuid(), actual.getGuid());
        assertCheckField("Type", expected.getType(), actual.getType());
    }

    /**
     * Сравнение данных для класса DeletePostTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertPostDeleteStatusEqual(PostModelBDResponse expected, PostsDeleteResponse actual) {
        assertCheckField("status", "trash", expected.getStatus());
        assertCheckField("status", "trash", actual.getStatus());
    }

    /**
     * Сравнение данных для класса CreateUserTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertUserCreateFieldsEqual(UserModelBD expected, UsersCreateResponse actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Username", expected.getUsername(), actual.getUsername());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Email", expected.getEmail(), actual.getEmail());
        assertCheckField("Nickname", expected.getNickname(), actual.getNickname());
        assertCheckField("First_name", expected.getFirst_name(), actual.getFirst_name());
        assertCheckField("Last_name", expected.getLast_name(), actual.getLast_name());
        assertCheckField("Description", expected.getDescription(), actual.getDescription());
        assertCheckField("Registered_date", expected.getRegistered_date(), actual.getRegistered_date());
        assertCheckField("Name", expected.getName(), actual.getName());
        assertCheckField("Url", expected.getUrl(), actual.getUrl());
    }

    /**
     * Сравнение данных для класса ReadUserTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertUserReadFieldsEqual(UserModelBD expected, UsersReadResponse actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Description", expected.getDescription(), actual.getDescription());
        assertCheckField("Name", expected.getName(), actual.getName());
        assertCheckField("Url", expected.getUrl(), actual.getUrl());
    }

    /**
     * Сравнение данных для класса UpdateUserTest
     *
     * @param expected ожидаемые данные
     * @param actual   актуальные данные
     */
    public static void assertUserUpdateFieldsEqual(UserModelBD expected, UsersUpdateResponse actual) {
        assertCheckField("Id", expected.getId(), actual.getId());
        assertCheckField("Username", expected.getUsername(), actual.getUsername());
        assertCheckField("Slug", expected.getSlug(), actual.getSlug());
        assertCheckField("Email", expected.getEmail(), actual.getEmail());
        assertCheckField("Nickname", expected.getNickname(), actual.getNickname());
        assertCheckField("First_name", expected.getFirst_name(), actual.getFirst_name());
        assertCheckField("Last_name", expected.getLast_name(), actual.getLast_name());
        assertCheckField("Description", expected.getDescription(), actual.getDescription());
        assertCheckField("Registered_date", expected.getRegistered_date(), actual.getRegistered_date());
        assertCheckField("Name", expected.getName(), actual.getName());
        assertCheckField("Url", expected.getUrl(), actual.getUrl());
    }

    /**
     * Сравнение данных для класса DeleteUserTest
     *
     * @param object проверяемый объект
     */
    public static void assertUserDeleteStatusEqual(UsersDeleteResponse object) {
        assertCheckField("deleted", true, object.getDeleted());
    }

    /**
     * Метод проверки совпадения проверяемого поля
     *
     * @param fieldName     проверяемое поле
     * @param expectedValue ожидаемые данные
     * @param actualValue   актуальные данные
     */
    private static void assertCheckField(String fieldName, Object expectedValue, Object actualValue) {
        if (!Objects.equals(expectedValue, actualValue)) {
            throw new AssertionError(fieldName + " не совпадает: expected= " + expectedValue + ", actual= " + actualValue);
        }
    }

    /**
     * Проверка наличия user в БД
     *
     * @param bdObject ответ наличия user в БД
     */
    public static void assertUserDeletedBD(Object bdObject) {
        assertThat(bdObject)
                .withFailMessage("Пользователь не удалился из БД")
                .isNull();
    }
}