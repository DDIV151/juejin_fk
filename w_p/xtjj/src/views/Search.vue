<template>
  <Navbar/>
  <HomeContainer>
    <NavbarHome/>
    <ArticleComponent/>
  </HomeContainer>
</template>

<script setup>
  import Navbar from '@/components/Navbar.vue';
  import NavbarHome from '@/components/NavbarHome.vue';
  import HomeContainer from '@/components/homeContainer.vue';
  import ArticleComponent from '@/components/articleComponent.vue';
</script>

<style scoped>
/* 这里可添加组件样式 */
</style>