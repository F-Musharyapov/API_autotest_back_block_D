package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * POJO класс для хранения ответа API при удалении post
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsDeleteResponse {

    private String status;

}
