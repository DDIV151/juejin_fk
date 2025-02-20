package com.ddiv.juejin_fk.service;

import com.ddiv.juejin_fk.pojo.Article;
import com.ddiv.juejin_fk.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ArticleService {
    Integer addArticle(Article article);

    Map<String,Object> getArticle(Integer articleId);

    void updateArticle(Article article);

    void publishArticle(Integer articleId);

    void deleteArticle(Integer articleId, Integer userId);

    void updateStar(Integer userId, Integer articleId);

    List<Map<String, Object>> getHotArticles();

    List<Map<String, Object>> getNewArticles();

    List<Map<String, Object>> getCommendByAID(Integer articleId);

    void addArticleCommend(Comment comment);

    boolean ifStar(Integer userId, Integer articleId);

    Map<String, Object> getArticleUP(Integer articleId, Integer userId);

    List<Map<String, Object>> getArticleUPByUser(Integer userId);
}
