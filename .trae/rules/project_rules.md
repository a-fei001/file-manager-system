# 项目开发规范总览

本文档定义了文件管理系统的全局开发规范，所有开发人员必须严格遵守。

## 一、后端开发规范

### 1.1 分层架构规范
- **Controller层**：只负责接收请求和返回响应，不负责业务逻辑的处理
- **Service层**：负责业务逻辑的处理
  - 必须采用接口+实现类的设计模式
  - Service接口定义在service包下，只定义方法签名
  - Service实现类定义在service.impl包下，实现具体业务逻辑
  - Controller层通过接口调用Service，不直接依赖实现类
- **Mapper层**：负责数据访问操作

### 1.2 实体类规范
- 如果接收请求发送响应的实体类结构都和数据库中的表结构一致，不需要创建DTO和VO实体类
- 如果接收请求的实体类结构复杂，在pojo中的dto包下创建对应的DTO实体类
- 如果响应的实体类结构复杂，在pojo中的vo包下创建对应的VO实体类

### 1.3 统一响应规范
- 所有Controller接口必须使用Result统一响应类返回数据
- Result类使用规范：
  - 成功响应：Result.success(message) 或 Result.success(message, data)
  - 错误响应：Result.error(message) 或 Result.error(code, message)
  - code字段：200表示成功，400表示客户端错误，401表示未登录，403表示无权限，500表示服务器错误

### 1.4 接口协议规范
- 登录接口：POST /login
- 普通用户相关接口：/user/**
- 管理员相关接口：/admin/**

## 二、前端开发规范

### 2.1 技术栈
- Vue 3 + Vue Router + Axios + Element Plus
- 使用ref替代reactive进行响应式数据定义

### 2.2 接口协议规范
- 登录接口：POST /login
- 普通用户相关接口：/user/**
- 管理员相关接口：/admin/**

### 2.3 响应数据结构
后端统一使用Result响应类，前端接收的数据结构为：
```javascript
{
  code: number,    // 状态码：200成功，400客户端错误，401未登录，403无权限，500服务器错误
  message: string, // 响应消息
  data: any        // 响应数据（可选）
}
```

### 2.4 请求和响应处理规范
- **请求拦截器**：自动在请求头中添加token、role、username（登录接口除外）
- **响应拦截器**：统一处理错误状态码，401/403跳转到登录页并清除localStorage
- 所有接口调用通过http工具类，使用try-catch处理错误

## 三、通用规范

### 3.1 命名规范
- 类名使用大驼峰命名法（PascalCase）
- 方法名和变量名使用小驼峰命名法（camelCase）
- 常量使用全大写下划线分隔（UPPER_SNAKE_CASE）

### 3.2 注释规范
- 所有类必须添加类级别注释，说明类的用途
- 所有公共方法必须添加方法注释，说明参数和返回值
- 复杂业务逻辑必须添加行内注释说明

### 3.3 代码风格
- 遵循Java代码规范（后端）
- 遵循JavaScript代码规范（前端）
- 使用ESLint进行代码检查（前端）
