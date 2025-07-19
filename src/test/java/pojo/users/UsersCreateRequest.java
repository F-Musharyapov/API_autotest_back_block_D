package pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersCreateRequest {

    private String username;
    private String name; //Displayname
    private String first_name;
    private String last_name;
    private String email;
    private String url;
    private String description;
    private String nickname;
    private String password;
    private String slug;
}

// Тело запроса апи PostsCreateRequest(slug=rkzrokzc-szzvhn-tapn-th-ribv-hbxuqulq, status=future, title=fqhofsoq llojid nevy, content=Officia ut molestiae ut debitis adipisci corporis ea quo facere amet distinctio amet quos voluptates est qui minus unde reprehenderit sequi repellat et quod ut exercitationem architecto id., author=1, excerpt=Commodi perspiciatis officiis voluptatum illo fugit labore vel nisi ut vitae., comment_status=closed, ping_status=closed, format=video, sticky=true)
// Тело ответа апи PostsCreateResponse(id=36, author=1, date=2025-07-18T16:50:25, date_gmt=2025-07-18T13:50:25, content=PostsCreateResponse.Content(raw=Officia ut molestiae ut debitis adipisci corporis ea quo facere amet distinctio amet quos voluptates est qui minus unde reprehenderit sequi repellat et quod ut exercitationem architecto id., rendered=<p>Officia ut molestiae ut debitis adipisci corporis ea quo facere amet distinctio amet quos voluptates est qui minus unde reprehenderit sequi repellat et quod ut exercitationem architecto id.</p>, isProtected=false, block_version=0), title=PostsCreateResponse.Title(raw=fqhofsoq llojid nevy, rendered=fqhofsoq llojid nevy), excerpt=PostsCreateResponse.Excerpt(raw=Commodi perspiciatis officiis voluptatum illo fugit labore vel nisi ut vitae., rendered=<p>Commodi perspiciatis officiis voluptatum illo fugit labore vel nisi ut vitae.</p>, isProtected=false), status=publish, comment_status=closed, ping_status=closed, password=, slug=rkzrokzc-szzvhn-tapn-th-ribv-hbxuqulq, modified=2025-07-18T16:50:25, modified_gmt=2025-07-18T13:50:25, guid=PostsCreateResponse.Guid(raw=http://localhost:8000/?p=36, rendered=http://localhost:8000/?p=36), type=post)
// Тело запроса бд PostModelBD        (id=36, author=1, date=2025-07-18T16:50:25, date_gmt=2025-07-18T13:50:25,                                 content=Officia ut molestiae ut debitis adipisci corporis ea quo facere amet distinctio amet quos voluptates est qui minus unde reprehenderit sequi repellat et quod ut exercitationem architecto id., title=fqhofsoq llojid nevy, excerpt=Commodi perspiciatis officiis voluptatum illo fugit labore vel nisi ut vitae., status=publish, comment_status=closed, ping_status=closed, password=null, slug=rkzrokzc-szzvhn-tapn-th-ribv-hbxuqulq, modified=2025-07-18T16:50:25, modified_gmt=2025-07-18T13:50:25, guid=http://localhost:8000/?p=36, type=post)> Task :test
//BUILD SUCCESSFUL in 25s
