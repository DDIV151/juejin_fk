<template>
    <div class="left">
        <div class="icon">
            <div class="like"><img :src="likeIcon" class="like-icon" @click="like()" /></div>
            <div class="badge">{{ detail.article_likes-(prelike?1:0)+(islike?1:0) }}</div>
        </div>
        <div>
            <div class="comment"><img :src="commentIcon" class="comment-icon" /></div>
        </div>
    </div>
    <div class="container">
        <div v-if="loading">加载中...</div>
        <div v-else-if="error">加载数据出错：{{ error }}</div>
        <div v-else>
            <div class="title">
                <h1>{{ detail.article_title }}</h1>
            </div>
            <div class="info">
                <span class="info_content">{{ detail.user_name }}</span>
                <span class="info_content">{{ detail.article_date }}</span>
                <span class="info_content"><img :src='eyeIcon' class="eye-icon" />{{ detail.article_views }}</span>
            </div>
            <div id="content" class="content" v-html="markedContent"></div>
        </div>
    </div>
    <div class="bottom">
        <div class="edit-container">
            <textarea class="input-a" placeholder="输入评论..." type="text" id="comment"></textarea>
            <div class="down"><button class="button-a" @click="submitComment">发布</button></div>
        </div>
        <div class="comments">
            <div class="comment-item" v-for="comment in comments" >
                <div class="left-session">
                    <img :src="comment.user_image" class="avatar" />
                </div>
                <div class="right-session">
                    <div class="comment-user">{{ comment.user_name }}</div>
                    <div class="comment-content">{{ comment.comment_content }}</div>
                    <div class="comment-info">
                        <div class="likes"><img class="zang-icon" :src="zangIcon"/>{{ comment.comment_likes }}</div>
                        <div class="delete-button" @click="deleteComment(comment.comment_id)">删除</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref,onUnmounted } from 'vue';
import { marked } from 'marked';
import { useRoute } from 'vue-router';
import eye from '@/assets/icons/eye_gray.svg';
import like_gray from '@/assets/icons/love_gray.png';
import like_black from '@/assets/icons/love_black.png';
import like_blue from '@/assets/icons/love_blue.png';
import comment_gray from '@/assets/icons/comment_gray.png';
import comment_black from '@/assets/icons/comment_black.png';
import zang_gray from '@/assets/icons/like_gray.svg';
import { getInfo } from './getInfo';
import { computed } from 'vue';
const eyeIcon = eye;
const commentIcon = comment_gray;
const zangIcon = zang_gray;

const route = useRoute();
const detail = ref({});
const comments = ref([]);
const markedContent = ref('');
const loading = ref(true);
const error = ref(null);

