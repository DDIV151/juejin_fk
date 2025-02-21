<template>
    <div class="valNavbar-home">
        <!--<a href="/">推荐</a>-->
        <router-link to="/user" >我的文章</router-link>
        <router-link to="/likes">我的点赞</router-link>
        <!--<a href="/?sort=newest">最新</a>-->
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
        margin-top: 20px;
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