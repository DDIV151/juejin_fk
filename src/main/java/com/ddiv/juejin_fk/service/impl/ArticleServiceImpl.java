package com.ddiv.juejin_fk.service.impl;

import com.ddiv.juejin_fk.mapper.ArticleMapper;
import com.ddiv.juejin_fk.mapper.UserMapper;
import com.ddiv.juejin_fk.pojo.Article;
import com.ddiv.juejin_fk.pojo.Comment;
import com.ddiv.juejin_fk.service.ArticleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final UserMapper userMapper;
    private final RedisTemplate<String,Object> redisTemplate;

    // Redis 键名常量
    private static final String HOT_ARTICLES_KEY = "hot:articles";
    private static final String NEW_ARTICLES_KEY = "new:articles";
    private static final long CACHE_TIMEOUT = 30; // 缓存过期时间（分钟）

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, UserMapper userMapper,RedisTemplate<String,Object> redisTemplate) {
        this.articleMapper = articleMapper;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

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
    public int deleteComment(Integer userId, Integer commentId) {
        Comment comment = articleMapper.getCommentById(commentId);
        if (comment == null) return 0;
        if (!comment.getUserId().equals(userId))
            return 0;
        return articleMapper.deleteComment(commentId);
    }

    @Override
    public Integer addArticle(Article article) {
        articleMapper.addArticle(article);
        return articleMapper.findLatestArticleIdByUserId(article.getUserId()).get(0).getArticleId();
    }

    @Override
    public Map<String, Object> getArticle(Integer articleId) {
        Map<String, Object> result = articleMapper.getArticle(articleId);
        if (result != null)
            articleMapper.addViews(articleId);
        return result;
    }

    @Override
    public int updateArticle(Integer userId, Article article) {
        Integer articleId = article.getArticleId();
        Integer realUserId = articleMapper.getArticleUser(articleId);
        if (realUserId != null && !realUserId.equals(userId)) {
            return 0;
        }
        int lines = 0;
        if (article.getArticleTitle() != null) {
            lines += articleMapper.updateTitle(articleId, article.getArticleTitle());
        }
        if (article.getArticleContent() != null) {
            lines += articleMapper.updateContent(articleId, article.getArticleContent());
        }
        return lines;
    }

    @Override
    public void publishArticle(Integer articleId) {
        articleMapper.publishArticle(articleId);
    }

    @Override
    public void deleteArticle(Integer articleId, Integer userId) {
        Article article = articleMapper.ifArticleExist(articleId);
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
