package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.BaseConfig;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import static io.restassured.RestAssured.preemptive;


/**
 * Базовый тестовый класс с общими настройками для запросов REST API
 */
public class BaseRequests {

    /**
     * Экземпляр интерфейса с конфигурацией
     */
    private static final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    static {
        // Настройка ObjectMapper для поддержки Java 8 Time API
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> {
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.registerModule(new JavaTimeModule());
                            return mapper;
                        }));
    }

    /**
     * Метод для получения спецификации RestAssured
     *
     * @return спецификация
     * @throws IOException
     */
    @Description("Дефолтный метод для получения спецификации RestAssured")
    public static RequestSpecification initRequestSpecification() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setBaseUri(config.apiUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(preemptive().basic(config.usernameAuth(), config.passwordAuth()));
        ;
        RequestSpecification requestSpecification;
        return requestSpecification = requestSpecBuilder.build();
    }
}