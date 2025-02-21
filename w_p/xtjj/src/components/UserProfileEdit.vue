<template>
    <div class="container">
        <div class="edit-container">
            <div class="left-section">
                <label for="username">用户名：</label>
                <input id="username"  type="text" placeholder="请输入新的用户名" />
            </div>
            <div class="right-section">
                <label for="image-upload">上传头像：</label>
                <input id="image-upload" type="file" @change="handleImageUpload" accept="image/*"/>
                <img v-if="imageUrl" :src="imageUrl" alt="上传的头像" class="preview-image" />
                <ConfirmDialog :visible="dialog" :message="'是否保存修改？'"  @confirm="handleConfirm" @cancel="handleCancel"/>
            </div>
            <!-- 底部：保存修改按钮 -->
        </div>
        <div>
            <button @click="saveChanges">保存修改</button>
        </div>
    </div>
</template>

<script setup>
  import { ref } from 'vue';
import ConfirmDialog from './ConfirmDialog.vue';
import { getInfo } from './getInfo';
  
  // 定义响应式数据
  //const username = ref('');
  const selectedFile = ref(null);
  const imageUrl = ref('');
  const dialog = ref(false);
  
  // 处理图像上传事件
  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      selectedFile.value = file;
      const reader = new FileReader();
      reader.onload = (e) => {
        imageUrl.value = e.target.result;
      };
      reader.readAsDataURL(file);
    }
    else{
      imageUrl.value = '';
    }
  };
  //确认保存
  const handleConfirm = () => {
    alert('确认保存');
    changeName();
    changeAvatar();
    dialog.value = false;
  }
  // 取消保存
  const handleCancel = () => {
    alert('取消保存');
    dialog.value = false;
  }
  
  // 点击保存修改按钮
  const saveChanges = () => {
    dialog.value = true;
  };

  function isOKname(str) {
  return str.length<12&&str.length>0;
}
//修改用户名
const changeName = () => {
  const {user_id,token}=getInfo();
  const newUsername = document.getElementById('username').value;
  if (newUsername === '') return;
  if (!isOKname(newUsername)) {
    alert('用户名长度应在1-12之间');
    return;
  }
  ///api/users/${user_id}/username
  fetch(`/api/users/${user_id}/username`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'token': token
    },
    body: JSON.stringify({
      user_name: newUsername
    })
  })
    .then(response => {
      if (!response.ok) {
        if(response.status===409) alert('用户名已存在');
        throw new Error(response.status);
      }
      return response.json();
    })
    .then(data => {
      alert('用户名修改成功');
      const userdata = localStorage.getItem('xtjj_userdata');
      const dataObj = JSON.parse(userdata);
      dataObj.user_name = newUsername;
      localStorage.setItem('xtjj_userdata', JSON.stringify(dataObj));
    })
    .catch(error => {
      console.log(error.message);
    })
}
//修改头像
const changeAvatar = () => {
  if(imageUrl.value==='') return;
  const {user_id,token}=getInfo();
  ///api/users/${user_id}/image/upload
  //https://apifoxmock.com/m1/5825455-5510878-default/users/1/image/upload
  fetch(`/api/users/${user_id}/image/upload`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'token': token
    },
    body: JSON.stringify({
      avater_base64: imageUrl.value
    })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('上传失败');
    }
    return response.json();
  })
  .then(data => {
    alert('头像上传成功');
  })
  .catch(error => {
    console.error(error);
  })
}
</script>

<style scoped>
  .container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background-color: white;
    width:100%;
    height: 400px;
  }
  
  .edit-container {
    display: flex;
    justify-content: space-around;
    width: 100%;
    margin-bottom: 20px;
  }
  
  .left-section{
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
  
  input[type="text"],
  input[type="file"] {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .preview-image {
    width:160px;
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
  }
  
  button:hover {
    background-color: #0056b3;
  }
  </style>