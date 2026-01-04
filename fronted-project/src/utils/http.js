import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

/**
 * 创建axios实例
 * @description 配置基础URL和超时时间
 */
const http = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

/**
 * axios请求拦截器
 * @description 在发送请求之前对请求配置进行处理，添加token和role
 * @param {Object} config - 请求配置对象
 * @returns {Object} 处理后的请求配置
 */
http.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')
  const username = localStorage.getItem('username')
  
  if (config.url !== '/login') {
    if (token) {
      config.headers['token'] = token
    }
    if (role) {
      config.headers['role'] = role
    }
    if (username) {
      config.headers['username'] = username
    }
  }
  
  if (config.data instanceof FormData) {
    delete config.headers['Content-Type']
  }
  
  return config
}, e => Promise.reject(e))

/**
 * axios响应拦截器
 * @description 处理响应数据，统一返回响应体中的data字段，处理错误信息
 * @param {Object} res - 响应对象
 * @returns {Object} 响应数据
 */
http.interceptors.response.use(res => {
  const { code, message } = res.data
  
  if (code === 200) {
    return res.data
  } else {
    ElMessage.error(message || '请求失败')
    return Promise.reject(new Error(message || '请求失败'))
  }
}, e => {
  if (e.response) {
    const { status, data } = e.response
    
    if (status === 401) {
      ElMessage.error('未登录或登录已过期')
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('username')
      router.push('/login')
    } else if (status === 403) {
      ElMessage.error('无权访问')
      router.push('/login')
    } else if (data && data.message) {
      ElMessage.error(data.message)
    } else {
      ElMessage.error('请求失败，请稍后重试')
    }
  } else {
    ElMessage.error('网络错误，请检查网络连接')
  }
  
  return Promise.reject(e)
})

export default http
