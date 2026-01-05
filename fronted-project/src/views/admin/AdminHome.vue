<template>
  <div class="admin-home">
    <div class="welcome-card">
      <h1 class="welcome-title">欢迎回来</h1>
      <p class="welcome-subtitle">文件管理系统管理员控制台</p>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.admin-home {
  padding: 24px;
  max-width: 1280px;
  margin: 0 auto;
}

.welcome-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 48px 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  text-align: center;
}

.welcome-title {
  font-size: 32px;
  font-weight: 600;
  color: #24292e;
  margin: 0 0 16px 0;
  line-height: 1.4;
}

.welcome-subtitle {
  font-size: 16px;
  color: #6b7280;
  opacity: 0.65;
  margin: 0;
  line-height: 1.65;
}
</style>
