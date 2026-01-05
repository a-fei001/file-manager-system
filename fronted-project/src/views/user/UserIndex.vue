<template>
  <div class="user-documents">
    <LayoutHead />
    <div class="content-container">
      <div class="filter-section">
        <el-input
          v-model="queryParams.fileName"
          placeholder="搜索文件名称"
          clearable
          class="filter-input"
          @clear="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-input
          v-model="queryParams.fileType"
          placeholder="文件类型"
          clearable
          class="filter-input"
          @clear="handleSearch"
        >
          <template #prefix>
            <el-icon><Document /></el-icon>
          </template>
        </el-input>
        <el-input
          v-model="queryParams.adminName"
          placeholder="管理员名称"
          clearable
          class="filter-input"
          @clear="handleSearch"
        >
          <template #prefix>
            <el-icon><User /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <div class="table-section">
        <el-table
          :data="documentList"
          border
          stripe
          style="width: 100%"
          :header-cell-style="{ background: '#f9fafb', color: '#24292e', fontWeight: '600' }"
          v-loading="loading"
        >
          <el-table-column prop="fileName" label="文档名称" min-width="200" />
          <el-table-column prop="fileType" label="类型" width="100" />
          <el-table-column prop="adminName" label="管理员名称" width="150" />
          <el-table-column prop="createTime" label="上传时间" width="180" :formatter="formatDate" />
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="scope">
              <el-button type="primary" text @click="handleDownload(scope.row)">下载</el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <div class="pagination-section">
          <el-pagination
            v-model:current-page="queryParams.pageNum"
            v-model:page-size="queryParams.pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSearch"
            @current-change="handleSearch"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Document, User } from '@element-plus/icons-vue'
import http from '@/utils/http'
import LayoutHead from '@/views/layout/LayoutHead.vue'

const queryParams = ref({
  fileName: '',
  fileType: '',
  adminName: '',
  pageNum: 1,
  pageSize: 10
})

const documentList = ref([])
const total = ref(0)
const loading = ref(false)

const handleSearch = async () => {
  loading.value = true
  try {
    const response = await http.get('/user/document/page', {
      params: queryParams.value
    })
    if (response.code === 200) {
      documentList.value = response.data.items
      total.value = response.data.total
    } else {
      ElMessage.error(response.message)
    }
  } catch {
    ElMessage.error('查询失败')
  } finally {
    loading.value = false
  }
}

const formatDate = (row) => {
  if (row.createTime) {
    return new Date(row.createTime).toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    })
  }
  return ''
}

const handleDownload = async (row) => {
  try {
    const response = await http.get(`/user/document/download/${row.id}`, {
      responseType: 'blob'
    })
    
    const blob = new Blob([response])
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = row.fileName
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
    
    ElMessage.success('下载成功')
  } catch {
    ElMessage.error('下载失败')
  }
}

onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.user-documents {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
}

.content-container {
  flex: 1;
  padding: 24px;
  max-width: 1280px;
  margin: 0 auto;
  width: 100%;
}

.filter-section {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
  align-items: center;
  background: #ffffff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

.filter-input {
  flex: 1;
  max-width: 300px;
}

.table-section {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

.pagination-section {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table th) {
  background-color: #f9fafb;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
  background-color: #f9fafb;
}

:deep(.el-table__body tr:hover > td) {
  background-color: #f3f4f6 !important;
}

:deep(.el-button--text) {
  padding: 0 8px;
}
</style>
