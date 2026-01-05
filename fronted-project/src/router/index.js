import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: () => import('../views/login/LogInIndex.vue')
    },
    {
      path: '/user',
      component: () => import('../views/user/UserIndex.vue')
    },
    {
      path: '/admin',
      component: () => import('../views/layout/AdminLayout.vue'),
      redirect: '/admin/home',
      children: [
        {
          path: 'home',
          component: () => import('../views/admin/AdminHome.vue')
        },
        {
          path: 'user',
          component: () => import('../views/admin/AdminUser.vue')
        },
        {
          path: 'manager',
          component: () => import('../views/admin/AdminManager.vue')
        },
        {
          path: 'documents',
          component: () => import('../views/admin/AdminDocuments.vue')
        }
      ]
    }
  ],
})

/**
 * 全局前置守卫
 * @description 进行权限验证，检查用户是否已登录以及是否有权限访问目标页面
 */
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (to.path === '/login') {
    if (token) {
      if (role === '1') {
        next('/admin/home')
      } else {
        next('/user')
      }
    } else {
      next()
    }
  } else if (to.path.startsWith('/admin')) {
    if (!token) {
      ElMessage.error('请先登录')
      next('/login')
    } else if (role !== '1') {
      ElMessage.error('无权访问管理员页面')
      next('/user')
    } else {
      next()
    }
  } else if (to.path.startsWith('/user')) {
    if (!token) {
      ElMessage.error('请先登录')
      next('/login')
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
