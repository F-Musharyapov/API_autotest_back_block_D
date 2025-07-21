package database.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущности post базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class PostModelBD {

    /**
     * Уникальный идентификатор поста
     */
    private final Integer id;

    /**
     * Идентификатор автора поста
     */
    private final Integer author;

    /**
     * Дата создания поста в локальном времени
     */
    private final LocalDateTime date;

    /**
     * Дата создания поста в GMT
     */
    private final LocalDateTime date_gmt;

    /**
     * Контент поста
     */
    private final String content;

    /**
     * Заголовок поста
     */
    private final String title;

    /**
     * Краткое описание поста
     */
    private final String excerpt;

    /**
     * Статус поста (publish, future, draft, pending, private)
     */
    private final String status;

    /**
     * Статус комментариев (open, closed)
     */
    private final String comment_status;

    /**
     * Статус пингов (open, closed)
     */
    private final String ping_status;

    /**
     * ЧПУ URL поста
     */
    private final String slug;

    /**
     * Дата последнего изменения поста в локальном времени
     */
    private final LocalDateTime modified;

    /**
     * Дата последнего изменения поста в GMT
     */
    private final LocalDateTime modified_gmt;

    /**
     * Глобальный уникальный идентификатор поста
     */
    private final String guid;

    /**
     * Тип записи
     */
    private final String type;
}
