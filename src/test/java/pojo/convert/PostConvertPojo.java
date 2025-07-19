package pojo.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.posts.PostsCreateResponse;
import pojo.posts.PostsReadResponse;
import pojo.posts.PostsUpdateResponse;

import java.time.LocalDateTime;

/**
 * Конвертер полей в объекте post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostConvertPojo {

    /**
     * Уникальный идентификатор поста
     */
    private Integer id;

    /**
     * Идентификатор автора поста
     */
    private Integer author;

    /**
     * Дата создания поста в локальном времени
     */
    private LocalDateTime date;

    /**
     * Дата создания поста в GMT
     */
    private LocalDateTime date_gmt;

    /**
     * Контент поста
     */
    private String content;

    /**
     * Заголовок поста
     */
    private String title;

    /**
     * Краткое описание поста
     */
    private String excerpt;

    /**
     * Статус поста (publish, future, draft, pending, private)
     */
    private String status;

    /**
     * Статус комментариев (open, closed)
     */
    private String comment_status;

    /**
     * Статус пингов (open, closed)
     */
    private String ping_status;

    /**
     * ЧПУ URL поста
     */
    private String slug;

    /**
     * Дата последнего изменения поста в локальном времени
     */
    private LocalDateTime modified;

    /**
     * Дата последнего изменения поста в GMT
     */
    private LocalDateTime modified_gmt;

    /**
     * Глобальный уникальный идентификатор поста
     */
    private String guid;

    /**
     * Тип записи
     */
    private String type;

    /**
     * Метод для конвертации объекта PostsCreateResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static PostConvertPojo from(PostsCreateResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(response.getContent() != null ? response.getContent().getRaw() : null)
                .title(response.getTitle() != null ? response.getTitle().getRaw() : null)
                .excerpt(response.getExcerpt() != null ? response.getExcerpt().getRaw() : null)
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(response.getGuid() != null ? response.getGuid().getRaw() : null)
                .type(response.getType())
                .build();
    }

    /**
     * Метод для конвертации объекта PostsReadResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static PostConvertPojo from(PostsReadResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .title(cleanHtml(response.getTitle() != null ? response.getTitle().getRendered() : null))
                .excerpt(cleanHtml(response.getExcerpt() != null ? response.getExcerpt().getRendered() : null))
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(cleanHtml(response.getGuid() != null ? response.getGuid().getRendered() : null))
                .type(response.getType())
                .build();
    }

    /**
     * Метод для конвертации объекта PostsUpdateResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static PostConvertPojo from(PostsUpdateResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .title(cleanHtml(response.getTitle() != null ? response.getTitle().getRendered() : null))
                .excerpt(cleanHtml(response.getExcerpt() != null ? response.getExcerpt().getRendered() : null))
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(cleanHtml(response.getGuid() != null ? response.getGuid().getRendered() : null))
                .type(response.getType())
                .build();
    }

    /**
     * Очистка параметра от тегов и переносов
     *
     * @param html неочищенный текст
     * @return очищенный текст
     */
    private static String cleanHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>|\\n", "") : null;
    }
}