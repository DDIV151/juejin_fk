<template>
    <div class="article-container" >
      <!-- 文章标题 -->
      <div @click="goToArticleDetail" class="article-title">
        <h2>{{ article.article_title }}</h2>
      </div>
      <!-- 文章正文开头 -->
      <div @click="goToArticleDetail" class="article-content">
        <p>{{ article.article_content }}</p>
      </div>
      <!-- 文章信息 -->
      <div class="article-info">
        <!-- 点击量和点赞数 -->
        <div class="info-left">
          <span class="views"><img :src='eyeIcon' class="eye-icon" />{{ article.article_view }}</span>
          <span class="views"><img :src='likeIcon' class="like-icon">{{ article.article_star }}</span>
          <span>{{ article.article_date }}</span>
        </div>
        <!-- 文章作者 -->
        <div class="info-right">
          <div class="delete" @click="deleteArticle">删除</div>
          <div class="unpublish" @click="unpublishArticle">取消发布</div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref,computed } from 'vue';
  import { useRouter } from 'vue-router';
  import eye from '../assets/icons/eye_gray.svg';
  import like_gray from '../assets/icons/like_gray.svg';
  import { getInfo } from './getInfo';
  const eyeIcon= eye;
  const likeIcon = like_gray;
  // 接收文章数据作为 props
  const props = defineProps({
    article: {
      type: Object,
      required: true
    }
  });
  
  // 获取路由实例
  const router = useRouter();

const goToArticleDetail = () => {
    router.push({ name: 'Detail', params: { article_id: props.article.article_id } });
};
const deleteArticle = () => {
    const {user_id,token} =getInfo();
    ///api/articles/${article_id}/delete
    //https://apifoxmock.com/m1/5825455-5510878-default/articles/1/delete
    fetch(`/api/articles/${props.article.article_id}/delete`, {
    method: 'DELETE',
    headers: {
        'Content-Type': 'application/json',
        'token': token
    }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        alert('删除成功');
    })
    .catch(error => {
        console.error(error);
        alert('删除失败');
    });
    
}
const unpublishArticle = () => {
    const {user_id,token} =getInfo();
    ///api/articles/${article_id}/update
    //https://apifoxmock.com/m1/5825455-5510878-default/articles/${props.article.article_id}/update
    fetch(`/api/articles/${props.article.article_id}/update`, {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json',
        'token': token
    }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        alert('取消发布成功');
    })
    .catch(error => {
        console.error(error);
        alert('取消发布失败');
    });   
}

//废弃的卡片点赞
/*const likeIcon =computed(()=>{
  if(isClicked.value){
    return like_blue;
  }
  else if(isHovered.value) return like_black;
  else return like_gray;
})
const handleMouseEnter = () => {
  isHovered.value = true;
};

const handleMouseLeave = () => {
  isHovered.value = false;
};

const handleClick = () => {
  isClicked.value = !isClicked.value;
};*/
  </script>
  
  <style scoped>
  .article-container {
    background-color: white;
    width: 100%;
    margin:0;
    border-bottom:0.5px solid #e0e0e0 ;
    padding: 15px 20px;
    border-radius: 2px;
    cursor: pointer;
  }
  
  .article-container:hover {
    background-color: #f7f8fa; /* 鼠标悬浮背景变浅灰色 */
  }
  
  .article-title h2 {
    margin: 0;
    white-space: nowrap; /* 标题始终一行 */
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .article-content p {
    margin: 10px 0;
    white-space: nowrap; /* 正文始终一行 */
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .article-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    color: #888;
  }
  .info-left {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }
.eye-icon {
  width: 17px;
}
.like-icon {
  width: 17px;
}
.views{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-right: 10px;
}
.info-right {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}
.delete{
    margin: 0 10px;
}
.delete:hover{
    color: red;
}
.unpublish{
 margin-left:10px ;   
}
.unpublish:hover{
    color: #007BFF;
}
  </style>