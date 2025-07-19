package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO класс для хранения ответа API при обновлении post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsUpdateResponse {

    private int id;
    private int author;
    private LocalDateTime date;
    private LocalDateTime date_gmt;

    @JsonProperty("content")
    private PostsReadResponse.Content content;

    @JsonProperty("title")
    private PostsReadResponse.Title title;

    @JsonProperty("excerpt")
    private PostsReadResponse.Excerpt excerpt;

    private String status;
    private String comment_status;
    private String ping_status;
    private String slug;
    private LocalDateTime modified;
    private LocalDateTime modified_gmt;

    @JsonProperty("guid")
    private PostsReadResponse.Guid guid;

    private String type;

    // Вложенные классы
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        @JsonProperty("rendered")
        private String rendered;

        @JsonIgnore
        private String raw;
        @JsonIgnore
        private boolean isProtected;
        @JsonIgnore
        private int block_version;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        @JsonProperty("rendered")
        private String rendered;

        @JsonIgnore
        private String raw;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Excerpt {
        @JsonProperty("rendered")
        private String rendered;

        @JsonIgnore
        private String raw;
        @JsonIgnore
        private boolean isProtected;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Guid {
        @JsonProperty("rendered")
        private String rendered;

        @JsonIgnore
        private String raw;
    }
}