package pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения данных API запроса при создании user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersUpdateRequest {

    /**
     * Отображаемое имя
     */
    private String name;

    /**
     * Имя пользователя
     */
    private String first_name;

    /**
     * Фамилия пользователя
     */
    private String last_name;

    /**
     * Адрес почты
     */
    private String email;

    /**
     * URL пользователя
     */
    private String url;

    /**
     * Описание пользователя
     */
    private String description;

    /**
     * Псевдоним пользователя
     */
    private String nickname;

    /**
     * Буквенно-цифровой идентификатор пользователя
     */
    private String slug;

    /**
     * Пароль пользователя
     */
    private String password;

}