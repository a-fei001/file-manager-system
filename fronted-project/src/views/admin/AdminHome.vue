<template>
  <div>
    <div>管理员首页</div>
    <button @click="handleLogout">退出登录</button>
    <div>
      <button @click="goToUser">用户管理</button>
      <button @click="goToManager">管理员管理</button>
      <button @click="goToDocuments">文档管理</button>
      <button @click="goToLogs">下载记录</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const handleLogout = () => {
  router.push('/login')
}

const goToUser = () => {
  router.push('/admin/user')
}

const goToManager = () => {
  router.push('/admin/manager')
}

const goToDocuments = () => {
  router.push('/admin/documents')
}

const goToLogs = () => {
  router.push('/admin/logs')
}
</script>

<style scoped>
</style>
