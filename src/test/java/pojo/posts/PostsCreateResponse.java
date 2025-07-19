package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения ответа API при создании post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsCreateResponse {
    private int id;
    private int author;
    private LocalDateTime date;
    private LocalDateTime date_gmt;
    private Content content;
    private Title title;
    private Excerpt excerpt;
    private String status;
    private String comment_status;
    private String ping_status;
    private String slug;
    private LocalDateTime modified;
    private LocalDateTime modified_gmt;
    private Guid guid;
    private String type;

    // Вложенные классы
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String raw;
        @JsonIgnore
        private String rendered;
        @JsonIgnore
        private boolean isProtected;
        @JsonIgnore
        private int block_version;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        private String raw;
        @JsonIgnore
        private String rendered;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Excerpt {
        private String raw;
        @JsonIgnore
        private String rendered;
        @JsonIgnore
        private boolean isProtected;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Guid {
        private String raw;
        @JsonIgnore
        private String rendered;
    }
}