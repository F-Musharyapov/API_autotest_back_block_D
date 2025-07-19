package helpers;

import com.github.javafaker.Faker;

import java.util.Random;

/**
 * Класс с константами
 */
public class TestDataHelper {

    /**
     * Экземпляр для Faker
     */
    private static final Faker faker = new Faker();

    /**
     * Экземпляр для Random
     */
    private static final Random random = new Random();

    /**
     * Идентификаторы
     */
    public static final String EMAIL_USER = "[a-z]{10}\\@[a-z]{5}\\.[a-z]{2}";
    public static final String SLUG_USER = "[a-z]{8}-[a-z]{6}-[a-z]{4}";
    public static final int REASSIGN_USER = 1;
    public static final boolean FORCE_USER = true;
    public static final int AUTHOR_ID = 1;
    public static final int POST_ID = 1;
    public static final int AUTHOR_ID_UPDATE = 3;
    public static final int POST_ID_UPDATE = 6;
    public static final String SLUG_POST = "[a-z]{8}-[a-z]{6}-[a-z]{4}-[a-z]{2}-[a-z]{4}-[a-z]{8}";
    public static final String[] STATUS_POST = {"publish", "future", "draft", "pending", "private"};
    public static final String TITLE_POST = "[a-z]{8} [a-z]{6} [a-z]{4}";
    public static final int AUTHOR_POST = 1;
    public static final String[] COMMENT_STATUS_POST = {"open", "closed"};
    public static final String[] PING_STATUS_POST = {"open", "closed"};
    public static final String[] FORMAT_POST = {"standard", "aside", "chat", "gallery", "link", "image", "quote", "status", "video", "audio"};
    public static final Boolean[] STICKY_POST = {true, false};

    /**
     * Статусы выполнения запросов
     */
    public static final int STATUS_CODE_CREATED = 201;
    public static final int STATUS_CODE_OK = 200;

    /**
     * Методы генерации случайных данных для user
     *
     * @return сгенерированные данные
     */
    public static String getUserRandomEmail() {
        return faker.regexify(EMAIL_USER);
    }
    public static String getUserRandomUserName() {
        return faker.name().username();
    }
    public static String getUserRandomDisplayName() {
        return faker.name().nameWithMiddle();
    }
    public static String getUserRandomFirstName() {
        return faker.name().lastName();
    }
    public static String getUserRandomLastname() {
        return faker.name().firstName();
    }
    public static String getUserRandomUrl() {
        return faker.internet().url();
    }
    public static String getUserRandomDescription() {
        return faker.lorem().sentence(2, 4);
    }
    public static String getUserRandomNickName() {
        return faker.funnyName().name();
    }
    public static String getUserRandomSlug() {
        return faker.regexify(SLUG_USER);
    }
    public static String getUserRandomPassword() {
        return faker.internet().password();
    }

    /**
     * Методы генерации случайных данных для post
     *
     * @return сгенерированные данные
     */
    public static String getPostRandomSlug() {
        return faker.regexify(SLUG_POST);
    }
    public static String getPostRandomStatus() {
        return STATUS_POST[random.nextInt(STATUS_POST.length)];
    }
    public static String getPostRandomTitle() {
        return faker.regexify(TITLE_POST);
    }
    public static String getPostRandomContent() {
        return faker.lorem().sentence(20, 30);
    }
    public static String getPostRandomExcerpt() {
        return faker.lorem().sentence(10, 20);
    }
    public static String getPostRandomCommentStatus() {
        return COMMENT_STATUS_POST[random.nextInt(COMMENT_STATUS_POST.length)];
    }
    public static String getPostRandomPingStatus() {
        return PING_STATUS_POST[random.nextInt(PING_STATUS_POST.length)];
    }
    public static String getPostRandomFormat() {
        return FORMAT_POST[random.nextInt(FORMAT_POST.length)];
    }
    public static Boolean getPostRandomSticky() {
        return STICKY_POST[random.nextInt(STICKY_POST.length)];
    }

    /**
     * Методы генерации случайных данных для post
     *
     * @return сгенерированные данные
     */
    public static String getCommentRandomContent() {
        return faker.lorem().sentence(20, 30);
    }


}