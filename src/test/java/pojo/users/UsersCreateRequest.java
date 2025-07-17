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