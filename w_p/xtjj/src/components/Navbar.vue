<template>
    <div>
      <!-- 导航栏 -->
      <div class="navbar_home">
        <div class="left-component">
          <img class="logo" :src='logoUrl' alt="logo">
          <router-link to="/" class="link-a">首页</router-link>
        </div>
        <div class="right-component">
          <div class="search-container">
            <!--搜索框-->
            <input class="search-input" type="text" placeholder="搜索">
            <button class="search-button" @click="searchContent()">搜索</button>
          </div>
          <!--搜索按钮-->
          <!--创作者中心-->
          <button class="creator-button" @click="goCreatorCenter()">创作者中心</button>
          <!--个人主页-->
          <router-link to="/user">个人主页</router-link>
          <img :src="imgUrl" alt="头像" class="avatar"/>
        </div>
      </div>
      <!--首页-->
      <div>
      </div>
    </div>
  </template>
    
    <script setup>
      import logo from '@/assets/logo.png';
      import { useRouter } from 'vue-router';
      import { onMounted } from 'vue';
import { getInfo } from './getInfo';
import { ref } from 'vue';
import avatarDefault from '@/assets/avatarDefault.png';

      onMounted(() => {
        const {user_id,token}=getInfo();
        ///users/${user_id}/image
        //https://apifoxmock.com/m1/5825455-5510878-default/users/1/image
        fetch(`/api/users/${user_id}/image`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'token': token
          }
        })
        .then(response =>{
          if (!response.ok){
            throw new Error(response.status);
          }
          return response.json();
        })
        .then(data => {
          console.log(data);
          imgUrl.value = data.data.user_image;
          if(!imgUrl.value){
            imgUrl.value = avatarDefault;
          }
        })
        .catch(err => {
          console.error(err);
        })
      })
      const imgUrl=ref('');
      const router = useRouter();
      const logoUrl = logo;
      const searchContent = () => {
        router.push('/search');
      }
      function goCreatorCenter(){
        router.push('/creator');
      }
    </script>
    <style scoped>
    .navbar_home{
  position: fixed;
  top:0;
  left: 0;
  margin: 0;
  width: 100%;
  background-color:white;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.right-component{
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-right: 10px;
}

.left-component{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.navbar_home a{
  box-sizing: border-box;
  color: rgb(40, 40, 40);
  text-align: center;
  padding: 14px 0px;
  margin: 0 15px;
  text-decoration: none;
  font-size: 17px;
  outline: none;
  background-color: white;
  white-space: nowrap;
}
.navbar_home a:hover {
  padding-bottom: 11.5px;
  border-bottom:2.5px solid #007BFF ;
  background-color:white;
}
.navbar_home a.router-link-active {
  color: #007BFF; /* 选中时字体颜色为蓝色 */
}
.logo{
  margin:0 10px;
  height: auto;
}

.search-container {
  position: relative;
  display: flex;
  align-items: center;
  margin: 0 10px;
}
.search-input {
  width: 350px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.search-input:hover {
  border-color: #999;
}
.search-input:focus {
  outline: none;
  border: #007BFF 1px solid;
}
.search-input:focus+.search-button {
  background-color: #e4f1ff;
  color:#007BFF ;
}
.search-button {
  position: absolute;
  right: 5px; /* 按钮距离搜索框右侧的距离 */
  padding: 8px 15px;
  background-color: #eee;
  color: #333;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.creator-button{
  padding:8px;
  border-radius: 3px;
  background-color: #2684ff;
  color: white;
  border: none;
  margin: 0 15px;
}
.creator-button:hover {
  background-color: #007afc;
}
@media (max-width: 835px) {
  .creator-button {
    display: none; /* 当屏幕宽度小于等于 840px 时，隐藏创作者中心链接 */
  }
}
@media (max-width: 735px) {
  .logo{
    display: none; /* 当屏幕宽度小于等于 576px 时，隐藏个人主页链接 */
  }
}
@media (max-width: 580px) {
  .right-component a{
    display: none; /* 当屏幕宽度小于等于 576px 时，隐藏个人主页链接 */
  }
}

.home-container{
  margin-top: 60px;
  width: 750px;
  height: 100px;
  background-color: white;

}
.avatar{
  width: 35px;
  height: 35px;
  margin: 10px;
  border-radius: 50%;
}
    </style>