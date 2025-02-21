<template>
    <div class="container" >
      <!-- 文章标题 -->
      <div class="title" @click="goToDraftDetail()">
        <h2>{{ article.article_title }}</h2>
      </div>
      <!-- 文章正文开头 -->
      <div class="content" @click="goToDraftDetail()">
        <p>{{ article.article_content }}</p>
      </div>
      <!-- 文章信息 -->
      <div class="info">
        <!-- 点击量和点赞数 -->
        <span class="date">{{ article.article_date }}</span>
        <span>
        <span class="delete-button" @click="deleteArticle">删除</span>
        <span class="publish-button" @click="publishArticle">发布</span>
        </span>
      </div>
    </div>
  </template>
  
  <script setup>

  import { useRouter } from 'vue-router';
import { getInfo } from './getInfo';
  // 接收文章数据作为 props
  const props = defineProps({
    article: {
      type: Object,
      required: true
    }
  });
  
  // 获取路由实例
  const router = useRouter();
  

const goToDraftDetail = () => {
    router.push({ name: 'Draft', params: { article_id: props.article.article_id } });
};
const deleteArticle = () => {
  const articleId = props.article.article_id;
  const {user_id,token}=getInfo();
  ///api/articles/{article_id}/delete
  fetch(`/api/articles/${props.article.article_id}/delete`, {
    method: 'DELETE',
    headers: {
        'Content-Type': 'application/json',
        'token': token
    }
  })
  .then(response => {
      if (!response.ok) {
          throw new Error(`Error:${response.status}`);
      }
      return response.json();
  })
  .then(data => {
      console.log('Success:', data);
      alert('删除成功');
      //刷新页面
      window.location.reload();
  })
  .catch(error => {
      console.error(error);
      alert('删除失败');
  });
  
}
const publishArticle = () => {
  const articleId = props.article.article_id;
  const {user_id,token}=getInfo();
  ///api/articles/{article_id}/update
  fetch(`/api/articles/${props.article.article_id}/update`, {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json',
        'token': token
    }
  })
  .then(response => {
      if (!response.ok) {
          throw new Error(`Error:${response.status}`);
      }
      return response.json();
  })
  .then(data => {
      console.log('Success:', data);
      alert('发布成功');
      //刷新页面
      window.location.reload();
  })
  .catch(error => {
      console.error(error);
      alert('发布失败');
  });
}

  </script>
  
  <style scoped>
  .container {
    background-color: white;
    width: 100%;
    margin:0;
    border-bottom:0.5px solid #e0e0e0 ;
    padding: 15px 20px;
    border-radius: 2px;
    cursor: pointer;
  }
  
  .container:hover {
    background-color: #f7f8fa; /* 鼠标悬浮背景变浅灰色 */
  }
  
  .title h2 {
    margin: 0;
    white-space: nowrap; /* 标题始终一行 */
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .content p {
    margin: 10px 0;
    white-space: nowrap; /* 正文始终一行 */
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    color: #888;
  }
  .delete-button{
    margin: 0 10px;
  }
  .delete-button:hover{
    color: red;
  }
  
  .publish-button:hover{
    color: #007BFF;
  }
  
  </style>