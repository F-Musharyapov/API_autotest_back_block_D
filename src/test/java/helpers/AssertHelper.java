package helpers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertHelper {

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

}
