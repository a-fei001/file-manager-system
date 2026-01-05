<template>
  <div class="admin-page">
    <div class="header-actions">
      <el-button type="primary" @click="handleAdd">新增管理员</el-button>
    </div>
    <el-table :data="adminList" border style="width: 100%" :header-cell-style="{ background: '#f5f7fa', color: '#606266' }">
      <el-table-column prop="id" label="用户ID" width="120" />
      <el-table-column prop="username" label="用户名称" width="180" />
      <el-table-column prop="password" label="用户密码" width="180" />
      <el-table-column prop="joinDate" label="入系统时间" width="220" :formatter="formatDate" />
      <el-table-column label="操作" width="300">
        <template #default="scope">
          <el-button type="text" @click="handleView(scope.row)">查看</el-button>
          <el-button type="text" @click="handleEdit(scope.row)">修改</el-button>
          <el-button type="text" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <el-dialog title="管理员详情" v-model="dialogVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="currentAdmin" label-width="120px">
        <el-form-item label="用户ID">
          <el-input :value="currentAdmin.id" disabled />
        </el-form-item>
        <el-form-item label="用户名称">
          <el-input :value="currentAdmin.username" disabled />
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input :value="currentAdmin.password" disabled />
        </el-form-item>
        <el-form-item label="角色">
          <el-input value="管理员" disabled />
        </el-form-item>
        <el-form-item label="入系统时间">
          <el-input :value="formatDate(currentAdmin)" disabled />
        </el-form-item>
        <el-form-item label="部门职称">
          <el-input :value="currentAdmin.description" disabled />
        </el-form-item>
        <el-form-item label="操作管理员ID">
          <el-input :value="currentAdmin.adminId" disabled />
        </el-form-item>
        <el-form-item label="操作管理员名称">
          <el-input :value="currentAdmin.adminName" disabled />
        </el-form-item>
      </el-form>
    </el-dialog>
    
    <el-dialog title="新增管理员" v-model="addDialogVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="newAdmin" label-width="120px" :rules="rules" ref="addFormRef">
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="newAdmin.username" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="newAdmin.password" type="password" placeholder="请输入用户密码" />
        </el-form-item>
        <el-form-item label="部门职称" prop="description">
          <el-input v-model="newAdmin.description" placeholder="请输入部门职称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddSubmit">确定</el-button>
      </template>
    </el-dialog>
    
    <el-dialog title="修改管理员" v-model="editDialogVisible" width="500px" :close-on-click-modal="false">
      <el-form :model="editAdmin" label-width="120px" :rules="rules" ref="editFormRef">
        <el-form-item label="用户ID">
          <el-input :value="editAdmin.id" disabled />
        </el-form-item>
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="editAdmin.username" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="editAdmin.password" placeholder="请输入用户密码" />
        </el-form-item>
        <el-form-item label="角色">
          <el-input value="管理员" disabled />
        </el-form-item>
        <el-form-item label="入系统时间">
          <el-input :value="formatDate(editAdmin)" disabled />
        </el-form-item>
        <el-form-item label="部门职称" prop="description">
          <el-input v-model="editAdmin.description" placeholder="请输入部门职称" />
        </el-form-item>
        <el-form-item label="操作管理员ID">
          <el-input :value="editAdmin.adminId" disabled />
        </el-form-item>
        <el-form-item label="操作管理员名称">
          <el-input :value="editAdmin.adminName" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEditSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import http from '@/utils/http'

const adminList = ref([])
const dialogVisible = ref(false)
const currentAdmin = ref({})
const addDialogVisible = ref(false)
const editDialogVisible = ref(false)
const newAdmin = ref({
  username: '',
  password: '',
  description: ''
})
const editAdmin = ref({
  id: '',
  username: '',
  password: '',
  description: '',
  role: '',
  joinDate: '',
  adminId: '',
  adminName: ''
})
const addFormRef = ref(null)
const editFormRef = ref(null)

const rules = {
  username: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
  password: [{ required: true, message: '请输入用户密码', trigger: 'blur' }],
  description: [{ required: true, message: '请输入部门职称', trigger: 'blur' }]
}

const formatDate = (row) => {
  if (row.joinDate) {
    return new Date(row.joinDate).toLocaleString()
  }
  return ''
}

const fetchAdmins = async () => {
  try {
    const response = await http.get('/admin/admin/list')
    if (response.code === 200) {
      adminList.value = response.data
    } else {
      ElMessage.error(response.message)
    }
  } catch {
    ElMessage.error('查询失败')
  }
}

const handleView = async (row) => {
  try {
    const response = await http.get(`/admin/admin/${row.id}`)
    if (response.code === 200) {
      currentAdmin.value = response.data
      dialogVisible.value = true
    } else {
      ElMessage.error(response.message)
    }
  } catch {
    ElMessage.error('查询失败')
  }
}

const handleDelete = async (id) => {
  try {
    const response = await http.delete(`/admin/admin/${id}`)
    if (response.code === 200) {
      ElMessage.success('删除成功')
      fetchAdmins()
    } else {
      ElMessage.error(response.message)
    }
  } catch {
    ElMessage.error('删除失败')
  }
}

const handleAdd = () => {
  newAdmin.value = {
    username: '',
    password: '',
    description: ''
  }
  addDialogVisible.value = true
}

const handleEdit = async (row) => {
  try {
    const response = await http.get(`/admin/admin/${row.id}`)
    if (response.code === 200) {
      editAdmin.value = response.data
      editDialogVisible.value = true
    } else {
      ElMessage.error(response.message)
    }
  } catch {
    ElMessage.error('查询失败')
  }
}

const handleAddSubmit = async () => {
  if (!addFormRef.value) return
  await addFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await http.post('/admin/admin', newAdmin.value)
        if (response.code === 200) {
          ElMessage.success('新增成功')
          addDialogVisible.value = false
          fetchAdmins()
        } else {
          ElMessage.error(response.message)
        }
      } catch {
        ElMessage.error('新增失败')
      }
    }
  })
}

const handleEditSubmit = async () => {
  if (!editFormRef.value) return
  await editFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await http.put('/admin/admin', editAdmin.value)
        if (response.code === 200) {
          ElMessage.success('修改成功')
          editDialogVisible.value = false
          fetchAdmins()
        } else {
          ElMessage.error(response.message)
        }
      } catch {
        ElMessage.error('修改失败')
      }
    }
  })
}

onMounted(() => {
  fetchAdmins()
})
</script>

<style scoped>
.admin-page {
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

:deep(.el-table) {
  font-size: 14px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
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

:deep(.el-form-item__label) {
  font-size: 14px;
  font-weight: 500;
  color: #24292e;
  line-height: 1.65;
}

:deep(.el-input__wrapper) {
  border-radius: 6px;
  box-shadow: 0 0 0 1px #e5e7eb inset;
  transition: all 0.2s ease-out;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #6366f1 inset;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #1e40af inset;
}
</style>
