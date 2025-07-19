package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения ответа API при создании post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsCreateResponse {

    /**
     * Уникальный идентификатор поста
     */
    private int id;

    /**
     * Идентификатор автора поста
     */
    private int author;

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
    private Content content;

    /**
     * Заголовок поста
     */
    private Title title;

    /**
     * Краткое описание поста
     */
    private Excerpt excerpt;

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
    private Guid guid;

    /**
     * Тип записи
     */
    private String type;

    /**
     * Вложенный класс Content
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String raw;
        @JsonIgnore
        private String rendered;
        @JsonIgnore
        private boolean isProtected;
        @JsonIgnore
        private int block_version;
    }

    /**
     * Вложенный класс Title
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        private String raw;
        @JsonIgnore
        private String rendered;
    }

    /**
     * Вложенный класс Excerpt
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Excerpt {
        private String raw;
        @JsonIgnore
        private String rendered;
        @JsonIgnore
        private boolean isProtected;
    }

    /**
     * Вложенный класс Guid
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Guid {
        private String raw;
        @JsonIgnore
        private String rendered;
    }
}