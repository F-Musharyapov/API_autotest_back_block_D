package pojo.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения данных API запроса при редактировании comment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsUpdateRequest {

    /**
     * Идентификатор связанного поста
     */
    private Integer post;

    /**
     * Содержание комментария
     */
    private String content;

    /**
     * Идентификатор объекта пользователя
     */
    private Integer author;

}
