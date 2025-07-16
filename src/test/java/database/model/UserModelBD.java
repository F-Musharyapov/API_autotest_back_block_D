package database.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Модель сущность user базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class UserModelBD {

    /**
     * ID
     */
    private final int id;
    private final String user_login;
    private final String user_email;
}