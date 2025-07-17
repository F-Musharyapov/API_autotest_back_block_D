package database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущность user базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class UserModelBD {

    /**
     * Переменные для хранения данных из БД
     */
    private final String id;//
    @JsonProperty("user_login")
    private final String username; //username
    @JsonProperty("user_nicename")
    private final String slug; //slug
    @JsonProperty("user_email")
    private final String email;//
    private final String nickname;//
    private final String first_name;//
    private final String last_name;//
    private final String description;//
    @JsonProperty("user_registered")
    private final LocalDateTime registered_date;//
    @JsonProperty("display_name")
    private final String name; //name
    @JsonProperty("user_url")
    private final String url;//

}

