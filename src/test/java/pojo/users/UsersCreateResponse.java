package pojo.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения ответа тела API запроса при создании user
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersCreateResponse {

    /**
     * Уникальный идентифигатор пользователя
     */
    private String id;

    /**
     * Логин
     */
    private String username;

    /**
     * Буквенно-цифровой идентификатор пользователя
     */
    private String slug;

    /**
     * Адрес почты
     */
    private String email;

    /**
     * Псевдоним пользователя
     */
    private String nickname;

    /**
     * Имя пользователя
     */
    private String first_name;

    /**
     * Фамилия пользователя
     */
    private String last_name;

    /**
     * Описание пользователя
     */
    private String description;

    /**
     * Дата регистрации
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime registered_date;

    /**
     * Отображаемое имя
     */
    private String name;

    /**
     * URL пользователя
     */
    private String url;

}

