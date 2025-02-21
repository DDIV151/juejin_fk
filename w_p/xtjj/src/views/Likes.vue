<template>
    <div>
      <Navbar />
      <HomeContainer>
        <div class="prompt" v-if="loading">加载中...</div>
        <div class="prompt" v-else-if="error">{{ error.message }}</div>
        <div v-else>
          <UserInfoCard :userdata />
        </div>
        <NavbarUser />
        <ArticleComponent v-for="article in articles.like_articles" :article />
      </HomeContainer>
    </div>
  </template>
    
    <script setup>
    import { useUserData } from '@/components/useUserData';
    import { useArticleData } from '@/components/useArticleData';
    import HomeContainer from '@/components/homeContainer.vue';
  import Navbar from '../components/Navbar.vue'
  import UserInfoCard from '@/components/UserInfoCard.vue';
  import AvatarDefault from '@/assets/avatar-default.png';
  import NavbarUser from '@/components/NavbarUser.vue';
  import ArticleComponent from '@/components/articleComponent.vue';
  import { getInfo } from '@/components/getInfo';
  const avatarUrl = AvatarDefault;
  //https://apifoxmock.com/m1/5825455-5510878-default/1
  ///api/${user_id}
  const {user_id}=getInfo();
  const userUrl=`/api/${user_id}`
  ///api/users/${user_id}/info
  const articleUrl=`/api/users/${user_id}/info`
    const { loading, error, userdata } = useUserData(userUrl);
    const { articles } = useArticleData(articleUrl);
    </script>
    
    <style scoped>
    /* 这里可添加组件样式 */
    </style>