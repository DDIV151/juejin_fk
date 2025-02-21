<template>
    <div class="article-container" @click="goToArticleDetail">
      <!-- 文章标题 -->
      <div class="article-title">
        <h2>{{ article.article_title }}</h2>
      </div>
      <!-- 文章正文开头 -->
      <div class="article-content">
        <p>{{ article.article_content }}</p>
      </div>
      <!-- 文章信息 -->
      <div class="article-info">
        <!-- 点击量和点赞数 -->
        <div class="info-left">
          <span class="views"><img :src='eyeIcon' class="eye-icon" />{{ article.article_views }}</span>
          <span class="views"><img :src='likeIcon' class="like-icon">{{ article.article_likes }}</span>
          <span>{{ article.article_date }}</span>
        </div>
        <!-- 文章作者 -->
        <div class="info-right">
          {{ article.article_auther }}
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref,computed } from 'vue';
  import { useRouter } from 'vue-router';
  import eye from '../assets/icons/eye_gray.svg';
  import like_gray from '../assets/icons/like_gray.svg';
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
  const router = useRouter();//?
  
   //废弃的代码
  /*// 定义截断文本的函数
  const truncateText = (text, maxLength) => {
    return text.length > maxLength ? text.slice(0, maxLength) + '...' : text;
  };

  // 根据屏幕宽度动态计算标题和正文的最大长度
  const screenWidth = ref(window.innerWidth);
  const titleMaxLength = computed(() => {
    if (screenWidth.value < 400) {
      return 10;
    } else if (screenWidth.value < 600) {
      return 15;
    } else {
      return 20;
    }
  });
  const contentMaxLength = computed(() => {
    if (screenWidth.value < 400) {
      return 20;
    } else if (screenWidth.value < 600) {
      return 30;
    } else {
      return 40;
    }
  });
  
  // 监听窗口大小变化
  window.addEventListener('resize', () => {
    screenWidth.value = window.innerWidth;
  });
  */
  // 点击文章跳转到详情页
  /*const isHovered = ref(false);
  const isClicked = ref(false);*/

const goToArticleDetail = () => {
    router.push({ name: 'Detail', params: { article_id: props.article.article_id } });
};

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
  </style>