package pojo.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения тела ответа API запроса при чтении comment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsReadResponse {

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
     * URL автора
     */
    private String author_url;

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
    private CommentsReadResponse.Content content;

    /**
     * Статус комментария
     */
    private String status;

    /**
     * Тип комментария
     */
    private String type;

    /**
     * Тип комментария
     */
    private Integer author;

    /**
     * Вложенный класс Content
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String rendered;
        @JsonIgnore
        private String raw;
    }
}