package pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * POJO класс для хранения тела ответа API запроса при чтении user
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersReadResponse {

    /**
     * Уникальный идентифигатор пользователя
     */
    private String id;

    /**
     * Отображаемое имя Displayname
     */
    private String name;

    /**
     * URL пользователя
     */
    private String url;

    /**
     * Описание пользователя
     */
    private String description;

    /**
     * Буквенно-цифровой идентификатор пользователя
     */
    private String slug;

}