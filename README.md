# Java Intern Task Demo

一个面向 Java 后端实习求职的轻量级任务管理 Demo。项目重点不是复杂业务，而是展示基础后端工程能力：RESTful API、参数校验、统一响应、全局异常处理、分层结构、接口文档和可运行说明。

## 功能范围

- 任务列表查询、创建、更新状态、删除
- 基于内存数据的 Demo 数据存储，便于快速运行和面试演示
- 参数校验与统一错误返回
- 全局异常处理
- Swagger/OpenAPI 接口文档
- Dockerfile 与 docker-compose 示例

## 技术栈

- Java 17
- Spring Boot 3
- Spring MVC
- Jakarta Validation
- springdoc-openapi
- Docker / Docker Compose

## 快速启动

```bash
mvn spring-boot:run
```

启动后访问：

- API 根路径：http://localhost:8080
- Swagger UI：http://localhost:8080/swagger-ui/index.html

## 接口示例

### 查询任务

```bash
curl http://localhost:8080/api/tasks
```

### 创建任务

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d "{\"title\":\"Prepare GitHub README\",\"description\":\"Add run steps and screenshots\"}"
```

### 更新状态

```bash
curl -X PATCH "http://localhost:8080/api/tasks/1/status?status=DONE"
```

### 删除任务

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 项目说明

这个仓库用于展示第一份 Java 后端实习所需的基础能力。当前版本使用内存存储，后续可以扩展为 MySQL、MyBatis-Plus、JWT 登录、Redis 缓存和前端页面。

## 后续计划

- 接入 MySQL 和 MyBatis-Plus
- 增加 JWT 登录认证
- 增加 Redis 缓存或防重复提交示例
- 补充单元测试与接口测试
- 部署到云服务器或 Render/Railway 等平台
