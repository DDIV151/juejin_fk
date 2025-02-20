package com.ddiv.juejin_fk.controller;

import com.ddiv.juejin_fk.exception.UserNotFoundException;
import com.ddiv.juejin_fk.pojo.ApiResult;
import com.ddiv.juejin_fk.pojo.Article;
import com.ddiv.juejin_fk.pojo.Comment;
import com.ddiv.juejin_fk.pojo.JuejinUser;
import com.ddiv.juejin_fk.service.ArticleService;
import com.ddiv.juejin_fk.service.UserService;
import com.ddiv.juejin_fk.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/articles/publish")
    public ApiResult<Object> addArticle(@RequestBody Article article, @RequestHeader String token) {
        Integer userId = getID(token);
        JuejinUser user = userService.findByUserId(userId);
        if (user == null) {
            throw new UserNotFoundException(401, "用户不存在");
        }
        article.setUserId(userId);
        Integer articleId = articleService.addArticle(article);
        Map<Object, Object> map = new HashMap<>();
        map.put("article_id", articleId);
        return ApiResult.success(200, "新增文章成功", map);
    }

    @GetMapping("/articles/{articleId}")
    public Object getArticle(@PathVariable Integer articleId, @RequestHeader String token) {
        Map<String, Object> result = articleService.getArticle(articleId);
        Integer userId = getID(token);
        if (result != null)
            if (articleService.ifStar(userId, articleId))
                result.put("is_star", true);
            else
                result.put("is_star", false);
        return ApiResult.success(200, "查询成功", result);
    }

    @GetMapping("/articles/{articleId}/unpublish")
    public Object getArticleUP(@PathVariable Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        Map<String, Object> result = articleService.getArticleUP(articleId, userId);
        if (result != null)
            if (articleService.ifStar(userId, articleId))
                result.put("is_star", true);
            else
                result.put("is_star", false);
        return ApiResult.success(200, "查询成功", result);
    }

    @PutMapping("/articles/{article_id}")
    public ApiResult<Object> updateArticle(@RequestBody Article article) {
        if (article.getArticleId() != null)
            if (article.getArticleTitle() != null || article.getArticleContent() != null)
                articleService.updateArticle(article);
        return ApiResult.success(200, "修改成功");
    }


    @PutMapping("/articles/{articleId}/update")
    public ApiResult<Object> publishArticle(@PathVariable Integer articleId) {
        articleService.publishArticle(articleId);
        return ApiResult.success(200, "修改成功");
    }

    @DeleteMapping("/articles/{articleId}/delete")
    public ApiResult<Object> deleteArticle(@PathVariable Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.deleteArticle(articleId, userId);
        return ApiResult.success(200, "删除成功");
    }

    protected static Integer getID(String token) {
        Jws<Claims> claims = TokenUtils.parseToken(token);
        Integer userId = (Integer) claims.getPayload().get("user_id");
        return userId;
    }

    @PostMapping("/articles/{article_id}/star")
    public ApiResult<Object> addStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    @DeleteMapping("/articles/{article_id}/star")
    public ApiResult<Object> deleteStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    @GetMapping("/articles")
    public ApiResult<Object> getArticles(@RequestParam(name = "tags") String tags) {
        List<Map<String, Object>> result = null;
        if (tags.equals("click_down")) {
            result = articleService.getHotArticles();
        } else if (tags.equals("update_up")) {
            result = articleService.getNewArticles();
        }
        return ApiResult.success(200, "获取成功", result);
    }

    @GetMapping("/{articleId}/comment")
    public ApiResult<Object> getArticleCommend(@PathVariable Integer articleId) {
        return ApiResult.success(200, "获取成功", articleService.getCommendByAID(articleId));
    }

    @PostMapping("/{articleId}/comment")
    public ApiResult<Object> addArticleCommend(@PathVariable Integer articleId, @RequestBody Comment comment, @RequestHeader String token) {
        comment.setArticleId(articleId);
        comment.setUserId(getID(token));
        articleService.addArticleCommend(comment);
        return ApiResult.success(200, "评论成功");
    }


}
