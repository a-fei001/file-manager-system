<template>
  <div class="admin-layout">
    <LayoutHead />
    <div class="admin-layout-body">
      <LayoutNavi />
      <div class="admin-content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import LayoutHead from './LayoutHead.vue'
import LayoutNavi from './LayoutNavi.vue'
</script>

<style scoped>
.admin-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.admin-layout-body {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.admin-content {
  flex: 1;
  overflow-y: auto;
  background-color: #f5f5f5;
}
</style>
