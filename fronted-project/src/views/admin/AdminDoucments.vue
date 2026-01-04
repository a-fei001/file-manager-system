<template>
  <div class="admin-page">
    <div class="header-actions">
      <el-button type="primary" @click="handleUpload">上传文件</el-button>
    </div>
    
    <el-dialog title="上传文件" v-model="uploadDialogVisible" width="500px" :close-on-click-modal="false">
      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :on-change="handleFileChange"
        :limit="1"
        drag
        action="#"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
      </el-upload>
      <template #footer>
        <el-button @click="uploadDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleUploadSubmit" :loading="uploading">确定</el-button>
      </template>
    </el-dialog>
    
    <div v-if="fileUrl" class="file-result">
      <div>文件上传成功！</div>
      <div class="file-url">
        <span>访问地址：</span>
        <a :href="fileUrl" target="_blank">{{ fileUrl }}</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import http from '@/utils/http'

const uploadDialogVisible = ref(false)
const uploadRef = ref(null)
const uploading = ref(false)
const fileUrl = ref('')
const selectedFile = ref(null)

const handleUpload = () => {
  uploadDialogVisible.value = true
  fileUrl.value = ''
}

const handleFileChange = (file, fileList) => {
  console.log('选择的文件:', file)
  console.log('文件列表:', fileList)
  selectedFile.value = file.raw
}

const handleUploadSubmit = async () => {
  if (!selectedFile.value) {
    ElMessage.warning('请选择要上传的文件')
    return
  }

  const formData = new FormData()
  formData.append('file', selectedFile.value)

  uploading.value = true
  try {
    const response = await http.post('/admin/doc/upload', formData)
    if (response.code === 200) {
      ElMessage.success('上传成功')
      fileUrl.value = response.data
      uploadDialogVisible.value = false
      selectedFile.value = null
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('上传失败')
    console.error('上传失败:', error)
  } finally {
    uploading.value = false
  }
}
</script>

<style scoped>
.admin-page {
  padding: 24px;
}

.header-actions {
  margin-bottom: 20px;
}

.file-result {
  margin-top: 20px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
}

.file-url {
  margin-top: 10px;
  word-break: break-all;
}

.file-url a {
  color: #409eff;
  text-decoration: none;
}

.file-url a:hover {
  text-decoration: underline;
}
</style>
