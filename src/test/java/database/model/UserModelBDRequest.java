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
//@EqualsAndHashCode(exclude = {"id","password"})
public class UserModelBDRequest {

    /**
     * Уникальный идентифигатор пользователя
     */
    private final String id;
    private final String user_login;
    private final String user_pass;
    private final String user_nicename;
    private final String user_email;
    private final String user_url;
    private final LocalDateTime user_registered;
    private final String user_activation_key;
    private final String user_status;
    private final String display_name;

    private final String nickname; //псевдоним
    private final String first_name;
    private final String last_name;
    private final String description;

}

/*


 */

