<template>
    <div class="home-container" :style="{ width: elementWidth + 'px' }">
        <slot></slot>
        <!--<ArticleComponent :article="{ id: 1, title: '这是一篇很长很长的文章标题很长很长很长很长', content: '这是文章的正文内容，可能会很长很长很出哈哈哈哈哈出哈哈哈哈哈哈哈哈', views: 100, likes: 20, author: '张三' }" />-->
    </div>
  </template>
  
  <script setup>
  //import ArticleComponent from '../components/articleComponent.vue';
  // 这里可添加组件逻辑
  import { ref, onMounted, onUnmounted } from 'vue';
  
  // 定义响应式变量来存储元素的宽度
  const elementWidth = ref(750);
  // 处理窗口大小变化的函数
  const handleResize = () => {
    if (window.innerWidth >= 790) {
      elementWidth.value = 750;
    } else {
      elementWidth.value = window.innerWidth - 80;
    }
  };
  onMounted(() => {
    // 页面加载时调用一次，确保初始宽度正确
    handleResize();
    // 监听窗口大小变化事件
    window.addEventListener('resize', handleResize);
  });
  
  onUnmounted(() => {
    // 组件卸载时移除事件监听器，防止内存泄漏
    window.removeEventListener('resize', handleResize);
  });
  </script>
  
  <style scoped>
  /* 这里可添加组件样式 */
  .home-container {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
    margin-top: 140px;
    margin-left: 20px;
    margin-right: 20px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 20px;
    padding-top: 0;
    /*max-width:300px;*/
    background-color: #f2f3f5;
    height: auto;
  }
  @media (max-width: 790px) {
    .home-container {
      width: calc(100% - 40px);
    }
  }
  </style>