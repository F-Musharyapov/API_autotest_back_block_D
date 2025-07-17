package helpers;

import com.github.javafaker.Faker;

/**
 * Класс с константами
 */
public class TestDataHelper {

    /**
     * Экземпляр для Faker
     */
    private static final Faker faker = new Faker();

    /**
     * Идентификаторы
     */
    public static final String EMAIL = "[a-z]{10}\\@[a-z]{5}\\.[a-z]{2}";
    public static final String SLUG = "[a-z]{8}-[a-z]{6}-[a-z]{4}";
    public static final int REASSIGN = 1;
    public static final boolean FORCE = true;
    public static final boolean DELETE_STATUS = true;

    /**
     * Статусы выполнения запросов
     */
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_OK = 200;

    /**
     * Методы генерации случайных данных
     *
     * @return сгенерированный
     */
    public static String getRandomEmail() {
        return faker.regexify(EMAIL);
    }
    public static String getRandomUserName() {
        return faker.name().username();
    }
    public static String getRandomDisplayName() {
        return faker.name().nameWithMiddle();
    }
    public static String getRandomFirstName() {
        return faker.name().lastName();
    }
    public static String getRandomLastname() {
        return faker.name().firstName();
    }
    public static String getRandomUrl() {
        return faker.internet().url();
    }
    public static String getRandomDescription() {
        return faker.lorem().sentence(2, 4);
    }
    public static String getRandomNickName() {
        return faker.funnyName().name();
    }
    public static String getRandomSlug() {
        return faker.regexify(SLUG);
    }
    public static String getRandomPassword() {
        return faker.internet().password();
    }

}