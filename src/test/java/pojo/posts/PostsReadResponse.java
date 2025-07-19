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
 * POJO класс для хранения ответа API при чтении post
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostsReadResponse {
    private int id;
    private int author;
    private LocalDateTime date;
    private LocalDateTime date_gmt;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("title")
    private Title title;

    @JsonProperty("excerpt")
    private Excerpt excerpt;

    private String status;
    private String comment_status;
    private String ping_status;
    private String slug;
    private LocalDateTime modified;
    private LocalDateTime modified_gmt;

    @JsonProperty("guid")
    private Guid guid;

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
/*
{
        "id": 26,
        "date": "2025-07-17T19:21:47",
        "date_gmt": "2025-07-17T16:21:47",
        "guid": {
        "rendered": "http://localhost:8000/?p=26",
        "raw": "http://localhost:8000/?p=26"
        },
        "modified": "2025-07-17T19:21:47",
        "modified_gmt": "2025-07-17T16:21:47",
        "password": "",
        "slug": "test_post_9",
        "status": "publish",
        "type": "post",
        "link": "http://localhost:8000/?p=26",
        "title": {
        "raw": "Post 9",
        "rendered": "Post 9"
        },
        "content": {
        "raw": "In orci, cursus sit nec vel platea sed eget ex. Dapibus elit. Pellentesque vulputate efficitur imperdiet velit sit efficitur risus pulvinar augue vestibulum ornare libero, velit pulvinar eget arcu aenean odio. Mattis mollis ut. Sit in malesuada et velit vulputate non luctus sodales et nunc non ornare ut. Amet, aenean nisi vitae eget vulputate nulla dictum lacinia in et. Nisi aenean ex. Ornare et. Luctus odio.7",
        "rendered": "<p>In orci, cursus sit nec vel platea sed eget ex. Dapibus elit. Pellentesque vulputate efficitur imperdiet velit sit efficitur risus pulvinar augue vestibulum ornare libero, velit pulvinar eget arcu aenean odio. Mattis mollis ut. Sit in malesuada et velit vulputate non luctus sodales et nunc non ornare ut. Amet, aenean nisi vitae eget vulputate nulla dictum lacinia in et. Nisi aenean ex. Ornare et. Luctus odio.7</p>\n",
        "protected": false,
        "block_version": 0
        },
        "excerpt": {
        "raw": "",
        "rendered": "<p>In orci, cursus sit nec vel platea sed eget ex. Dapibus elit. Pellentesque vulputate efficitur imperdiet velit sit efficitur risus pulvinar augue vestibulum ornare libero, velit pulvinar eget arcu aenean odio. Mattis mollis ut. Sit in malesuada et velit vulputate non luctus sodales et nunc non ornare ut. Amet, aenean nisi vitae eget vulputate nulla [&hellip;]</p>\n",
        "protected": false
        },
        "author": 1,
        "featured_media": 0,
        "comment_status": "open",
        "ping_status": "open",
        "sticky": false,
        "template": "",
        "format": "standard",
        "meta": {
        "footnotes": ""
        },
        "categories": [
        1
        ],
        "tags": [],
        "permalink_template": "http://localhost:8000/?p=26",
        "generated_slug": "post-9",
        "class_list": [
        "post-26",
        "post",
        "type-post",
        "status-publish",
        "format-standard",
        "hentry",
        "category-1"
        ],
        "_links": {
        "self": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26",
        "targetHints": {
        "allow": [
        "GET",
        "POST",
        "PUT",
        "PATCH",
        "DELETE"
        ]
        }
        }
        ],
        "collection": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts"
        }
        ],
        "about": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/types/post"
        }
        ],
        "author": [
        {
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/users/1"
        }
        ],
        "replies": [
        {
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fcomments&post=26"
        }
        ],
        "version-history": [
        {
        "count": 0,
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26/revisions"
        }
        ],
        "wp:attachment": [
        {
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fmedia&parent=26"
        }
        ],
        "wp:term": [
        {
        "taxonomy": "category",
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Fcategories&post=26"
        },
        {
        "taxonomy": "post_tag",
        "embeddable": true,
        "href": "http://localhost:8000/index.php?rest_route=%2Fwp%2Fv2%2Ftags&post=26"
        }
        ],
        "wp:action-publish": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-unfiltered-html": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-sticky": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-assign-author": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-create-categories": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-assign-categories": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-create-tags": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "wp:action-assign-tags": [
        {
        "href": "http://localhost:8000/index.php?rest_route=/wp/v2/posts/26"
        }
        ],
        "curies": [
        {
        "name": "wp",
        "href": "https://api.w.org/{rel}",
        "templated": true
        }
        ]
        }
        }

 */