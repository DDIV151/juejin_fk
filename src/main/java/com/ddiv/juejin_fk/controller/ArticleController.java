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
import jakarta.servlet.http.HttpServletResponse;
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
    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping(value = "/api/articles/publish")
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

    @GetMapping("/api/articles/{articleId}")
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

    @GetMapping("/api/articles/{articleId}/unpublish")
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

    @PutMapping("/api/articles/{articleId}")
    public ApiResult<Object> updateArticle(@RequestBody Article article, @PathVariable Integer articleId, @RequestHeader String token) {
        int lines = 0;
        if (article.getArticleTitle() != null || article.getArticleContent() != null) {
            Integer userId = getID(token);
            article.setArticleId(articleId);
            lines = articleService.updateArticle(userId, article);
        }
        if (lines > 0)
            return ApiResult.success(200, "修改成功");
        httpServletResponse.setStatus(401);
        return ApiResult.error(401, "修改失败");
    }


    @PutMapping("/api/articles/{articleId}/update")
    public ApiResult<Object> publishArticle(@PathVariable Integer articleId) {
        articleService.publishArticle(articleId);
        return ApiResult.success(200, "修改成功");
    }

    @DeleteMapping("/api/articles/{articleId}/delete")
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

    @PostMapping("/api/articles/{article_id}/star")
    public ApiResult<Object> addStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    @DeleteMapping("/api/articles/{article_id}/star")
    public ApiResult<Object> deleteStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    @GetMapping("/api/articles")
    public ApiResult<Object> getArticles(@RequestParam(name = "tags") String tags) {
        List<Map<String, Object>> result = null;
        if (tags.equals("click_down")) {
            result = articleService.getHotArticles();
        } else if (tags.equals("update_up")) {
            result = articleService.getNewArticles();
        }
        return ApiResult.success(200, "获取成功", result);
    }

    @GetMapping("/api/{articleId}/comment")
    public ApiResult<Object> getArticleComment(@PathVariable Integer articleId) {
        return ApiResult.success(200, "获取成功", articleService.getCommendByAID(articleId));
    }

    @PostMapping("/api/{articleId}/comment")
    public ApiResult<Object> addArticleComment(@PathVariable Integer articleId, @RequestBody Comment comment, @RequestHeader String token) {
        comment.setArticleId(articleId);
        comment.setUserId(getID(token));
        articleService.addArticleCommend(comment);
        return ApiResult.success(200, "评论成功");
    }

    @DeleteMapping("/api/{commentId}/comment")
    public ApiResult<Object> deleteArticleComment(@PathVariable Integer commentId, @RequestHeader String token) {
        Integer userId = getID(token);
        int line = 0;
        line = articleService.deleteComment(userId, commentId);
        if (line == 0) {
            httpServletResponse.setStatus(402);
            return ApiResult.error(402, "删除失败");
        }
        return ApiResult.success(200, "成功");
    }

    @GetMapping("/api/articles/unpublish")
    public ApiResult<Object> getArticleUPByUser(@RequestHeader String token) {
        Integer userId = getID(token);
        List<Map<String, Object>> result = articleService.getArticleUPByUser(userId);
        return ApiResult.success(200, "成功", result);
    }


}
