package config;

import org.aeonbits.owner.Config;

/**
 * Интерфейс с основной конфигурацией проекта
 */
@Config.Sources({"classpath:config.properties"})
public interface BaseConfig extends Config {

    /**
     * Метод для возвращения значения параметра адрес апи запрсоов baseUrl из config.properties
     *
     * @return API URL
     */
    String apiUrl();

    /**
     * Метод для возвращения значения параметра usernameAuth из config.properties
     *
     * @return usernameAuth
     */
    String usernameAuth();

    /**
     * Метод для возвращения значения параметра passwordAuth из config.properties
     *
     * @return passwordAuth
     */
    String passwordAuth();

    /**
     * Метод для возвращения значения параметра адрес драйвера из config.properties
     *
     * @return адрес драйвера
     */
    String driverDb();

    /**
     * Метод для возвращения значения параметра адрес базы данных urlDb из config.properties
     *
     * @return адрес базы данных
     */
    String urlDb();

    /**
     * Пользователь базы данных
     *
     * @return пользователь
     */
    String userDb();

    /**
     * Пароль базы данных
     *
     * @return пароль
     */
    String passwordDb();

    /**
     * Эндпоинт создания юзера
     *
     * @return эндпоинт
     */
    String createUserEndpoint();

    /**
     * Эндпоинт чтения юзера
     *
     * @return эндпоинт
     */
    String readUserEndpoint();

    /**
     * Эндпоинт обновления юзера
     *
     * @return эндпоинт
     */
    String updateUserEndpoint();

    /**
     * Эндпоинт удаления юзера
     *
     * @return эндпоинт
     */
    String deleteUserEndpoint();

    /**
     * Эндпоинт создания Post
     *
     * @return эндпоинт
     */
    String createPostEndpoint();

    /**
     * Эндпоинт чтения Post
     *
     * @return эндпоинт
     */
    String readPostEndpoint();

    /**
     * Эндпоинт обновления Post
     *
     * @return эндпоинт
     */
    String updatePostEndpoint();

    /**
     * Эндпоинт удаления Post
     *
     * @return эндпоинт
     */
    String deletePostEndpoint();

    /**
     * Эндпоинт создания Comment
     *
     * @return эндпоинт
     */
    String createCommentEndpoint();

    /**
     * Эндпоинт чтения Comment
     *
     * @return эндпоинт
     */
    String readCommentEndpoint();

    /**
     * Эндпоинт обновления Comment
     *
     * @return эндпоинт
     */
    String updateCommentEndpoint();

    /**
     * Эндпоинт удаления Comment
     *
     * @return эндпоинт
     */
    String deleteCommentEndpoint();
}