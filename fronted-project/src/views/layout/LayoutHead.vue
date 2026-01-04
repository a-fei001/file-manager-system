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
  height: 60px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 0 20px;
}

.head-left {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.head-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  font-size: 14px;
  color: #606266;
}

.role-text {
  margin-left: 5px;
  color: #409eff;
}
</style>
