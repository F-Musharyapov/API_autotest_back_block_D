package database.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущности Comment базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class CommentModelBD {

    /**
     * Уникальный идентификатор комментария
     */
    private final Integer id;

    /**
     * Идентификатор связанного поста
     */
    private final Integer post;

    /**
     * Отображаемое имя автора
     */
    private final String author_name;

    /**
     * Адрес электронной почты автора
     */
    private final String author_email;

    /**
     * URL автора
     */
    private final String author_url;

    /**
     * IP-адрес автора
     */
    private final String author_ip;

    /**
     * Дата публикации комментария в часовом поясе сайта
     */
    private final LocalDateTime date;

    /**
     * Дата публикации комментария по Гринвичу
     */
    private final LocalDateTime date_gmt;

    /**
     * Содержание комментария
     */
    private final String content;

    /**
     * Статус комментария
     */
    private final String status;

    /**
     * Пользовательский агент автора комментария
     */
    private final String author_user_agent;

    /**
     * Тип комментария
     */
    private final String type;

    /**
     * Идентификатор объекта пользователя
     */
    private final Integer author;
}