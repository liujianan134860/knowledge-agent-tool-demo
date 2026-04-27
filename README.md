# 智能项目协同管理平台

一个面向 Java 后端实习求职的全栈后台作品集项目。项目围绕“项目协作、任务流转、文件记录、操作审计、接口文档和容器化部署”展开，重点展示 Spring Boot 后端分层设计、认证鉴权、统一响应、参数校验、异常处理和可演示交付能力。

> 当前仓库提供可直接运行的后端版本，内置演示数据和轻量 JWT 实现，便于面试官快速启动、登录和调用接口。代码结构保留 Repository/Service/Controller 边界，可平滑替换为 MySQL + MyBatis-Plus + Redis 的真实持久化实现。

## 项目亮点

- 登录认证：提供演示账号登录，签发 HMAC-SHA256 JWT，使用拦截器保护 `/api/**` 接口。
- 权限模型：内置 `ADMIN`、`MANAGER`、`MEMBER` 三类角色，为后续按钮级权限和菜单渲染保留扩展点。
- 项目管理：支持项目列表、项目创建、项目状态流转。
- 任务协同：支持任务创建、条件筛选、负责人筛选、状态流转、优先级和截止时间。
- 文件记录：模拟文件上传元数据校验，记录项目文件、上传人、大小和类型。
- 操作审计：关键业务动作自动写入审计日志，支持按动作和操作人查询。
- 仪表盘：聚合项目数、任务数、任务状态分布和近期操作日志数量。
- 工程化：统一响应、全局异常处理、参数校验、Swagger/OpenAPI、Dockerfile、Docker Compose、Nginx 反向代理配置。

## 技术栈

- Java 17
- Spring Boot 3
- Spring MVC
- Jakarta Validation
- springdoc-openapi
- JWT / HMAC-SHA256
- Docker / Docker Compose
- MySQL、Redis、Nginx 容器编排示例

## 快速启动

```bash
mvn spring-boot:run
```

启动后访问：

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 演示账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `admin123` |
| 项目负责人 | `manager` | `manager123` |
| 成员 | `member` | `member123` |

## 接口示例

### 登录获取 Token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

将返回的 `token` 放入后续请求头：

```bash
Authorization: Bearer <token>
```

### 查询仪表盘

```bash
curl http://localhost:8080/api/dashboard/summary \
  -H "Authorization: Bearer <token>"
```

### 创建项目

```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"简历投递后台\",\"description\":\"管理项目、任务和审计日志\"}"
```

### 创建任务

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d "{\"projectId\":1,\"title\":\"完善接口文档\",\"description\":\"补充 Swagger 和 README 示例\",\"assigneeId\":2,\"priority\":\"HIGH\",\"dueDate\":\"2026-05-10\"}"
```

### 条件查询任务

```bash
curl "http://localhost:8080/api/tasks?projectId=1&status=REVIEW" \
  -H "Authorization: Bearer <token>"
```

### 上传文件记录

```bash
curl -X POST http://localhost:8080/api/files \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d "{\"projectId\":1,\"filename\":\"api-design.md\",\"contentType\":\"text/markdown\",\"size\":2048}"
```

### 查询操作日志

```bash
curl http://localhost:8080/api/audit-logs \
  -H "Authorization: Bearer <token>"
```

## Docker Compose

```bash
docker compose up --build
```

Compose 中包含：

- 后端服务：`project-collaboration-platform`
- MySQL 8
- Redis 7
- Nginx 反向代理

## 目录结构

```text
src/main/java/com/liujianan/collab
├── auth        # 登录认证与演示账号
├── audit       # 操作日志
├── common      # 统一响应、异常
├── config      # Web 拦截器配置
├── dashboard   # 仪表盘汇总
├── file        # 文件记录
├── project     # 项目管理
├── security    # JWT 与当前用户上下文
├── task        # 任务协同
└── user        # 用户与角色
```

## 面试说明

这个项目适合用来展示第一份 Java 后端实习所需的完整后端链路：从需求拆分、接口设计、认证鉴权、业务模块、日志审计，到接口文档和容器化演示。相比只写 CRUD，它更强调后台管理系统常见的工程边界和可验证交付。
