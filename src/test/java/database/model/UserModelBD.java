package database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущности user базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id","password"})
public class UserModelBD {

    /**
     * Уникальный идентифигатор пользователя
     */
    private final String id;

    private final String password;

    /**
     * Логин
     */
    @JsonProperty("user_login")
    private final String username;

    /**
     * Буквенно-цифровой идентификатор пользователя
     */
    @JsonProperty("user_nicename")
    private final String slug;

    /**
     * Адрес почты
     */
    @JsonProperty("user_email")
    private final String email;

    /**
     * Псевдоним пользователя
     */
    private final String nickname;

    /**
     * Имя пользователя
     */
    private final String first_name;

    /**
     * Фамилия пользователя
     */
    private final String last_name;

    /**
     * Описание пользователя
     */
    private final String description;

    /**
     * Дата регистрации
     */
    @JsonProperty("user_registered")
    private final LocalDateTime registered_date;

    /**
     * Отображаемое имя
     */
    @JsonProperty("display_name")
    private final String name;

    /**
     * URL пользователя
     */
    @JsonProperty("user_url")
    private final String url;
}

