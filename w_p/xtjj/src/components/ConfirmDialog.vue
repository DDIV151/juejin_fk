<template>
    <div v-if="visible" class="confirm-dialog-overlay">
      <div class="confirm-dialog">
        <div class="confirm-dialog-content">{{ message }}</div>
        <div class="confirm-dialog-buttons">
          <button @click="onCancel">取消</button>
          <button @click="onConfirm">确定</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, defineProps, defineEmits } from 'vue';
  
  // 定义组件接收的属性
  const props = defineProps({
    visible: {
      type: Boolean,
      default: false
    },
    message: {
      type: String,
      default: ''
    }
  });
  
  // 定义组件触发的事件
  const emits = defineEmits(['confirm', 'cancel']);
  
  // 取消按钮点击事件处理函数
  const onCancel = () => {
    emits('cancel');
  };
  
  // 确定按钮点击事件处理函数
  const onConfirm = () => {
    emits('confirm');
  };
  </script>
  
  <style scoped>
  .confirm-dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .confirm-dialog {
    background-color: white;
    padding: 40px 60px;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  .confirm-dialog-buttons {
    margin-top: 20px;
    text-align: center;
  }
  
  .confirm-dialog-buttons button {
    margin-left: 10px;
  }
  </style>