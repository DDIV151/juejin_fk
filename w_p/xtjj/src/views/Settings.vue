<template>
    <Navbar/>
    <HomeContainer>
        <NavbarSet/>
        <UserProfileEdit/>
    </HomeContainer>
    
</template>
  
  <script setup>
import HomeContainer from '@/components/homeContainer.vue';
import Navbar from '@/components/Navbar.vue';
import NavbarSet from '@/components/NavbarSet.vue';
import UserProfileEdit from '@/components/UserProfileEdit.vue';

  </script>
  
  <style scoped>
  /* 这里可添加组件样式 */
  </style>