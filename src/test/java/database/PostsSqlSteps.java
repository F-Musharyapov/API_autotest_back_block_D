package database;

import config.BaseConfig;
import database.model.PostModelBD;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Метод для взаимодействия с БД для сущности Post
 */
@AllArgsConstructor
public class PostsSqlSteps {

    /**
     * Константы полей из БД
     */
    private static final String ID_FIELD = "ID"; //id
    private static final String POST_AUTHOR_FIELD = "post_author"; //author
    private static final String POST_DATE_FIELD = "post_date"; //date
    private static final String POST_DATE_GMT_FIELD = "post_date_gmt"; //date_gmt
    private static final String POST_CONTENT_FIELD = "post_content"; //content raw
    private static final String POST_TITLE_FIELD = "post_title"; //title raw
    private static final String POST_EXCEPT_FIELD = "post_excerpt"; //excerpt raw
    private static final String POST_STATUS_FIELD = "post_status"; //status
    private static final String COMMENT_STATUS_FIELD = "comment_status"; //comment_status
    private static final String PING_STATUS_FIELD = "ping_status"; //ping_status
    private static final String POST_PASSWORD_FIELD = "post_password"; //password
    private static final String POST_NAME_FIELD = "post_name"; //slug
    //private static final String TO_PING_FIELD = "to_ping"; //
    //private static final String PINGED_FIELD = "pinged"; //
    private static final String POST_MODIFIED_FIELD = "post_modified"; //modified
    private static final String POST_MODIFIED_GMT_FIELD = "post_modified_gmt"; //modified_gmt
    //private static final String POST_CONTENT_FILTERED_FIELD = "post_content_filtered"; //
    //private static final String POST_PARENT_FIELD = "post_parent"; //
    private static final String GUID_FIELD = "guid"; //guid raw
    //private static final String MENU_ORDER_FIELD = "menu_order"; //
    private static final String POST_TYPE_FIELD = "post_type"; //type
    //private static final String POST_MIME_TYPE_FIELD = "post_mime_type"; //
    //private static final String COMMENT_COUNT_FIELD = "comment_count"; //

    /**
     * Константы с запросами в БД
     */
    private static final String DELETE_SQL_REQUEST_POST = "DELETE FROM wp_posts WHERE %s = %s";
    private static final String SELECT_BY_ID_SQL_REQUEST_POST = "SELECT * FROM wp_posts WHERE %s = %d";

    /**
     * Экземпляра конфигурации
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    private final Connection connection;

    /**
     * Метод открытия подключения к базе данных с использованием try-with-resources
     *
     * @return экземпляр подключения
     */
    @SneakyThrows
    public static Connection getConnection() {
        Class.forName(config.driverDb());
        return DriverManager.getConnection(config.urlDb(), config.userDb(), config.passwordDb());
    }

    /**
     * Метод удаления post в БД
     *
     * @param id идентификатор поля, которое удаляем
     */
    public void deletePost(String id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_SQL_REQUEST_POST, ID_FIELD, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод запроса в БД для получения данных post
     *
     * @param id идентификатор поля, которое удаляем
     */
    public PostModelBD getPostModelBD(int id) {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet result = stmt.executeQuery(String.format(SELECT_BY_ID_SQL_REQUEST_POST, ID_FIELD, id));
            if (result.next()) {
                PostModelBD postModelBD = PostModelBD.builder()
                        .id(Integer.valueOf(result.getString(ID_FIELD)))
                        .author(Integer.valueOf(result.getString(POST_AUTHOR_FIELD)))
                        .date(result.getObject(POST_DATE_FIELD, LocalDateTime.class))
                        .date_gmt(result.getObject(POST_DATE_GMT_FIELD, LocalDateTime.class))
                        .content(result.getString(POST_CONTENT_FIELD))
                        .title(result.getString(POST_TITLE_FIELD))
                        .excerpt(result.getString(POST_EXCEPT_FIELD))
                        .status(result.getString(POST_STATUS_FIELD))
                        .comment_status(result.getString(COMMENT_STATUS_FIELD))
                        .ping_status(result.getString(PING_STATUS_FIELD))
                        .slug(result.getString(POST_NAME_FIELD))
                        .modified(result.getObject(POST_MODIFIED_FIELD, LocalDateTime.class))
                        .modified_gmt(result.getObject(POST_MODIFIED_GMT_FIELD, LocalDateTime.class))
                        .guid(result.getString(GUID_FIELD))
                        .type(result.getString(POST_TYPE_FIELD))
                        .build();
                return postModelBD;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
/*
{
        "id": 55,
        "date": "2025-07-18T22:48:55",
        "date_gmt": "2025-07-18T19:48:55",
        "guid": {
        "rendered": "http://localhost:8000/?p=55"
        },
        "modified": "2025-07-18T22:48:56",
        "modified_gmt": "2025-07-18T19:48:56",
        "slug": "puuzpokb-hsezbc-gygn-mz-otfe-rocqkgkl__trashed",
        "status": "trash",
        "type": "post",
        "link": "http://localhost:8000/?p=55",
        "title": {
        "rendered": "tmfqjqqs pwwjmo hpej"
        },
        "content": {
        "rendered": "<p>Quod aliquam rerum et ut officiis non molestiae voluptas exercitationem neque sit blanditiis veniam quia autem voluptate dolor molestias molestiae temporibus optio provident est quis occaecati sit reiciendis quas praesentium sint sint similique facere dolores magnam laboriosam accusamus iste sed labore voluptatem ut quia.</p>\n",
        "protected": false
        },
        "excerpt": {
        "rendered": "<p>Ipsam illo est quae a optio est enim distinctio officiis quo est quae voluptatem suscipit qui molestiae possimus officiis enim ut eos officiis.</p>\n",
        "protected": false
        },
        "author": 1,
        "featured_media": 0,
        "comment_status": "open",
        "ping_status": "open",
        "sticky": false,
        "template": "",
        "format": "gallery",
        "meta": {
        "footnotes": ""
        },
        "categories": [
        1
        ],
        "tags": [],
        "class_list": [
        "post-55",
        "post",
        "type-post",
        "status-trash",
        "format-gallery",
        "hentry",
        "category-1",
        "post_format-post-format-gallery"
        ],
        "_links": {
        "self": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/55",
        "targetHints": {
        "allow": [
        "GET",
        "POST",
        "PUT",
        "PATCH",
        "DELETE"
        ]
        }
        }
        ],
        "collection": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts"
        }
        ],
        "about": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/types/post"
        }
        ],
        "author": [
        {
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/users/1"
        }
        ],
        "replies": [
        {
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fcomments&post=55"
        }
        ],
        "version-history": [
        {
        "count": 1,
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/55/revisions"
        }
        ],
        "predecessor-version": [
        {
        "id": 56,
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/55/revisions/56"
        }
        ],
        "wp:attachment": [
        {
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fmedia&parent=55"
        }
        ],
        "wp:term": [
        {
        "taxonomy": "category",
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fcategories&post=55"
        },
        {
        "taxonomy": "post_tag",
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Ftags&post=55"
        }
        ],
        "curies": [
        {
        "name": "wp",
        "href": "https://api.w.org/{rel}",
        "templated": true
        }
        ]
        }
        }

 */