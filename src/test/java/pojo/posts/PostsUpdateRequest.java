package pojo.posts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO класс для хранения запроса API при обновлении post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsUpdateRequest {

    private String slug;
    private String status; // publish, future, draft, pending,private
    private String title;
    private String content;
    //private int author;
    private String excerpt; // отрывок
    private String comment_status; // open,closed
    private String ping_status; // open,closed
    private String format; // standard, aside, chat, gallery, link, image, quote, status, video,audio
    private Boolean sticky;

}

