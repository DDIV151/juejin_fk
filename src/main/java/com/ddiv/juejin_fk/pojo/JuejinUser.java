package com.ddiv.juejin_fk.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "juejin_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuejinUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "user_id")
    @Column(name = "user_id", columnDefinition = "int UNSIGNED not null", insertable = false, updatable = false)
    private Integer userId;

    @JsonProperty(value = "user_name")
    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @JsonProperty(value = "user_image")
    @Column(name = "user_image")
    private String userImage;

    @JsonProperty(value = "user_resume")
    @Column(name = "user_resume")
    private String userResume;

    @JsonProperty(value = "user_password", access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_password", nullable = false, length = 32)
    private String userPassword;

    @JsonProperty(value = "create_time")
    @JsonIgnore
    @Column(name = "create_time", insertable = false, updatable = false)
    private LocalDateTime createTime;

    @JsonProperty(value = "update_time")
    @JsonIgnore
    @Column(name = "update_time", insertable = false, updatable = false)
    private LocalDateTime updateTime;


    public JuejinUser(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "userId: " + userId + "\nuserName: " + userName;
    }
}
