<template>
    <div class="valNavbar-home">
        <router-link to="/" >推荐</router-link>
        <router-link to="/newest">最新</router-link>
    </div>
  </template>
  
  <script setup>
  // 这里可添加组件逻辑
  </script>
  
  <style scoped>
  .valNavbar-home {
      width: 100%;
      background-color: #fff;
      padding:3px 20px;
      border-radius: 2px;
      border-bottom: 1px solid #e0e0e0;
  }
  .valNavbar-home a {
    display: inline-block;
    text-align: center;
    padding: 12px 0px;
    margin: 0 12px;
    text-decoration: none;
    font-size: 17px;
    outline: none;
    color:#aaa
  }
  .valNavbar-home a.router-link-active {
      color: #007BFF;
  }
  .valNavbar-home a:hover {
      padding-bottom: 9.5px;
      border-bottom: 2.5px solid #007BFF
  }
  /* 这里可添加组件样式 */
  </style>