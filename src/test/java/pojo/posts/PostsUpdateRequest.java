package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения запроса API при обновлении post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsUpdateRequest {

    /**
     * ЧПУ URL поста
     */
    private String slug;

    /**
     * Статус поста (publish, future, draft, pending, private)
     */
    private String status;

    /**
     * Заголовок поста
     */
    private String title;

    /**
     * Контент поста
     */
    private String content;

    /**
     * Краткое описание поста
     */
    private String excerpt;

    /**
     * Статус комментариев (open, closed)
     */
    private String comment_status;

    /**
     * Статус пингов (open, closed)
     */
    private String ping_status;

    /**
     * Формат поста (standard, aside, chat, gallery, link, image, quote, status, video, audio)
     */
    private String format;

    /**
     * Закрепленность поста
     */
    private Boolean sticky;

}

