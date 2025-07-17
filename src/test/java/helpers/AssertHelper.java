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

    public static void assertUserDeleted(Boolean veriable, Object bdObject) {
        // Проверка статуса удаления в ответе API
        assertEquals(true, veriable,
                "DELETE_STATUS пользователя должен быть true");

        // Проверка что пользователь удален из БД
        assertThat(bdObject)
                .withFailMessage("Пользователь не удалился из БД")
                .isNull();
    }
}
