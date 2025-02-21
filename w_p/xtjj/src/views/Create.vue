<template>
    <div>
        <DraftCreate/>

    </div>
  </template>
  
  <script setup>
import DraftCreate from '@/components/DraftCreate.vue';
import { ref } from 'vue';
const title = ref('');
const markdownContent = ref('');

  </script>
  
  <style scoped>
  </style>