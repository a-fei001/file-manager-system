<template>
  <div class="user-layout">
    <LayoutHead />
    <div class="user-content">
      <div>用户首页</div>
    </div>
  </div>
</template>

<script setup>
import LayoutHead from '../layout/LayoutHead.vue'
</script>

<style scoped>
.user-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.user-content {
  flex: 1;
  overflow-y: auto;
  background-color: #f5f5f5;
  padding: 20px;
}
</style>
