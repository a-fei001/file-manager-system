<template>
  <div>
    <div>用户首页</div>
    <button @click="handleLogout">退出登录</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const handleLogout = () => {
  router.push('/login')
}
</script>

<style scoped>
</style>
