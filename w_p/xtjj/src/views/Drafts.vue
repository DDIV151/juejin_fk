<template>
  <div>
    <Navbar />
    <HomeContainer>
      <NavbarCreator/>
      <div class="prompt" v-if="loading">加载中...</div>
      <div class="prompt" v-else-if="error">{{ error.message }}</div>
      <div v-else>
        <DraftsComponent v-for="article in articles" :article/>
      </div>
      
    </HomeContainer>
  </div>
</template>
  
  <script setup>
import { useArticleData } from '@/components/useArticleData.js';
import HomeContainer from '@/components/homeContainer.vue';
import Navbar from '../components/Navbar.vue'
import NavbarCreator from '@/components/NavbarCreator.vue';
import DraftsComponent from '@/components/DraftsComponent.vue';
///api/articles/{article_id}/unpublish
///api/articles/unpublish
// 'https://apifoxmock.com/m2/5825455-5510878-default/260789799?apifoxApiId=260789799'
const apiUrl =`/api/articles/unpublish`;
const { articles, loading, error } = useArticleData(apiUrl);
  // 这里可添加组件逻辑
  </script>
  
  <style scoped>
  /* 这里可添加组件样式 */
  </style>