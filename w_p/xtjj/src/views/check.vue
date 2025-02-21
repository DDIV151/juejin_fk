<template>
    <button @click="Register()">用户注册</button>
    <button @click="Login()">用户注册</button>
    <button @click="getArticleDetail()">查看文章详情</button>
    <button @click="getArticleList1a()">查看文章列表</button>
</template>
<script setup>
import md5 from 'js-md5';
function Register() {
    const username = '张三';
    const password = md5('123456');
    fetch(`api/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            user_name: username,
            user_password: md5(password)
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
            const userdata = JSON.stringify(data);
            localStorage.setItem("xtjj_userdata", userdata);
            router.push('');
            console.log(data);
        })
        .catch((error) => {
            console.error(error);
        })
}
function getArticleDetail(){
    const token = localStorage.getItem('token')
fetch(`https://apifoxmock.com/m1/5825455-5510878-default/articles/1`, {
    method: 'GET',
    headers: {
        'Authorization': token
    }
    })
    .then(response => {
    if(response.ok){
        return response.json()
    }else{
        throw new Error(`error:${response.status}`)
    }
})
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
}
</script>
<style scoped>
</style>