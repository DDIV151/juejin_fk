package com.ddiv.juejin_fk.mapper;

import com.ddiv.juejin_fk.pojo.Article;
import com.ddiv.juejin_fk.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

    @Insert("insert into juejin_article(article_title,article_content,user_id) values (#{articleTitle},#{articleContent},#{userId})")
    void addArticle(Article article);

    @Select("select article_id from juejin_article where user_id=#{userId} order by update_time desc")
    List<Article> findLatestArticleIdByUserId(Integer userId);

    //头皮发麻了,一条的不好看，实际上是这样：
    /*
    SELECT
    a.article_id AS article_id,
    a.article_title AS article_title,
    a.article_content AS article_content,
    COUNT(star.star_id) AS article_likes,
    a.create_time AS article_date,
    a.article_views AS article_views
FROM
    juejin_article a
LEFT JOIN
    article_star star
    ON a.article_id = star.article_id
    AND star.is_delete = 0
WHERE
    a.article_id = #{article_id}
    AND a.is_delete = 0
GROUP BY
    a.article_id;
     */
    @Select("select u.user_name,a.article_id,a.article_title,a.article_content, count(star.user_id) as article_likes,a.update_time as article_date,a.article_views from juejin_article a left join article_star star on a.article_id=star.article_id and star.is_delete=0 inner join juejin_user u on a.user_id=u.user_id and u.is_delete=0 where a.article_id=#{articleId} and a.is_delete=0 and a.is_publish=1 group by a.article_id, u.user_name, a.article_title, a.article_content, a.update_time, a.article_views")
    Map<String, Object> getArticle(Integer articleId);

    @Update("update juejin_article set article_views=article_views+1 where article_id=#{articleId} and is_delete=0")
    void addViews(Integer articleId);

    @Update("update juejin_article set article_title=#{articleTitle} where article_id=#{articleId} and is_delete=0")
    void updateTitle(Integer articleId, String articleTitle);

    @Update("update juejin_article set article_content=#{articleContent} where article_id=#{articleId} and is_delete=0")
    void updateContent(Integer articleId, String articleContent);

    @Update("update juejin_article set is_publish=1-is_publish where article_id=#{articleId} and is_delete=0")
    void publishArticle(Integer articleId);

    @Select("select article_id,user_id,is_publish from juejin_article where article_id=#{articleId} and is_delete=0 and is_publish=1")
    Article getArticleInfo(Integer articleId);

    @Update("update juejin_article set is_delete=1 where article_id=#{articleId} and is_delete=0")
    void deleteArticle(Integer articleId);

    @Update("update article_star set is_delete=1-is_delete where user_id=#{userId} and article_id=#{articleId}")
    int updateStar(Integer userId, Integer articleId);

    @Insert("insert into article_star(user_id,article_id) values (#{userId},#{articleId})")
    void addStar(Integer userId, Integer articleId);

    @Select("select a.article_id,a.article_title,a.article_content,a.article_views as article_view,count(s.user_id) as article_star from juejin_article a left join article_star s on s.article_id=a.article_id and s.is_delete=0 where a.user_id=#{userId} and a.is_delete=0 and a.is_publish=1 group by a.article_id")
    List<Map<String, Object>> getArticleFromUser(Integer userId);

    @Select("SELECT a.article_id,a.article_title,a.article_content,a.article_views AS article_view,COUNT(s_all.user_id) AS article_star FROM juejin_article a INNER JOIN article_star s_user ON a.article_id = s_user.article_id AND s_user.user_id = 1 AND s_user.is_delete = 0 LEFT JOIN article_star s_all ON a.article_id = s_all.article_id AND s_all.is_delete = 0 WHERE a.is_delete = 0 and a.is_publish=1 GROUP BY a.article_id")
    List<Map<String, Object>> getMyLikeArticle(Integer userId);

    @Select("select a.article_id,a.article_title,substring(a.article_content,1,30) as article_content,a.article_views,count(star.user_id) as article_likes,a.update_time as article_date,`user`.user_name as article_auther from juejin_article a left join article_star star on a.article_id=star.user_id inner join juejin_user `user` on `user`.user_id=a.user_id and `user`.is_delete=0 where a.is_delete=0 and a.is_publish=1 group by a.article_id, a.article_title, a.article_content, a.article_views, a.update_time, `user`.user_name order by a.article_views desc")
    List<Map<String, Object>> getHotArticles();

    @Select("select a.article_id,a.article_title,substring(a.article_content,1,30) as article_content,a.article_views,count(star.user_id) as article_likes,a.update_time as article_date,`user`.user_name as article_auther from juejin_article a left join article_star star on a.article_id=star.user_id inner join juejin_user `user` on `user`.user_id=a.user_id and `user`.is_delete=0 where a.is_delete=0 and a.is_publish=1 group by a.article_id, a.article_title, a.article_content, a.article_views, a.update_time, `user`.user_name order by a.update_time desc")
    List<Map<String, Object>> getNewArticles();

    @Select("select user_image,user_name,comment_content from article_comment c inner join juejin_user u on u.user_id=c.user_id and u.is_delete=0 inner join juejin_article a on c.article_id=a.article_id and a.is_delete = 0 where c.is_delete=0 and c.article_id=#{articleId} order by a.update_time desc ")
    List<Map<String, Object>> getCommendByAID(Integer articleId);

    @Insert("insert into article_comment(user_id,article_id,comment_content) values (#{userId},#{articleId},#{commentContent})")
    void addArticleCommend(Comment comment);

    @Select("select user_id,article_id from article_star where article_id=#{articleId} and user_id=#{userId} and is_delete=0")
    Map<String, Object> ifStar(Integer userId, Integer articleId);

    @Select("select u.user_name,a.article_id,a.article_title,a.article_content, count(star.user_id) as article_likes,a.update_time as article_date,a.article_views from juejin_article a left join article_star star on a.article_id=star.article_id and star.is_delete=0 inner join juejin_user u on a.user_id=u.user_id and u.is_delete=0 where a.article_id=#{articleId} and a.is_delete=0 group by a.article_id, u.user_name, a.article_title, a.article_content, a.update_time, a.article_views")
    Map<String,Object> getArticleUP(Integer articleId);

    @Select("select a.article_id,a.article_title,substring(a.article_content,1,30) as article_content,update_time as article_date from juejin_article a where a.user_id=#{userId} and a.is_delete=0 and a.is_publish=0 group by a.article_id")
    List<Map<String, Object>> getArticleUPByUser(Integer userId);
}
