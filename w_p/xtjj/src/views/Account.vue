<template>
    <Navbar/>
    <HomeContainer>
        <NavbarSet/>
        <AccountSet/>
    </HomeContainer>
    
</template>
  
  <script setup>
import AccountSet from '@/components/AccountSet.vue';
import HomeContainer from '@/components/homeContainer.vue';
import Navbar from '@/components/Navbar.vue';
import NavbarSet from '@/components/NavbarSet.vue';


  </script>
  
  <style scoped>
  /* 这里可添加组件样式 */
  </style>