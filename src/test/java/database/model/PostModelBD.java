package database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущность post базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class PostModelBD {

    /**
     * Переменные для хранения данных из БД
     */
    private final Integer id;
    private final Integer author;
    private final LocalDateTime date;
    private final LocalDateTime date_gmt;
    private final String content; //raw
    private final String title; //raw
    private final String excerpt; //raw
    private final String status;
    private final String comment_status;
    private final String ping_status;
    //private final String password;
    private final String slug;
    private final LocalDateTime modified;
    private final LocalDateTime modified_gmt;
    private final String guid; //ссылка raw
    private final String type;

}