onMounted(() => {
    const article_id = route.params.article_id; // 获取文章ID
    ///api/articles/${article_id}
    //https://apifoxmock.com/m1/5825455-5510878-default/articles/1
    const {token}=getInfo();
    fetch(`/api/articles/${article_id}`, {
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            'token':token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(data => {
        loading.value = false;
        console.log(data);
        detail.value = data.data;
        markedContent.value = marked.parse(detail.value.article_content);
    })
    .catch(error => {
        error.value = error.message;
        console.error(error);
    });  
    ///api/{article_id}/comment
    //https://apifoxmock.com/m1/5825455-5510878-default/${article_id}/comment
    fetch(`/api/${article_id}/comment`, {
        method: 'GET',
        headers:{
            'Content-Type': 'application/json',
            'token':token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        comments.value = data.data;
    })
    .catch(err => {
        error.value = err.message;
        loading.value = false;
        console.error(err);
    });
});

const likeIcon =computed(()=>{
    if(detail.value.is_star){
        return like_blue;
    }
    return like_gray;
})

const like=(()=>{
    ///api/articles/${article_id}/star
    // 获取文章ID
    const article_id = route.params.article_id; 
    const {user_id,token}=getInfo();
    if(detail.value.is_star){
        ///api/articles/${article_id}/star
        fetch(`/api/articles/${article_id}/star`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'token':token
        }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            alert('取消点赞成功');
            //重新加载
            location.reload();
        })
        .catch(err => {
            console.error(err);
            alert('取消点赞失败');
        })
    }
    else{
        fetch(`/api/articles/${article_id}/star`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'token':token
        }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            alert('点赞成功');
            location.reload();
        })
        .catch(err => {
            console.error(err);
            alert('点赞失败');
        })
    }
})
function submitComment() {
    ///{article_id}/comment
    const article_id = route.params.article_id; // 获取文章ID
    const {user_id,token}=getInfo();
    const comment=document.getElementById('comment').value;
    fetch(`/api/${article_id}/comment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'token':token
        },
        body: JSON.stringify({
            comment_content: comment
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        // 更新评论列表
        comments.value.push(data.data);
        document.getElementById('comment').value = ''; // 清空评论输入框
    })
    .catch(err => {
        console.error(err);
    })
}


function deleteComment(comment_id){
    ///api/${comment_id}/comment
    const article_id = route.params.article_id; // 获取文章ID
    const {user_id,token}=getInfo();
    const comment=document.getElementById('comment').value;
    fetch(`/api/${comment_id}/comment`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'token':token
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        alert('删除成功');
    })
    .catch(err => {
        console.error(err);
        alert('删除失败');
    })
}
</script>

<style scoped>
.left {
    margin-top: 20px;
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    left: -80px;
}
.icon{
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: flex-start;
}
.like {
    background-color: white;
    width: 45px;
    height: 45px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 30px;
}
.badge {
    background-color: #c2c8d1;
    border-radius: 10px;
    padding: 1.5px 4px;
    font-size: 12px;
    color:white;
    margin-left: -8px;
}
.like-icon {
    width: 30px;
}
.comment {
    background-color: white;
    width: 45px;
    height: 45px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);   
}
.comment-icon {
    width: 26px;
}
.container {
    width: 100%;
    background-color: white;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    padding: 20px;
    border-radius: 2px;
}
.title {
    margin:5px 0;
}
.info {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    margin-bottom: 30px;
    color: #777;
    align-items: center;
}
.info_content {
    display: flex;
    align-items: center;
    margin-right: 20px;
}
.eye-icon {
  width: 20.8px;
  margin: 0 1px;
}
.bottom{
    margin-top:40px ;
    width: 100%;
    background-color: white;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
    padding: 20px;
    border-radius: 2px;   
}
.edit-container{
    position: relative;
    width: 100%;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-end;
    min-height: 90px;
}
.input-a{
    width: 100%;
    height: 100%;
    border-radius: 5px;
    height: 90px;
    text-align: left;
    vertical-align: top;
    /* 禁止用户手动调整文本框大小 */
    resize: none;
    /* 防止文本溢出 */
    overflow: auto;
    /* 内边距设置，避免文字紧贴边框 */
    padding: 5px;
    box-sizing: border-box;
    font-family: 'Microsoft YaHei';
}
.input-a:focus{
    outline: none;
    border: 1px solid #007afc;
}
.button-a{
    position: absolute;
    bottom: 5px;
    right: 5px;
    padding: 6px 16px;
    white-space: nowrap;
    border: none;
    background-color: #2684ff;
    color: white;
    border-radius: 5px;
}

.button-a:hover{
    background-color: #007afc;
}
.comments {
    margin-top: 10px;
    width: 100%;
    display: flex;
    flex-direction: column;
}
.comment-item {
    display: flex;
    flex-direction: row;
    margin: 10px 0;

}
.left-session {
    margin:0 10px;
}
.avatar {
    width: 40px;
    border-radius: 50%;
}
.right-session {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: stretch;
    width: 100%;
}
.comment-user {
    font-size: 14px;
    color: #777;
}
.comment-content {
    padding: 5px 0;
}
.comment-info {
    font-size: 14px;
    color: #777;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
}
.zang-icon {
    width: 16px;
    margin: 0 2px;
}
.delete-button {
    margin-right: 40px;
}
.delete-button:hover {
    color: red;
}
</style>