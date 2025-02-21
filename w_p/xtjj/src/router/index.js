import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue';
import Home from '../views/Home.vue';
import Register from '../views/Register.vue';
import Search from '../views/Search.vue';
import User from '../views/User.vue';
import Newest from '@/views/Newest.vue';
import Creator from '@/views/Creator.vue';
import Drafts from '@/views/Drafts.vue';
import Settings from '@/views/Settings.vue';
import Create from '@/views/Create.vue';
import Check from '@/views/check.vue';
import Account from '@/views/Account.vue';
import Likes from '@/views/Likes.vue';
import Detail from '@/views/Detail.vue';
import Draft from '@/views/Draft.vue';
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/search',
      name: 'Search',
      component:Search
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/newest',
      name: 'Newest',
      component: Newest
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path:'/creator',
      name:'Creator',
      component:Creator

    },
    {
      path:'/drafts',
      name:'Drafts',
      component:Drafts
    },
    {
      path:'/settings',
      name:'Settings',
      component:Settings
    },
    {
      path:'/create',
      name:'Create',
      component:Create
    },
    {
      path:'/check',
      name:'Check',
      component:Check
    },
    {
      path: '/account',
      name: 'Account',
      component: Account,
    },
    {
      path:'/likes',
      name:'Likes',
      component:Likes
    },
    {
      path:'/detail/:article_id',
      name:'Detail',
      component:Detail
    },
    {
      path:'/draft/:article_id',
      name:'Draft',
      component:Draft
    }
  ],
})


/*function isAuthenticated() {
  // 检查用户是否已登录，这里假设有一个全局的登录状态
  return localStorage.getItem('token') !== null;   
}

router.beforeEach((to, from, next) => {
  if (to.name!== 'Login'&&to.name!== 'Register'&&!isAuthenticated()) {
    // 如果要访问的页面不是登录页也不是注册页且用户未登录，跳转到登录页
    next({ name: 'Login' });
  } else {
    // 否则正常跳转
    next();
  }
});*/

export default router
