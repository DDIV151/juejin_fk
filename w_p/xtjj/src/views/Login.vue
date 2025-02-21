<template>
  <div>
    <!-- 导航栏 -->
    <div class="navbar">
      <router-link to="/login">登录</router-link>
      <router-link to="/register">注册</router-link>
    </div>
    <!-- 主容器 -->
    <div class="main-container">
      <h2>登录</h2>
      <input id="username" type="text" class="input-field" placeholder="用户名" />
      <input id="password" type="password" class="input-field" placeholder="密码" />
      <button @click="login" class="submit-button">登录</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import md5 from 'js-md5';

const router = useRouter();



//判断密码是否为长度在[6,12]字母和数字的组合,用户名长度不超过10
function isOKpsw(str) {
    if(str.length<6||str.length>12) return false;
    // 正则表达式匹配除字母和数字之外的字符
    const regex = /[^a-zA-Z0-9]/;
    return!regex.test(str);
}
function isOKname(str) {
  return str.length<12&&str.length>0;
}

const login = () => {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  //判断用户名和密码是否为空
  if(!username || !password){
    alert("请输入用户名和密码");
    return;
  }
  //判断密码是否为长度在[6,12]字母和数字的组合
  if(!isOKpsw(password)){
    alert("密码必须是长度在[6,12]的字母和数字的组合");
    return;
  }
  if(!isOKname(username)){
    alert("用户名长度必须小于12");
    return;
  }
  const mdpassword = md5(password);
  //向后端发送请求，传递用户名和密码
  //api/login
  //https://apifoxmock.com/m1/5825455-5510878-default/login
  //https://apifoxmock.com/m1/5825455-5510878-default/login?apifoxResponseId=612015060
  fetch(`api/login`,{
    method:'POST',
    headers:{
      'Content-Type':'application/json'
    },
    body:JSON.stringify({
      user_name:username,
      user_password:mdpassword
  })})
  .then(response =>{
    if(response.status === 200){
      return response.json();
    }
    else if(response.status === 401){
      alert("用户名或密码错误");
    }
    throw new Error(`error:${response.status}`);
  })
  .then(data => {
    //存储用户信息
    const userdata=JSON.stringify(data.data);
    localStorage.removeItem('xtjj_userdata');
    localStorage.setItem('xtjj_userdata',userdata);
    router.push('/');//跳转页面
    console.log(data);
    const user=localStorage.getItem('xtjj_userdata');
    console.log(user);
  })
  .catch(error => console.error(error))
}

</script>