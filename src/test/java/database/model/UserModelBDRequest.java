package database.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель сущности user базы данных
 */
@Data
@Builder
public class UserModelBDRequest {

    /**
     * Уникальный идентифигатор пользователя
     */
    private final String id;

    /**
     * Уникальный идентифигатор пользователя
     */
    private final String user_login;

    /**
     * Пароль
     */
    private final String user_pass;

    /**
     * Буквенно-цифровой идентификатор пользователя nicename
     */
    private final String user_nicename;

    /**
     * Адрес почты
     */
    private final String user_email;

    /**
     * URL пользователя
     */
    private final String user_url;

    /**
     * Дата и время регистрации
     */
    private final LocalDateTime user_registered;

    /**
     * Ключ активации
     */
    private final String user_activation_key;

    /**
     * Статус пользователя
     */
    private final String user_status;

    /**
     * Отображаемое имя
     */
    private final String display_name;

    /**
     * Псевдоним пользователя
     */
    private final String nickname; //псевдоним

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
}


