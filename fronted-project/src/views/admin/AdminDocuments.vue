<template>
  <div class="admin-documents">
    <div class="header-actions">
      <el-button type="primary" plain @click="handleUpload">上传文件</el-button>
    </div>
    
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
        <el-table-column prop="createTime" label="上传时间" width="180" :formatter="formatDate" />
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="scope">
            <el-button type="primary" text @click="handleView(scope.row)">查看</el-button>
            <el-button type="primary" text @click="handleDownload(scope.row)">下载</el-button>
            <el-button type="danger" text @click="handleDelete(scope.row.id)">删除</el-button>
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
    
    <el-dialog title="上传文件" v-model="uploadDialogVisible" width="500px" :close-on-click-modal="false">
      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :limit="1"
        :on-change="handleFileChange"
        :on-exceed="handleExceed"
        drag
        action="#"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            只能上传单个文件，且文件名不能重复
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button @click="uploadDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUploadSubmit" :loading="uploading">确定</el-button>
      </template>
    </el-dialog>
    
    <el-dialog title="文档详情" v-model="viewDialogVisible" width="600px" :close-on-click-modal="false">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="文档ID">{{ currentDocument.id }}</el-descriptions-item>
        <el-descriptions-item label="文档名称">{{ currentDocument.fileName }}</el-descriptions-item>
        <el-descriptions-item label="文件类型">{{ currentDocument.fileType }}</el-descriptions-item>
        <el-descriptions-item label="存储桶">{{ currentDocument.bucketName }}</el-descriptions-item>
        <el-descriptions-item label="对象键">{{ currentDocument.objectKey }}</el-descriptions-item>
        <el-descriptions-item label="上传管理员ID">{{ currentDocument.adminId }}</el-descriptions-item>
        <el-descriptions-item label="上传管理员">{{ currentDocument.adminName }}</el-descriptions-item>
        <el-descriptions-item label="上传时间">{{ formatDate(currentDocument) }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled, Search, Document } from '@element-plus/icons-vue'
import http from '@/utils/http'

const queryParams = ref({
  fileName: '',
  fileType: '',
  pageNum: 1,
  pageSize: 10
})

const documentList = ref([])
const total = ref(0)
const loading = ref(false)
const uploadDialogVisible = ref(false)
const uploadRef = ref(null)
const uploading = ref(false)
const selectedFile = ref(null)
const viewDialogVisible = ref(false)
const currentDocument = ref({})

const handleSearch = async () => {
  loading.value = true
  try {
    const response = await http.get('/admin/document/page', {
      params: queryParams.value
    })
    if (response.code === 200) {
      documentList.value = response.data.items
      total.value = response.data.total
    } else {
      ElMessage.error(response.message)
    }
  } catch  {
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

const handleUpload = () => {
  uploadDialogVisible.value = true
  if (uploadRef.value) {
    uploadRef.value.clearFiles()
  }
  selectedFile.value = null
}

const handleFileChange = (file) => {
  selectedFile.value = file.raw
}

const handleExceed = () => {
  ElMessage.warning('只能上传一个文件')
}

const handleUploadSubmit = async () => {
  if (!selectedFile.value) {
    ElMessage.warning('请选择要上传的文件')
    return
  }
  
  uploading.value = true
  
  try {
    const formData = new FormData()
    formData.append('file', selectedFile.value)
    
    const response = await http.post('/admin/document/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    
    if (response.code === 200) {
      ElMessage.success('上传成功')
      uploadDialogVisible.value = false
      if (uploadRef.value) {
        uploadRef.value.clearFiles()
      }
      selectedFile.value = null
      handleSearch()
    } else {
      ElMessage.error(response.message || '上传失败')
    }
  } catch (error) {
    ElMessage.error(error.message || '上传失败')
  } finally {
    uploading.value = false
  }
}

const handleView = async (row) => {
  try {
    const response = await http.get(`/admin/document/${row.id}`)
    if (response.code === 200) {
      currentDocument.value = response.data
      viewDialogVisible.value = true
    } else {
      ElMessage.error(response.message)
    }
  } catch  {
    ElMessage.error('查询失败')
  }
}

const handleDownload = async (row) => {
  try {
    const response = await http.get(`/admin/document/download/${row.id}`, {
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

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除该文档吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const response = await http.delete(`/admin/document/${id}`)
    if (response.code === 200) {
      ElMessage.success('删除成功')
      handleSearch()
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.admin-documents {
  padding: 24px;
  max-width: 1280px;
  margin: 0 auto;
}

.header-actions {
  margin-bottom: 24px;
}

:deep(.el-button--primary) {
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease-out;
}

:deep(.el-button--primary:hover) {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(30, 64, 175, 0.15);
}

.filter-section {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
  align-items: center;
  background: #ffffff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
}

.filter-input {
  flex: 1;
  max-width: 300px;
}

.filter-input :deep(.el-input__wrapper) {
  border-radius: 6px;
  box-shadow: 0 0 0 1px #e5e7eb inset;
  transition: all 0.2s ease-out;
}

.filter-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #6366f1 inset;
}

.filter-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #1e40af inset;
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
  border-radius: 12px;
  overflow: hidden;
}

:deep(.el-table th) {
  background-color: #f9fafb;
  color: #24292e;
  font-weight: 600;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
  background-color: #f9fafb;
}

:deep(.el-table__body tr:hover > td) {
  background-color: #f3f4f6 !important;
}

:deep(.el-button--text) {
  padding: 4px 8px;
  border-radius: 6px;
  transition: all 0.2s ease-out;
}

:deep(.el-button--text:hover) {
  background-color: #f3f4f6;
}

:deep(.el-dialog) {
  border-radius: 12px;
}

:deep(.el-dialog__header) {
  padding: 24px 24px 16px;
  border-bottom: 1px solid #e5e7eb;
}

:deep(.el-dialog__body) {
  padding: 24px;
}

:deep(.el-dialog__footer) {
  padding: 16px 24px 24px;
  border-top: 1px solid #e5e7eb;
}

:deep(.el-upload-dragger) {
  border-radius: 12px;
  border: 2px dashed #e5e7eb;
  transition: all 0.2s ease-out;
}

:deep(.el-upload-dragger:hover) {
  border-color: #6366f1;
  background-color: #f9fafb;
}
</style>
