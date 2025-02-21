
import './assets/layout.css'; // 引入新的布局样式
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'




const app = createApp(App)

app.use(router)

app.mount('#app')
