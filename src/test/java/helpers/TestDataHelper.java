package helpers;

import com.github.javafaker.Faker;

/**
 * Класс с константами
 */
public class TestDataHelper {

    private static final Faker faker = new Faker();
    /**
     * Константы запросов UserRequest
     */

    public static final String REQUEST_USER_CREATE_POST = "index.php?rest_route=/wp/v2/users"; //
    public static final String REQUEST_USER_GET = "index.php?rest_route=/wp/v2/users/"; // чтение конкретного юзера <id>
    public static final String REQUEST_USER_LIST_GET = "index.php?rest_route=wp/v2/users"; // чтение списка юзеров
    public static final String REQUEST_USER_UPDATE_POST = "index.php?rest_route=/wp/v2/users/"; // обновление данных
    public static final String REQUEST_USER_DELETE = "index.php?rest_route=/wp/v2/users/"; // удаление пользователя


    public static final String EMAIL = "[a-z]{10}\\@[a-z]{5}\\.[a-z]{2}";
    public static final int REASSIGN = 1;
    public static final boolean FORCE = true;

    public static final int STATUS_CODE_CREATED = 201;

    /**
     * Статус код успешного выполнения запроса GET
     */
    public static final int STATUS_CODE_OK = 200;


    /**
     * Идентификатор verified
     */
    public static final boolean DELETE_STATUS = true;


    /**
     * Метода генерации случайного email
     *
     * @return случайный email
     */
    public static String getRandomEmail() {
        return faker.regexify(EMAIL);
    }

    /**
     * Метода генерации случайного имени
     *
     * @return случайное имя
     */
    public static String getRandomUserName() {
        return faker.name().username();
    }
    
    /**
     * Метода генерации случайного пароля
     *
     * @return случайный пароль
     */
    public static String getRandomPassword() {
        return faker.internet().password();
    }

}