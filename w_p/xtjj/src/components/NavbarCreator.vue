<template>
    <div class="valNavbar">
        <div class="left-container">
        <router-link to="/creator" >文章</router-link>
        <router-link to="/drafts">草稿箱</router-link>
        </div>
        <div>
            <button class="button-create" @click="create()">写文章</button>
        </div>
    </div>
  </template>
  
  <script setup>
import { useRouter } from 'vue-router'

  // 这里可添加组件逻辑
  const router = useRouter();
  function create(){
      router.push('/create')
  }
  </script>
  
  <style scoped>
  .valNavbar {
    display: flex;
    width: 100%;
    background-color: #fff;
    padding:3px 20px;
    border-radius: 2px;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #ddd;
  }
  .valNavbar a {
    display: inline-block;
    text-align: center;
    padding: 12px 0px;
    margin: 0 12px;
    text-decoration: none;
    font-size: 17px;
    outline: none;
    color:#aaa
  }
  .valNavbar a.router-link-active {
      color: #007BFF;
  }
  .valNavbar a:hover {
      padding-bottom: 9.5px;
      border-bottom: 2.5px solid #007BFF
  }
  /* 这里可添加组件样式 */
  .button-create{
    background-color: #007BFF;
    color: white;
    padding:8px 15px;
    border-radius: 3px;
    border:none;
  }
  </style>