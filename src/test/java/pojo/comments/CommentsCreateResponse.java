package pojo.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения тела ответа API запроса при создании comment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsCreateResponse {

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
    private CommentsCreateResponse.Content content;

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
     * Вложенный класс Content
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String raw;
        @JsonIgnore
        private String rendered;
    }
}
