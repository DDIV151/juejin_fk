<template>
    <div class="container">
        <div class="left-section">
            <label for="pre_password">旧密码：</label>
            <input id="pre_password" type="password" placeholder="请输入旧密码" />
            <label for="new_password">新密码：</label>
            <input id="new_password" type="password" placeholder="请输入新密码" />
            <button @click="changePassword()">修改密码</button>
        </div>
        <div class="right-section">
            <input id="password" type="password" placeholder="请输入密码"></input>
            <button @click="deleteAccount()">注销账号</button>
        </div>
    </div>
    <ConfirmDialog :visible="dialog" :message="message"  @confirm="handleConfirm" @cancel="handleCancel"/>
</template>

<script setup>
import ConfirmDialog from './ConfirmDialog.vue';
import { ref } from 'vue';
import { getInfo } from './getInfo';
import md5 from 'js-md5';
const dialog = ref(false);
const message = ref('');
const ifchange=ref(false);
const ifdelete=ref(false);

// 保存修改的方法;
const changePassword = () => {
    message.value = '确定修改密码吗？';
    dialog.value = true;
    ifchange.value=true;
};
const deleteAccount = () => {
    message.value = '确定注销账号吗？';
    dialog.value = true;
    ifdelete.value=true;
};

const handleConfirm = () => {
    dialog.value = false;
    if (ifchange.value) {
        changeOperation();
        ifchange.value=false;
    }
    if (ifdelete.value) {
        deleteOperation();
        ifdelete.value=false;
    }
};
const handleCancel = () => {
    dialog.value = false;
};

function isOKpsw(str) {
    if(str.length<6||str.length>12) return false;
    // 正则表达式匹配除字母和数字之外的字符
    const regex = /[^a-zA-Z0-9]/;
    return!regex.test(str);
}

const changeOperation = () => {
    //api//users/{user_id}/password
    const { user_id, token } = getInfo();
    //https://apifoxmock.com/m1/5825455-5510878-default/users/1/password
    ///api/users/${user_id}/password
    const apiUrl = `api/users/${user_id}/password`;
    const pre_word = document.getElementById('pre_password').value;
    const neo_word = document.getElementById('new_password').value;
    if (!isOKpsw(neo_word)) {
        alert('密码格式错误');
        return;
    }
    const p_password = md5(pre_word);
    const n_password = md5(neo_word);
    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'token': token
        },
        // 将请求体数据转换为 JSON 字符串
        body: JSON.stringify({
            pre_password: p_password,
            neo_password: n_password
        })
    })
        .then(response => {
            // 检查响应状态是否成功（状态码 200 - 299）
            if (!response.ok) {
                if(response.status==401) alert('原密码错误');
                throw new Error(response.status);
            }
            // 将响应数据解析为 JSON 格式
            return response.json();
        })
        .then(data => {
            alert('修改成功');
            console.log(data);
        })
        .catch(error => {
            // 处理请求过程中出现的错误
            console.error(error);
        });
};
const deleteOperation = () => {
    const { user_id, token } = getInfo();
    const password = md5(document.getElementById('password').value);
    //user/${user_id}/delete
    //https://apifoxmock.com/m2/5825455-5510878-default/259778606
    const apiUrl = `/api/user/${user_id}/delete`;
    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'token': token
        },
        body: JSON.stringify({
            user_password: password
        })
    })
        .then(response => {
            // 检查响应状态是否成功（状态码 200 - 299）
            if (!response.ok) {
                if(response.status==401) alert('密码错误');
                throw new Error(response.status);
            }
            // 将响应数据解析为 JSON 格式
            return response.json();
        })
        .then(data => {
            alert('注销成功');
        })
        .catch(error => {
            // 处理请求过程中出现的错误
            console.error(error);
        });    
}
</script>

<style scoped>
.container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background-color: white;
    width: 100%;
    height: 400px;
}



.left-section {
    margin: 50px;
    display: flex;
    flex-direction: column;
    width: 40%;
}

.right-section {
    margin: 50px;
    display: flex;
    flex-direction: column;
    width: 40%;
}

label {
    margin-bottom: 5px;
}

input {
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
}


button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    width: 100%;
    margin: 10px 0;
}

button:hover {
    background-color: #0056b3;
}
</style>