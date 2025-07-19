package database.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Модель сущность Comment базы данных
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class CommentModelBD {

    private final Integer id;
    private final Integer post;
    private final String author_name;
    private final String author_email;
    private final String author_url;
    private final String author_ip;
    private final LocalDateTime date;
    private final LocalDateTime date_gmt;
    private final String content;
    private final String status;
    private final String author_user_agent;
    private final String type;
    private final Integer author;

}

/*

comment_ID
comment_post_ID
comment_author
comment_author_email
comment_author_url
comment_author_IP
comment_date
comment_date_gmt
comment_content
comment_karma
comment_approved
comment_agent
comment_type
comment_parent
user_id

 */