<template>
  <Navbar/>
  <HomeContainer>
    <NavbarHome/>
    <div class="prompt" v-if="loading">加载中...</div>
    <div class="prompt" v-else-if="error">{{ error.message }}</div>
    <div v-else>
      <ArticleComponent v-for="article in articles" :article />
    </div>
  </HomeContainer>
</template>

<script setup>
import { useArticleData } from '@/components/useArticleData.js';
  import Navbar from '@/components/Navbar.vue';
  import NavbarHome from '@/components/NavbarHome.vue';
  import HomeContainer from '@/components/homeContainer.vue';
  import ArticleComponent from '@/components/articleComponent.vue';
  ///api/articles?tags=update_up
  //https://apifoxmock.com/m2/5825455-5510878-default/260789799?apifoxApiId=260789799
  const apiUrl = '/api/articles?tags=update_up';
const { articles, loading, error } = useArticleData(apiUrl);
</script>

<style scoped>
/* 这里可添加组件样式 */
</style>