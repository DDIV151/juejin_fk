<template>
  <Navbar/>
  <homeContainer>
    <NavbarHome/>
    <div class="prompt" v-if="loading">加载中...</div>
    <div class="prompt" v-else-if="error">{{ error.message }}</div>
    <div v-else>
      <ArticleComponent v-for="article in articles" :article />
    </div>
  </homeContainer>
</template>

<script setup>
import ArticleComponent from '@/components/articleComponent.vue';
import { useArticleData } from '@/components/useArticleData.js';
import homeContainer from '@/components/homeContainer.vue';
import Navbar from '@/components/Navbar.vue';
import NavbarHome from '@/components/NavbarHome.vue';
//api/articles
//https://apifoxmock.com/m2/5825455-5510878-default/260789799?apifoxApiId=260789799
const apiUrl = '/api/articles?tags=click_down';
const { articles, loading, error } = useArticleData(apiUrl);
</script>
<style scoped>
</style>