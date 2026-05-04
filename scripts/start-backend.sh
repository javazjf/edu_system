#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BACKEND_DIR="$ROOT_DIR/backend"
RUN_DIR="$ROOT_DIR/.run"
LOG_DIR="$RUN_DIR/logs"
PID_DIR="$RUN_DIR/pids"

SERVICES=(
  "edu-auth:9001"
  "edu-system:9002"
  "edu-course:9003"
  "edu-order:9004"
  "edu-learning:9005"
  "edu-exam:9006"
  "edu-marketing:9007"
  "edu-message:9008"
  "edu-statistics:9009"
  "edu-gateway:8080"
)

usage() {
  cat <<'EOF'
Usage: scripts/start-backend.sh [options]

Options:
  --skip-build       Do not run mvn clean package before starting services.
  --skip-docker      Do not run docker compose up -d.
  --no-stop          Do not stop services recorded in .run/pids first.
  -h, --help         Show help.
EOF
}

SKIP_BUILD=false
SKIP_DOCKER=false
STOP_FIRST=true

while [[ $# -gt 0 ]]; do
  case "$1" in
    --skip-build) SKIP_BUILD=true ;;
    --skip-docker) SKIP_DOCKER=true ;;
    --no-stop) STOP_FIRST=false ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown option: $1"; usage; exit 1 ;;
  esac
  shift
done

need_cmd() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo "Missing command: $1"
    echo "Please install it and retry."
    exit 1
  fi
}

java_major() {
  java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F. '{ if ($1 == "1") print $2; else print $1 }'
}

stop_existing() {
  if [[ ! -d "$PID_DIR" ]]; then
    return
  fi

  for pid_file in "$PID_DIR"/*.pid; do
    [[ -e "$pid_file" ]] || continue
    local pid
    pid="$(cat "$pid_file")"
    if kill -0 "$pid" >/dev/null 2>&1; then
      echo "Stopping $(basename "$pid_file" .pid) ($pid)"
      kill "$pid" >/dev/null 2>&1 || true
    fi
    rm -f "$pid_file"
  done
}

wait_for_http() {
  local url="$1"
  local label="$2"
  local max_attempts="${3:-30}"

  for _ in $(seq 1 "$max_attempts"); do
    if curl -fsS "$url" >/dev/null 2>&1; then
      echo "$label is reachable"
      return 0
    fi
    sleep 2
  done

  echo "Warning: $label did not become reachable at $url"
  return 1
}

start_service() {
  local service="$1"
  local port="$2"
  local jar="$BACKEND_DIR/$service/target/$service-0.1.0-SNAPSHOT.jar"
  local log_file="$LOG_DIR/$service.log"
  local pid_file="$PID_DIR/$service.pid"

  if [[ ! -f "$jar" ]]; then
    echo "Missing jar: $jar"
    echo "Run without --skip-build first."
    exit 1
  fi

  echo "Starting $service on port $port"
  nohup java -jar "$jar" >"$log_file" 2>&1 &
  echo $! >"$pid_file"
}

mkdir -p "$LOG_DIR" "$PID_DIR"

need_cmd java
need_cmd curl

JAVA_MAJOR="$(java_major)"
if [[ -z "$JAVA_MAJOR" || "$JAVA_MAJOR" -lt 21 ]]; then
  echo "Java 21+ is required. Current java version:"
  java -version
  exit 1
fi

if [[ "$SKIP_DOCKER" == false ]]; then
  need_cmd docker
  echo "Starting Docker Compose infrastructure"
  docker compose -f "$ROOT_DIR/docker-compose.yml" up -d
  wait_for_http "http://localhost:8848/nacos" "Nacos" 45 || true
fi

if [[ "$STOP_FIRST" == true ]]; then
  stop_existing
fi

if [[ "$SKIP_BUILD" == false ]]; then
  need_cmd mvn
  echo "Building backend modules"
  (cd "$BACKEND_DIR" && mvn clean package -DskipTests)
fi

for item in "${SERVICES[@]}"; do
  service="${item%%:*}"
  port="${item##*:}"
  start_service "$service" "$port"
  sleep 1
done

echo
echo "Backend services started."
echo "Gateway: http://localhost:8080"
echo "Logs: $LOG_DIR"
echo "Pids: $PID_DIR"
echo
echo "Smoke test:"
echo "curl -X POST http://localhost:8080/api/auth/login -H 'Content-Type: application/json' -d '{\"username\":\"admin\",\"password\":\"123456\"}'"
