package com.ddiv.juejin_fk.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity(name = "article_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "comment_id", columnDefinition = "int UNSIGNED not null")
    private Integer id;

    @JsonProperty(value = "user_id")
    @Column(name = "user_id", columnDefinition = "int UNSIGNED not null")
    private Integer userId;

    @JsonProperty(value = "article_id")
    @Column(name = "article_id", columnDefinition = "int UNSIGNED not null")
    private Integer articleId;

    @JsonProperty(value = "comment_content")
    @Column(name = "comment_content", nullable = false, length = 200)
    private String commentContent;

    @JsonIgnore
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @JsonIgnore
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

}
