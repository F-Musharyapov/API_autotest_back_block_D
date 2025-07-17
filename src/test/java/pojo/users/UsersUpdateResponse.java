package pojo.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersUpdateResponse {

    private String id;
    private String username;
    private String slug;
    private String email;
    private String nickname;
    private String first_name;
    private String last_name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime registered_date;
    private String name; //Displayname
    private String url;

}