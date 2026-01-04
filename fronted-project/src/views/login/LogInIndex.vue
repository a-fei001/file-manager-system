<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1 class="login-title">欢迎登录</h1>
        <p class="login-subtitle">文件管理系统</p>
      </div>

      <el-form :model="loginForm" class="login-form" label-position="top">
        <el-form-item label="用户名">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            size="medium"
            clearable
          />
        </el-form-item>

        <el-form-item label="密码">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="medium"
            show-password
            clearable
          />
        </el-form-item>

        <div class="login-actions">
          <el-button type="primary" plain class="login-button" @click="handleLogin">
            登录
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import http from '../../utils/http'

const router = useRouter()

const loginForm = ref({
  username: '',
  password: ''
})

const handleLogin = async () => {
  if (!loginForm.value.username || !loginForm.value.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  try {
    const res = await http.post('/login', {
      username: loginForm.value.username,
      password: loginForm.value.password
    })

    if (res.code === 200) {
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('role', res.data.role)
      localStorage.setItem('username', res.data.username)
      ElMessage.success('登录成功')

      if (res.data.role === 1) {
        router.push('/admin/home')
      } else {
        router.push('/user')
      }
    }
  } catch (error) {
    console.error('登录失败:', error)
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f2 50%, #d9e2ec 100%);
  position: relative;
  overflow: hidden;
}

.login-container::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle at 30% 30%, rgba(99, 102, 241, 0.08) 0%, transparent 50%),
              radial-gradient(circle at 70% 70%, rgba(30, 64, 175, 0.06) 0%, transparent 50%);
  animation: gradientShift 20s ease-in-out infinite;
}

@keyframes gradientShift {
  0%, 100% {
    transform: translate(0, 0) rotate(0deg);
  }
  33% {
    transform: translate(2%, 2%) rotate(1deg);
  }
  66% {
    transform: translate(-1%, 1%) rotate(-1deg);
  }
}

.login-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(12px);
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08),
              0 1px 3px rgba(0, 0, 0, 0.06);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  color: #24292e;
  margin: 0 0 8px 0;
  line-height: 1.4;
}

.login-subtitle {
  font-size: 14px;
  color: #6b7280;
  opacity: 0.65;
  margin: 0;
  line-height: 1.65;
}

.login-form {
  margin-top: 24px;
}

.login-form :deep(.el-form-item) {
  margin-bottom: 24px;
}

.login-form :deep(.el-form-item__label) {
  font-size: 14px;
  font-weight: 500;
  color: #24292e;
  line-height: 1.65;
  padding-bottom: 8px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 6px;
  padding: 8px 16px;
  transition: all 0.2s ease-out;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #6366f1 inset;
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #1e40af inset;
}

.login-actions {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 32px;
}

.login-button {
  width: 100%;
  height: 40px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease-out;
}

.login-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(30, 64, 175, 0.15);
}
</style>
