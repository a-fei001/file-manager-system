<template>
  <div class="layout-navi" :class="{ 'is-collapsed': isCollapsed }">
    <div class="navi-toggle" @click="toggleCollapse">
      <el-icon>
        <component :is="isCollapsed ? 'Expand' : 'Fold'" />
      </el-icon>
      <span v-if="!isCollapsed" class="toggle-text"></span>
      <span v-else class="toggle-text"></span>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapsed"
      background-color="#ffffff"
      text-color="#24292e"
      active-text-color="#1e40af"
      router
    >
      <el-menu-item index="/admin/home">
        <el-icon><HomeFilled /></el-icon>
        <template #title>首页</template>
      </el-menu-item>
      <el-menu-item index="/admin/user">
        <el-icon><User /></el-icon>
        <template #title>用户管理</template>
      </el-menu-item>
      <el-menu-item index="/admin/manager">
        <el-icon><UserFilled /></el-icon>
        <template #title>管理员管理</template>
      </el-menu-item>
      <el-menu-item index="/admin/documents">
        <el-icon><Document /></el-icon>
        <template #title>文档管理</template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { HomeFilled, User, UserFilled, Document } from '@element-plus/icons-vue'

const route = useRoute()

const isCollapsed = ref(false)

const activeMenu = computed(() => route.path)

const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
}
</script>

<style scoped>
.layout-navi {
  width: 200px;
  height: 100%;
  background-color: #ffffff;
  transition: width 0.2s ease-out;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #e5e7eb;
}

.layout-navi.is-collapsed {
  width: 64px;
}

.navi-toggle {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 8px;
  cursor: pointer;
  color: #24292e;
  transition: all 0.2s ease-out;
  background-color: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  font-size: 18px;
  padding-left: 20px;
}

.toggle-text {
  font-size: 14px;
  white-space: nowrap;
}

.navi-toggle:hover {
  background-color: #f9fafb;
  color: #1e40af;
}

.el-menu {
  border-right: none;
  flex: 1;
  border-left: none;
}

:deep(.el-menu-item) {
  border-radius: 6px;
  margin: 4px 8px;
  transition: all 0.2s ease-out;
}

:deep(.el-menu-item:hover) {
  background-color: #f9fafb;
  color: #1e40af;
}

:deep(.el-menu-item.is-active) {
  background-color: #eff6ff;
  color: #1e40af;
  font-weight: 500;
}
</style>
