总则：非必要不修改全局配置。

一、后端规范
1. 分层架构  
- Controller：仅处理请求/响应，无业务逻辑。  
- Service：接口（service包）+ 实现类（service.impl包），Controller依赖接口。  
- Mapper：专用于数据访问。
2. 实体类  
- 表结构一致时直接用POJO；  
- 请求结构复杂 → dto 包；  
- 响应结构复杂 → vo 包。
3. 统一响应  
- 所有接口返回 Result：  
  - 成功：Result.success(message[, data])  
  - 错误：Result.error(message) 或 Result.error(code, message)  
- 状态码：200（成功）、400（客户端错误）、401（未登录）、403（无权限）、500（服务器错误）。
4. 接口路径  
- 登录：POST /login  
- 用户接口：/user/**  
- 管理员接口：/admin/**

二、前端规范
1. 技术栈  
Vue 3 + Vue Router + Axios + Element Plus；优先使用 ref。
2. 接口协议  
与后端一致：/login、/user/、/admin/。
3. 响应结构  
{ code: number, message: string, data?: any }
4. 请求/响应处理  
- 请求拦截器：自动携带 token、role、username（登录除外）；  
- 响应拦截器：401/403 自动跳转登录页并清空 localStorage；  
- 所有调用通过 http 工具类，必须 try-catch。

三、通用规范
1. 命名  
- 类：PascalCase（如 UserService）  
- 方法/变量：camelCase（如 getUserInfo）  
- 常量：UPPER_SNAKE_CASE（如 MAX_RETRY_COUNT）
2. 注释：勤加
3. 代码风格  
- 后端遵循 Java 规范；  
- 前端遵循 JavaScript 规范，启用 ESLint 检查。