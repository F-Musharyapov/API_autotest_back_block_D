package pojo.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.comments.CommentsCreateResponse;
import pojo.comments.CommentsReadResponse;
import pojo.comments.CommentsUpdateResponse;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentConvertPojo {

    private Integer id;
    private Integer post;
    private String author_name;
    private String author_email;
    private String author_url;
    private String author_ip;
    private LocalDateTime date;
    private LocalDateTime date_gmt;
    private String content;
    private String status;
    private String author_user_agent;
    private String type;
    private Integer author;

    // Метод для конвертации из CommentsCreateResponse
    public static CommentConvertPojo from(CommentsCreateResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                .author_email(response.getAuthor_email())
                .author_url(response.getAuthor_url())
                .author_ip(response.getAuthor_ip())
                .author_user_agent(response.getAuthor_user_agent())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRaw() : null))
                .status(convertStatus(response.getStatus())) // Конвертим "approved" → "1"
                .type(response.getType())
                .build();
    }

    // Метод для конвертации из CommentsReadResponse
    public static CommentConvertPojo from(CommentsReadResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                //.author_email(response.getAuthor_email())
                .author_url(response.getAuthor_url())
                //.author_ip(response.getAuthor_ip())
                //.author_user_agent(response.getAuthor_user_agent())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .status(convertStatus(response.getStatus())) // Конвертим "approved" → "1"
                .type(response.getType())
                .build();
    }

    // Метод для конвертации из CommentsUpdateResponse
    public static CommentConvertPojo from(CommentsUpdateResponse response) {
        return CommentConvertPojo.builder()
                .id(response.getId())
                .post(response.getPost())
                .author(response.getAuthor())
                .author_name(response.getAuthor_name())
                .author_email(response.getAuthor_email())
                .author_url(response.getAuthor_url())
                .author_ip(response.getAuthor_ip())
                .author_user_agent(response.getAuthor_user_agent())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRaw() : null))
                .status(convertStatus(response.getStatus())) // Конвертим "approved" → "1"
                .type(response.getType())
                .build();
    }

    private static String cleanHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>|\\n", "") : null;
    }

    private static String convertStatus(String stringStatus) {
        if (stringStatus == null) return null;
        return switch (stringStatus) {
            case "approved" -> "1";
            //case "2" -> "rejected";
            //case "0" -> "trash";
            default -> stringStatus;
        };
    }
}
