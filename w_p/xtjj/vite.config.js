import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    //vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
    // 配置前端服务地址和端口
    server: {
      host: '192.168.93.233',
      port: 5173,
      // 是否开启 https
      https: false,
        // 设置反向代理，跨域
      proxy: {
        '/api': {
          // 后台地址
          target: 'http://47.122.63.100:8080/',
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, '')
        }
      }
    },
})

/*module.exports = {
  devServer: {
      proxy: {
          '/register': {
              target: 'http://47.122.63.100:8080',
              changeOrigin: true
          }
      }
  }
};*/
