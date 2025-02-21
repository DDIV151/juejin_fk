<template>
    <div class="container">
        <div class="head">
            <input v-model="title" type="text" placeholder="请输入文章标题" class="title-input" />
            <div class="right-container">
                <button @click="save()" class="save-button">保存至草稿箱</button>
                <!--<button class="save-button" @click="publish()">发布</button>-->
                <button class="back-button" @click="back()">返回创作中心</button>
            </div>
        </div>
        <div class="button-container">
            <button @click="insertMarkdown('# ', '')">一级标题</button>
            <button @click="insertMarkdown('## ', '')">二级标题</button>
            <button @click="insertMarkdown('### ', '')">三级标题</button>
            <button @click="insertMarkdown('**', '**')">加粗</button>
            <button @click="insertMarkdown('*', '*')">斜体</button>
            <button @click="insertMarkdown('```', '```')">代码块</button>           
        </div>
        <!-- 文章撰写和展示区域 -->
        <div class="content-container">
            <!-- 文章撰写区域 -->
            <textarea v-model="markdownContent" placeholder="请使用 Markdown 语法撰写文章" class="markdown-editor"></textarea>
            <!-- 文章展示区域 -->
            <div v-html="parsedMarkdown" class="markdown-preview"></div>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { marked } from 'marked';
import hljs from 'highlight.js';
import 'highlight.js/styles/default.css';
import { useRouter } from 'vue-router';
import { getInfo } from './getInfo';

// 初始化 marked 配置，启用代码高亮
marked.setOptions({
    highlight: function (code, lang) {
        const language = hljs.getLanguage(lang) ? lang : 'plaintext';
        return hljs.highlight(code, { language }).value;
    },
});

// 定义响应式数据
const title = ref('');
const markdownContent = ref('');
const parsedMarkdown = ref('');

// 监听 markdownContent 的变化，实时更新解析后的 Markdown
watch(markdownContent, (newValue) => {
    parsedMarkdown.value = marked.parse(newValue);
});

// 插入 Markdown 语法的方法
const insertMarkdown = (start, end) => {
    const textarea = document.querySelector('.markdown-editor');
    const startPos = textarea.selectionStart;
    const endPos = textarea.selectionEnd;
    const selectedText = textarea.value.substring(startPos, endPos);
    textarea.value =
        textarea.value.substring(0, startPos) +
        start +
        selectedText +
        end +
        textarea.value.substring(endPos);
    textarea.selectionStart = startPos + start.length;
    textarea.selectionEnd = startPos + start.length + selectedText.length;
    textarea.focus();
    markdownContent.value = textarea.value;
};
const router = useRouter();
function save() {
    const {user_id,token}=getInfo();
    ///articles/publish
    //https://apifoxmock.com/m1/5825455-5510878-default/articles/publish
    fetch(`/api/articles/publish`, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'token': token // 添加自定义请求头
    },
    body: JSON.stringify({
        article_title: title.value,
        article_content:markdownContent.value,
        //user_id: user_id
    })
    })
    .then(response => {
        if(!response.ok){
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(data =>{
        console.log(data);
        alert('保存成功');
    })
    .catch(error => {
        console.error(error)
        alert('保存失败');
    });
}
function back() {
    router.push('/drafts');
}

  
</script>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: space-between;
    height: 100vh;
    width: 100vw;
}

.head {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: white;
}

.title-input {
    padding: 12px;
    font-size: 24px;
    border: none;
    outline: none;
    font-weight: bold;
    width: 100%;
}
.right-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}
.save-button {
    padding: 5px 15px;
    white-space: nowrap;
    margin: 0 10px;
    background-color: white;
    color: #007BFF;
    border: 1px solid #007BFF;
}
.save-button:hover {
    border: 2px solid #007BFF;
    padding: 4px 14px;
}
.back-button {
    padding: 6px 16px;
    white-space: nowrap;
    margin-right: 40px;
    margin-left: 10px;
    border: none;
    background-color: #2684ff;
    color: white
}
.back-button:hover{
    background-color: #007afc;
}
.button-container {
    display: flex;
    gap: 10px;
    padding: 10px;
    background-color: #f0f0f0;
}

.content-container {
    display: flex;
    flex: 1;
}

.markdown-editor {
    flex: 1;
    padding: 10px;
    font-size: 16px;
    border: none;
    outline: none;
    resize: none;
    background-color: #f8f9fa;
}

.markdown-preview {
    flex: 1;
    padding: 10px;
    border-left: 1px solid #ccc;
    overflow-y: auto;
    background-color: white;
}
</style>