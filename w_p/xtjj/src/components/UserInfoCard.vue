<template>
    <div class="user-info-card">
        <div class="left-container">
        <div class="user-info-avatar">
            <img :src="!userdata.user_image?avatarDefault:userdata.user_image" alt="头像" class="user-avatar"/>
        </div>
        <div class="middle-container">
            <span class="username">{{ userdata.user_name }}</span>
            <span class="resume">个人简介：</span>
            <span>{{ userdata.user_resume }}</span>
        </div>
        </div>
        <div class="right-container">
            <button @click="getSet()">设置</button>
        </div>

    </div>
</template>

<script setup>
import AvatarDefault from '@/assets/avatar-default.png'
const avatarUrl = AvatarDefault;
import { useRouter } from 'vue-router'
import avatarDefault from '@/assets/avatarDefault.png'
  // 接收文章数据作为 props
const props = defineProps({
    userdata: {
      type: Object,
      required: true
    }
  });
  const router = useRouter();
  function getSet(){
    
    router.push('/settings');
  }
</script>

<style scoped>
.user-info-card {
    background-color: white;
    width: 100%;
    margin:0;
    border-bottom:0.5px solid #e0e0e0 ;
    padding: 15px 20px;
    border-radius: 2px;
    cursor: pointer;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.left-container{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: flex-start;
}
.user-avatar{
    width: 150px;
    height:150px;
    border-radius: 50%;
}
.middle-container{
    display: flex;
    flex-direction: column;
    padding: 10px;
}
.username{
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 15px;
}
.right-container{
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: flex-end;
}
button{
    background-color: white;
    border-radius: 5px;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    font-size: 14px;
    color:#007BFF;
    border: 1px solid #007BFF;
}
button:hover{
    border: 2px solid #007BFF;
    padding: 9px 19px;
}
</style>