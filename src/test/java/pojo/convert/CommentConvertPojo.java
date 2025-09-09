package pojo.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsReadResponse;
import pojo.comments.CommentsUpdateResponse;

import java.time.LocalDateTime;

/**
 * Конвертер полей в объекте comment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentConvertPojo {

    /**
     * Уникальный идентификатор комментария
     */
    private Integer id;

    /**
     * Идентификатор связанного поста
     */
    private Integer post;

    /**
     * Отображаемое имя автора
     */
    private String author_name;

    /**
     * Адрес электронной почты автора
     */
    private String author_email;

    /**
     * URL автора
     */
    private String author_url;

    /**
     * IP-адрес автора
     */
    private String author_ip;

    /**
     * Дата публикации комментария в часовом поясе сайта
     */
    private LocalDateTime date;

    /**
     * Дата публикации комментария по Гринвичу
     */
    private LocalDateTime date_gmt;

    /**
     * Содержание комментария
     */
    private String content;

    /**
     * Статус комментария
     */
    private String status;

    /**
     * Пользовательский агент автора комментария
     */
    private String author_user_agent;

    /**
     * Тип комментария
     */
    private String type;

    /**
     * Идентификатор объекта пользователя
     */
    private Integer author;

    /**
     * Метод для конвертации объекта CommentsCreateResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static CommentConvertPojo from(CommentsCreateResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                .author_email(response.getAuthor_email())
                .author_url(response.getAuthor_url())
                .author_ip(response.getAuthor_ip())
                .author_user_agent(response.getAuthor_user_agent())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRaw() : null))
                .status(convertStatus(response.getStatus()))
                .type(response.getType())
                .build();
    }

    /**
     * Метод для конвертации объекта CommentsReadResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static CommentConvertPojo from(CommentsReadResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                .author_url(response.getAuthor_url())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .status(convertStatus(response.getStatus()))
                .type(response.getType())
                .build();
    }

    /**
     * Метод для конвертации объекта CommentsUpdateResponse
     *
     * @param response параметр ответа API
     * @return сконвертированный объект
     */
    public static CommentConvertPojo from(CommentsUpdateResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                .author_email(response.getAuthor_email())
                .author_url(response.getAuthor_url())
                .author_ip(response.getAuthor_ip())
                .author_user_agent(response.getAuthor_user_agent())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRaw() : null))
                .status(convertStatus(response.getStatus()))
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

    /**
     * Конвертор статуса из буквенного в числовой
     *
     * @param stringStatus буквенный статус
     * @return числовой статус
     */
    private static String convertStatus(String stringStatus) {
        if (stringStatus == null) return null;
        return switch (stringStatus) {
            case "approved" -> "1";
            //case "2" -> "rejected";
            //case "0" -> "trash";
            default -> stringStatus;
        };
    }
}
