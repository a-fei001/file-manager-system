<template>
  <div class="layout-head">
    <div class="head-left">文档管理系统</div>
    <div class="head-right">
      <div class="user-info">
        <span>{{ username }}</span>
        <span class="role-text">({{ roleText }})</span>
      </div>
      <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const router = useRouter()

const username = ref(localStorage.getItem('username') || '')
const role = ref(localStorage.getItem('role') || '')

const roleText = computed(() => {
  return role.value === '1' ? '管理员' : '普通用户'
})

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('username')
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.layout-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
  padding: 0 24px;
  backdrop-filter: blur(12px);
  background-color: rgba(255, 255, 255, 0.8);
}

.head-left {
  font-size: 20px;
  font-weight: 600;
  color: #24292e;
  letter-spacing: -0.02em;
}

.head-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-info {
  font-size: 14px;
  color: #24292e;
  display: flex;
  align-items: center;
  gap: 8px;
}

.role-text {
  color: #6b7280;
  opacity: 0.65;
  font-size: 13px;
}

:deep(.el-button--danger) {
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease-out;
}

:deep(.el-button--danger:hover) {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(220, 38, 38, 0.15);
}
</style>
