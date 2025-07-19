package pojo.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsReadResponse {

    private Integer id;
    private Integer post;
    private String author_name;
    //private String author_email;
    private String author_url;
    //private String author_ip;
    private LocalDateTime date;
    private LocalDateTime date_gmt;
    @JsonProperty("content")
    private CommentsReadResponse.Content content;
    private String status;
    //private String author_user_agent;
    private String type;
    private Integer author;

    // Вложенные классы
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        @JsonProperty("rendered")
        private String rendered;

        @JsonIgnore
        private String raw;
    }
}