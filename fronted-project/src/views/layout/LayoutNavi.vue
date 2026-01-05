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
      background-color="#001529"
      text-color="#fff"
      active-text-color="#409eff"
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
  background-color: #001529;
  transition: width 0.3s;
  display: flex;
  flex-direction: column;
}

.layout-navi.is-collapsed {
  width: 64px;
}

.navi-toggle {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 8px;
  cursor: pointer;
  color: #fff;
  transition: all 0.3s;
  background-color: #1f2937;
  border-bottom: 1px solid #304156;
  font-size: 18px;
  padding-left: 20px;
}

.toggle-text {
  font-size: 14px;
  white-space: nowrap;
}

.navi-toggle:hover {
  background-color: #409eff;
  color: #fff;
}

.el-menu {
  border-right: none;
  flex: 1;
  border-left: none;
}
</style>
