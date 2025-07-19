package pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * POJO класс для хранения ответа тела API запроса при удалении user
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDeleteResponse {

    /**
     * Статус удаления
     */
    private Boolean deleted;

}
