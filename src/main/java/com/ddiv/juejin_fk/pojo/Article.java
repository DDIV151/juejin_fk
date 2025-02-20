package com.ddiv.juejin_fk.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity(name = "juejin_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", columnDefinition = "int UNSIGNED not null")
    @JsonProperty(value = "article_id")
    private Integer articleId;

    @JsonProperty(value = "article_title")
    @Column(name = "article_title", nullable = false, length = 20)
    private String articleTitle;

    @JsonProperty(value = "user_id")
    @Column(name = "user_id", columnDefinition = "int UNSIGNED not null")
    private Integer userId;

    @Lob
    @JsonProperty(value = "article_content")
    @Column(name = "article_content", nullable = false)
    private String articleContent;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @JsonProperty(value = "create_time")
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    @ColumnDefault("'0'")
    @Column(name = "is_publish", columnDefinition = "tinyint UNSIGNED not null")
    private Short isPublish;

    @JsonIgnore
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;


}
