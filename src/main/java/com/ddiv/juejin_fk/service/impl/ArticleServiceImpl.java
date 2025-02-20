package com.ddiv.juejin_fk.service.impl;

import com.ddiv.juejin_fk.mapper.ArticleMapper;
import com.ddiv.juejin_fk.mapper.UserMapper;
import com.ddiv.juejin_fk.pojo.Article;
import com.ddiv.juejin_fk.pojo.Comment;
import com.ddiv.juejin_fk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addArticleCommend(Comment comment) {
        articleMapper.addArticleCommend(comment);
    }

    @Override
    public boolean ifStar(Integer userId, Integer articleId) {
        Map<String, Object> map = articleMapper.ifStar(userId, articleId);
        if (map == null) return false;
        return true;
    }

    @Override
    public Map<String, Object> getArticleUP(Integer articleId, Integer userId) {
        Map<String, Object> result = articleMapper.getArticleUP(articleId);
        String userName = userMapper.findByUserId(userId).getUserName();
        if (!userName.equals(result.get("user_name")))
            result = null;
        return result;
    }

    @Override
    public List<Map<String, Object>> getArticleUPByUser(Integer userId) {
       return articleMapper.getArticleUPByUser(userId);
    }

    @Override
    public Integer addArticle(Article article) {
        articleMapper.addArticle(article);
        Integer articleId = articleMapper.findLatestArticleIdByUserId(article.getUserId()).get(0).getArticleId();
        return articleId;
    }

    @Override
    public Map<String, Object> getArticle(Integer articleId) {
        Map<String, Object> result = articleMapper.getArticle(articleId);
        if (result != null)
            articleMapper.addViews(articleId);
        return result;
    }

    @Override
    public void updateArticle(Article article) {
        if (article.getArticleTitle() != null) {
            articleMapper.updateTitle(article.getArticleId(), article.getArticleTitle());
        }
        if (article.getArticleContent() != null) {
            articleMapper.updateContent(article.getArticleId(), article.getArticleContent());
        }
    }

    @Override
    public void publishArticle(Integer articleId) {
        articleMapper.publishArticle(articleId);
    }

    @Override
    public void deleteArticle(Integer articleId, Integer userId) {
        Article article = articleMapper.getArticleInfo(articleId);
        if (article == null) {
            return;
        }
        if (article.getUserId().equals(userId)) {
            articleMapper.deleteArticle(articleId);
        }
    }

    @Override
    public void updateStar(Integer userId, Integer articleId) {
        if (articleMapper.updateStar(userId, articleId) > 0) {
            return;
        }
        articleMapper.addStar(userId, articleId);
    }

    @Override
    public List<Map<String, Object>> getHotArticles() {
        return articleMapper.getHotArticles();
    }

    @Override
    public List<Map<String, Object>> getNewArticles() {
        return articleMapper.getNewArticles();
    }

    @Override
    public List<Map<String, Object>> getCommendByAID(Integer articleId) {
        return articleMapper.getCommendByAID(articleId);
    }
}
