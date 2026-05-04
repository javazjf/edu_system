CREATE TABLE IF NOT EXISTS edu_user (
  id BIGINT PRIMARY KEY,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(128) NOT NULL,
  real_name VARCHAR(64) NOT NULL,
  role_code VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS edu_course (
  id BIGINT PRIMARY KEY,
  title VARCHAR(128) NOT NULL,
  category VARCHAR(64) NOT NULL,
  teacher_name VARCHAR(64) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  status VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS edu_order (
  id BIGINT PRIMARY KEY,
  order_no VARCHAR(64) NOT NULL UNIQUE,
  course_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  status VARCHAR(32) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS edu_learning_progress (
  id BIGINT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  percent INT NOT NULL,
  current_lesson_id BIGINT
);

INSERT INTO edu_user (id, username, password, real_name, role_code) VALUES
(1, 'admin', '123456', '运营管理员', 'ADMIN'),
(2, 'teacher', '123456', '演示教师', 'TEACHER'),
(3, 'student', '123456', '演示学生', 'STUDENT')
ON DUPLICATE KEY UPDATE real_name = VALUES(real_name);

INSERT INTO edu_course (id, title, category, teacher_name, price, status) VALUES
(1, 'Spring Cloud 在线教育实战', '后端开发', '演示教师', 299.00, 'PUBLISHED'),
(2, 'Vue3 + Element Plus 管理系统', '前端开发', '演示教师', 199.00, 'PUBLISHED'),
(3, 'AI 产品经理训练营', '产品设计', '运营导师', 399.00, 'PENDING')
ON DUPLICATE KEY UPDATE title = VALUES(title);
