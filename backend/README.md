# 后端服务

## 模块与端口

- `edu-gateway`: 8080
- `edu-auth`: 9001
- `edu-system`: 9002
- `edu-course`: 9003
- `edu-order`: 9004
- `edu-learning`: 9005
- `edu-exam`: 9006
- `edu-marketing`: 9007
- `edu-message`: 9008
- `edu-statistics`: 9009

## 运行要求

- JDK 21
- Maven 3.9+
- Docker Compose 中的 MySQL、Redis、MinIO、Elasticsearch、Nacos

## 一键启动

从项目根目录执行：

```bash
scripts/start-backend.sh
```

常用选项：

```bash
scripts/start-backend.sh --skip-build
scripts/start-backend.sh --skip-docker
scripts/stop-backend.sh
```

启动日志位于 `.run/logs/`，进程号位于 `.run/pids/`。

## 主流程接口

- `POST /api/auth/login`
- `GET /api/course/courses`
- `POST /api/course/courses/{id}/publish`
- `POST /api/course/courses/{id}/approve`
- `POST /api/order/orders`
- `POST /api/order/orders/{id}/mock-pay`
- `POST /api/learning/enroll`
- `POST /api/learning/progress`
- `POST /api/exam/submit`
