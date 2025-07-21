package helpers;

import com.fasterxml.jackson.annotation.JsonFormat;
import database.model.UserModelBD;
import org.assertj.core.api.Assertions;
import pojo.users.UsersCreateResponse;
import pojo.users.UsersReadResponse;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Набор Assert
 */
public class AssertHelper extends Assertions {

    /**
     * Сравнение двух объектов по полям
     * @param expected ожидаемый объект
     * @param actual фактический объект
     */
    public static void assertObjectsEqual(Object expected, Object actual) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**
     * Сравнение объектов User по указанным полям
     * @param expected ожидаемый объект
     * @param actual фактический объект
     */
    public static void assertUserFieldsEqual(Object expected, Object actual) {
        assertThat(expected)
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields(
                        "id", "name", "url", "description", "slug"
                )
                .isEqualTo(actual);
    }

    /**
     * Проверка статуса удаления user
     * @param veriable статус удаления
     */
    public static void assertStatusUserDeleted(Boolean veriable) {
        assertEquals(true, veriable,
                "Статус пользователя должен быть true");
    }

    /**
     * Проверка наличия user в БД
     * @param bdObject ответ наличия user в БД
     */
    public static void assertUserDeletedBD(Object bdObject) {
        assertThat(bdObject)
                .withFailMessage("Пользователь не удалился из БД")
                .isNull();
    }

    /**
     * Проверка статуса удаления post
     * @param status статус удаления
     */
    public static void assertStatusPostDeleted(String status) {
        assertEquals("trash", status,
                "Статус должен быть 'trash'");
    }

    /**
     * Проверка статуса удаления comment
     * @param status статус удаления
     */
    public static void assertStatusCommentDeleted(String status) {
        assertEquals("trash", status,
                "Статус должен быть 'trash'");
    }

    /**
     * Сравнение объектов comment по указанным полям
     * @param expected ожидаемый объект
     * @param actual фактический объект
     */
    public static void assertCommentFieldsEqual(Object expected, Object actual) {
        assertThat(expected)
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields(
                        "author_email", "author_ip", "author_user_agent"
                )
                .isEqualTo(actual);
    }


    //UsersCreateResponse
    public static void assertUserCreateFieldsEqual(UserModelBD expected, UsersCreateResponse actual) {
        if (!Objects.equals(expected.getId(), actual.getId())) {
            throw new AssertionError("Id не совпадает: expected= " + expected.getEmail() + ", actual= " + actual.getId());
        }
        if (!Objects.equals(expected.getUsername(), actual.getUsername())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getUsername() + ", actual= " + actual.getUsername());
        }
        if (!Objects.equals(expected.getSlug(), actual.getSlug())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getSlug() + ", actual= " + actual.getSlug());
        }
        if (!Objects.equals(expected.getEmail(), actual.getEmail())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getEmail() + ", actual= " + actual.getEmail());
        }
        if (!Objects.equals(expected.getNickname(), actual.getNickname())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getNickname() + ", actual= " + actual.getNickname());
        }
        if (!Objects.equals(expected.getFirst_name(), actual.getFirst_name())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getFirst_name() + ", actual= " + actual.getFirst_name());
        }
        if (!Objects.equals(expected.getLast_name(), actual.getLast_name())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getLast_name() + ", actual= " + actual.getLast_name());
        }
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getDescription() + ", actual= " + actual.getDescription());
        }
        if (!Objects.equals(expected.getRegistered_date(), actual.getRegistered_date())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getRegistered_date() + ", actual= " + actual.getRegistered_date());
        }
        if (!Objects.equals(expected.getName(), actual.getName())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getName() + ", actual= " + actual.getName());
        }
        if (!Objects.equals(expected.getUrl(), actual.getUrl())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getUrl() + ", actual= " + actual.getUrl());
        }
    }

    //UsersReadResponse
    public static void assertUserReadFieldsEqual(UserModelBD expected, UsersReadResponse actual) {
        if (!Objects.equals(expected.getId(), actual.getId())) {
            throw new AssertionError("Id не совпадает: expected= " + expected.getEmail() + ", actual= " + actual.getId());
        }
        if (!Objects.equals(expected.getSlug(), actual.getSlug())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getSlug() + ", actual= " + actual.getSlug());
        }
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getDescription() + ", actual= " + actual.getDescription());
        }
        if (!Objects.equals(expected.getName(), actual.getName())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getName() + ", actual= " + actual.getName());
        }
        if (!Objects.equals(expected.getUrl(), actual.getUrl())) {
            throw new AssertionError("Username не совпадает: expected= " + expected.getUrl() + ", actual= " + actual.getUrl());
        }
    }
}
