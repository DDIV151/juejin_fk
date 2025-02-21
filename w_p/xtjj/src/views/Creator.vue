<template>
  <div>
    <Navbar />
    <HomeContainer>
      <NavbarCreator />
      <div class="prompt" v-if="loading">加载中...</div>
      <div class="prompt" v-else-if="error">{{ error.message }}</div>
      <div v-else>
        <MyArticle v-for="article in articles.my_articles" :article />
      </div>
    </HomeContainer>
  </div>
</template>
  
  <script setup>
  import { useArticleData } from '@/components/useArticleData';
  import HomeContainer from '@/components/homeContainer.vue';
import Navbar from '../components/Navbar.vue'
import AvatarDefault from '@/assets/avatar-default.png';
import NavbarCreator from '@/components/NavbarCreator.vue';
import MyArticle from '@/components/MyArticle.vue';
import { getInfo } from '@/components/getInfo.js';
const avatarUrl = AvatarDefault;
///api/users/${user_id}/info
const {user_id,token}=getInfo();
const apiUrl = `/api/users/${user_id}/info`;
const { articles, loading, error } = useArticleData(apiUrl);
  </script>
  
  <style scoped>
  /* 这里可添加组件样式 */
  </style>