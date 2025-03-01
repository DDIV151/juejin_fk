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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final HttpServletResponse httpServletResponse;

    public ArticleController(ArticleService articleService, UserService userService, HttpServletResponse httpServletResponse) {
        this.articleService = articleService;
        this.userService = userService;
        this.httpServletResponse = httpServletResponse;
    }

    /**
     * 上传文章
     *
     * @param article 请求中body内json，应涵标题与正文
     * @param token   用户标识
     * @return 标准返回
     */
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

    /**
     * 获取已发布特定文章
     *
     * @param articleId 定位文章
     * @param token     用户标识
     * @return 标准返回
     */
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

    /**
     * 获取未发布特定文章（后续需验证身份）
     *
     * @param articleId 定位文章
     * @param token     用户标识
     * @return 标准返回
     */
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

    /**
     * 获取用户手下没发布的文章
     *
     * @param token 验证用户
     * @return 文章对象
     */
    @GetMapping("/api/articles/unpublish")
    public ApiResult<Object> getArticleUPByUser(@RequestHeader String token) {
        Integer userId = getID(token);
        List<Map<String, Object>> result = articleService.getArticleUPByUser(userId);
        return ApiResult.success(200, "成功", result);
    }

    /**
     * 修改文章
     *
     * @param article   要修改的文字内容（不修改部分为null）
     * @param articleId 定位文章
     * @param token     用户标识
     * @return 标准返回
     */
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

    /**
     * 发布文章
     *
     * @param articleId 定位文章
     * @return 标准返回
     */
    @PutMapping("/api/articles/{articleId}/update")
    public ApiResult<Object> publishArticle(@PathVariable Integer articleId) {
        articleService.publishArticle(articleId);
        return ApiResult.success(200, "修改成功");
    }

    /**
     * 删除文章（需验证身份）
     *
     * @param articleId 定位文章
     * @param token     用户标识
     * @return 标准返回
     */
    @DeleteMapping("/api/articles/{articleId}/delete")
    public ApiResult<Object> deleteArticle(@PathVariable Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.deleteArticle(articleId, userId);
        return ApiResult.success(200, "删除成功");
    }

    /**
     * 解析token获取用户id
     *
     * @param token token
     * @return 用户id
     */
    protected static Integer getID(String token) {
        Jws<Claims> claims = TokenUtils.parseToken(token);
        Integer userId = (Integer) claims.getPayload().get("user_id");
        return userId;
    }

    /**
     * 修改点赞状态（其实兼具删除功能）
     *
     * @param articleId 定位文章
     * @param token     标识点赞者
     * @return 标准返回
     */
    @PostMapping("/api/articles/{article_id}/star")
    public ApiResult<Object> addStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    /**
     * @param articleId 定位文章
     * @param token     点赞者
     * @return 标准返回
     */
    @DeleteMapping("/api/articles/{article_id}/star")
    public ApiResult<Object> deleteStar(@PathVariable(value = "article_id") Integer articleId, @RequestHeader String token) {
        Integer userId = getID(token);
        articleService.updateStar(userId, articleId);
        return ApiResult.success(200, "成功");
    }

    /**
     * @param tags 按条件获取文章列表
     * @return 文章列表
     */
    @GetMapping("/api/articles")
    public ApiResult<Object> getArticles(@RequestParam(name = "tags") String tags) {
        List<Map<String, Object>> result = null;
        //按点击数降序
        if (tags.equals("click_down")) {
            result = articleService.getHotArticles();
            //按时间升序
        } else if (tags.equals("update_up")) {
            result = articleService.getNewArticles();
        }
        return ApiResult.success(200, "获取成功", result);
    }

    /**
     * 获取文章详情
     *
     * @param articleId 定位文章
     * @return 文章对象
     */
    @GetMapping("/api/{articleId}/comment")
    public ApiResult<Object> getArticleComment(@PathVariable Integer articleId) {
        return ApiResult.success(200, "获取成功", articleService.getCommendByAID(articleId));
    }

    /**
     * 新增评论
     *
     * @param articleId 定位文章
     * @param comment   评论对象
     * @param token     定位用户
     * @return 标准结果
     */
    @PostMapping("/api/{articleId}/comment")
    public ApiResult<Object> addArticleComment(@PathVariable Integer articleId, @RequestBody Comment comment, @RequestHeader String token) {
        comment.setArticleId(articleId);
        comment.setUserId(getID(token));
        articleService.addArticleCommend(comment);
        return ApiResult.success(200, "评论成功");
    }

    /**
     * 删评跑路
     *
     * @param commentId 定位评论
     * @param token     验证用户
     * @return 标准结局
     */
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
}
