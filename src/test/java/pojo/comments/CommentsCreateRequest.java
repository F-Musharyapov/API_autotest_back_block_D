package pojo.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения данных API запроса при создании comment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsCreateRequest {

    /**
     * Идентификатор объекта пользователя
     */
    private int author;

    /**
     * Содержание комментария
     */
    private String content;

    /**
     * Идентификатор связанного поста
     */
    private int post;

}