# EdautionSystem 在线教育系统

基于 Spring Cloud + Vue 3 + Element Plus 的在线教育系统脚手架，包含运营管理后台、教师端、学生端、官方网站四个前端应用，以及标准微服务后端。

## 技术栈

- Java 21, Spring Boot 3.5.0, Spring Cloud 2025.0.0
- Spring Cloud Gateway, Spring Security, JWT, OpenFeign
- MySQL, Redis, MinIO, Elasticsearch, Nacos
- Vue 3, Vite, TypeScript, Element Plus, Pinia, Axios
- Docker Compose 本地基础设施

## 目录

- `backend/` Maven 多模块后端
- `frontend/` pnpm workspace 前端 monorepo
- `docker-compose.yml` 本地中间件
- `backend/sql/init.sql` 初始化表结构和演示数据

## 快速启动

```bash
scripts/start-backend.sh
```

前端：

```bash
cd frontend
pnpm install
pnpm dev:admin
pnpm dev:teacher
pnpm dev:student
pnpm dev:official
```

## 演示账号

- 运营管理员：admin / 123456
- 教师：teacher / 123456
- 学生：student / 123456

> 当前版本提供完整产品骨架和核心业务闭环接口，支付为模拟支付，文件与搜索服务保留通用扩展点。
