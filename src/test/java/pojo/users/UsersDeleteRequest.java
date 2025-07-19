package pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения API запроса при удалении user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersDeleteRequest {

    /**
     * Переназначить сообщения и ссылки удаленного пользователя этому идентификатору пользователя.
     */
    private int reassign;

    /**
     * Обязательно должно быть true, так как пользователи не поддерживают удаление.
     */
    private boolean force;

}