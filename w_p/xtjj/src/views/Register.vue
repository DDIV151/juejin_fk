<template>
  <div>
    <!-- 导航栏 -->
    <div class="navbar">
      <router-link to="/">登录</router-link>
      <router-link to="/register">注册</router-link>
    </div>
    <!-- 主容器 -->
    <div class="main-container">
      <h2>注册</h2>
      <input id="newUsername" class="input-field" placeholder="新用户名" />
      <input id="newPassword" type="password" class="input-field" placeholder="新密码" />
      <button @click="register" class="submit-button">注册</button>
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

const register = () => {
  const username = document.getElementById("newUsername").value;
  const password =document.getElementById("newPassword").value;
  if (username === "" || password === "") {
    alert("用户名或密码不能为空");
    return;
  }
  if (!isOKpsw(password, username)) {
    alert("密码必须是长度在[6,12]的字母和数字的组合");
    return;
  }
  if (!isOKname(username)) {
    alert("用户名长度必须小于12");
    return;
  }
  const mdpassword=md5(password);
  //向服务器发送请求，传递欲注册的用户名和密码
  //https://apifoxmock.com/m1/5825455-5510878-default/register
  //https://apifoxmock.com/m1/5825455-5510878-default/register?apifoxResponseId=612015508
  fetch(`api/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      user_name: username,
      user_password: mdpassword
    }),
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      }
      else if (response.status === 409) {
        alert("用户名已存在")
      }
      throw new Error(`error:${response.status}`);
    })
    .then((data) => {
      router.push('/login');
      console.log(data);
    })
    .catch((error) => {
      console.error(error);
    })
}

</script>