package pojo.convert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.posts.PostsCreateResponse;
import pojo.posts.PostsReadResponse;
import pojo.posts.PostsUpdateResponse;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostConvertPojo {
    private Integer id;
    private Integer author;
    private LocalDateTime date;
    private LocalDateTime date_gmt;
    private String content;
    private String title;
    private String excerpt;
    private String status;
    private String comment_status;
    private String ping_status;
    private String slug;
    private LocalDateTime modified;
    private LocalDateTime modified_gmt;
    private String guid;
    private String type;

    // Метод для конвертации из PostsCreateResponse
    public static PostConvertPojo from(PostsCreateResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(response.getContent() != null ? response.getContent().getRaw() : null)
                .title(response.getTitle() != null ? response.getTitle().getRaw() : null)
                .excerpt(response.getExcerpt() != null ? response.getExcerpt().getRaw() : null)
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(response.getGuid() != null ? response.getGuid().getRaw() : null)
                .type(response.getType())
                .build();
    }

    // Метод для конвертации из PostsReadResponse
    public static PostConvertPojo from(PostsReadResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .title(cleanHtml(response.getTitle() != null ? response.getTitle().getRendered() : null))
                .excerpt(cleanHtml(response.getExcerpt() != null ? response.getExcerpt().getRendered() : null))
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(cleanHtml(response.getGuid() != null ? response.getGuid().getRendered() : null))
                .type(response.getType())
                .build();
    }

    // Метод для конвертации из PostsReadResponse
    public static PostConvertPojo from(PostsUpdateResponse response) {
        return PostConvertPojo.builder()
                .id(response.getId())
                .author(response.getAuthor())
                .date(response.getDate())
                .date_gmt(response.getDate_gmt())
                .content(cleanHtml(response.getContent() != null ? response.getContent().getRendered() : null))
                .title(cleanHtml(response.getTitle() != null ? response.getTitle().getRendered() : null))
                .excerpt(cleanHtml(response.getExcerpt() != null ? response.getExcerpt().getRendered() : null))
                .status(response.getStatus())
                .comment_status(response.getComment_status())
                .ping_status(response.getPing_status())
                .slug(response.getSlug())
                .modified(response.getModified())
                .modified_gmt(response.getModified_gmt())
                .guid(cleanHtml(response.getGuid() != null ? response.getGuid().getRendered() : null))
                .type(response.getType())
                .build();
    }

    private static String cleanHtml(String html) {
        return html != null ? html.replaceAll("<[^>]*>|\\n", "") : null;
    }
}