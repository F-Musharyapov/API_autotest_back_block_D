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
     * Сравнение объектов по указанным полям
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
                "DELETE_STATUS пользователя должен быть true");
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
}
