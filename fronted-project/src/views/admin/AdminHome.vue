<template>
  <div class="admin-home">
    <div class="welcome-card">
      <h1 class="welcome-title">欢迎回来</h1>
      <p class="welcome-subtitle">文件管理系统管理员控制台</p>
    </div>

    <div class="tech-stack-container">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <div class="tech-card">
            <div class="tech-card-header">
              <el-icon class="tech-icon" color="#409EFF"><Monitor /></el-icon>
              <h2 class="tech-title">前端技术栈</h2>
            </div>
            <div class="tech-list">
              <div class="tech-item" v-for="(item, index) in frontendStack" :key="index">
                <span class="tech-name">{{ item.name }}</span>
                <el-tag type="primary" size="small">{{ item.version }}</el-tag>
              </div>
            </div>
          </div>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <div class="tech-card">
            <div class="tech-card-header">
              <el-icon class="tech-icon" color="#67C23A"><Setting /></el-icon>
              <h2 class="tech-title">后端技术栈</h2>
            </div>
            <div class="tech-list">
              <div class="tech-item" v-for="(item, index) in backendStack" :key="index">
                <span class="tech-name">{{ item.name }}</span>
                <el-tag type="success" size="small">{{ item.version }}</el-tag>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Monitor, Setting } from '@element-plus/icons-vue'

const frontendStack = ref([
  { name: 'Vue', version: '3.5.26' },
  { name: 'Vue Router', version: '4.6.4' },
  { name: 'Pinia', version: '3.0.4' },
  { name: 'Element Plus', version: '2.13.0' },
  { name: 'Axios', version: '1.13.2' },
  { name: 'Vite', version: '7.3.0' },
  { name: 'ESLint', version: '9.39.2' }
])

const backendStack = ref([
  { name: 'Spring Boot', version: '3.5.9' },
  { name: 'Java', version: '21' },
  { name: 'MyBatis', version: '3.0.4' },
  { name: 'PostgreSQL Database', version: '-' },
  { name: 'JWT', version: '0.11.5' },
  { name: 'MinIO', version: '8.5.7' },
  { name: 'Lombok', version: 'Latest' }
])
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
  margin-bottom: 24px;
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

.tech-stack-container {
  margin-top: 24px;
}

.tech-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  height: 100%;
  transition: all 0.3s ease;
}

.tech-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  transform: translateY(-2px);
}

.tech-card-header {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e5e7eb;
}

.tech-icon {
  font-size: 28px;
  margin-right: 12px;
}

.tech-title {
  font-size: 20px;
  font-weight: 600;
  color: #24292e;
  margin: 0;
}

.tech-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.tech-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #f9fafb;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.tech-item:hover {
  background: #f3f4f6;
}

.tech-name {
  font-size: 14px;
  color: #374151;
  font-weight: 500;
}
</style>
