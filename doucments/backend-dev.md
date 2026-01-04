1. 注意controller层只负责接收请求和返回响应，不负责业务逻辑的处理
2. 业务逻辑的处理应该在service层完成
3. Service层必须采用接口+实现类的设计模式：
   - Service接口定义在service包下，只定义方法签名
   - Service实现类定义在service.impl包下，实现具体业务逻辑
   - Controller层通过接口调用Service，不直接依赖实现类
4. 如果接收请求发送响应的实体类结构都和数据库中的表结构一致，不需要创建DTO和VO实体类
5. 如果接收请求的实体类结构复杂，在pojo中的dto包下创建对应的DTO实体类
6. 如果响应的实体类结构复杂，在pojo中的vo包下创建对应的VO实体类
7. 所有Controller接口必须使用Result统一响应类返回数据
8. Result类使用规范：
   - 成功响应：Result.success(message) 或 Result.success(message, data)
   - 错误响应：Result.error(message) 或 Result.error(code, message)
   - code字段：200表示成功，400表示客户端错误，401表示未登录，403表示无权限，500表示服务器错误
9. 接口协议规范：
   - 登录接口：POST /login
   - 普通用户相关接口：/user/**
   - 管理员相关接口：/admin/**