<template>
  <main class="page">
    <header class="bar">
      <div>
        <strong>教师工作台</strong>
        <span>管理课程内容、题库和学生学习数据</span>
      </div>
      <div class="actions">
        <el-button @click="demoLogin">演示登录</el-button>
        <el-button type="primary" :icon="Plus" @click="publish">发布课程</el-button>
      </div>
    </header>
    <section class="summary">
      <div><span>学生数</span><strong>{{ stats.students }}</strong></div>
      <div><span>课程收入</span><strong>¥{{ stats.income }}</strong></div>
      <div><span>课程评分</span><strong>{{ stats.rating }}</strong></div>
      <div><span>完课率</span><strong>{{ stats.completionRate }}</strong></div>
    </section>
    <section class="content">
      <div class="panel">
        <div class="title">我的课程</div>
        <el-table :data="courses" height="360">
          <el-table-column prop="title" label="课程" min-width="220" />
          <el-table-column prop="lessons" label="课时" width="90" />
          <el-table-column prop="students" label="学生" width="100" />
          <el-table-column prop="rating" label="评分" width="90" />
          <el-table-column prop="status" label="状态" width="120" />
        </el-table>
      </div>
      <div class="panel">
        <div class="title">题库试卷</div>
        <div v-for="paper in papers" :key="paper.id" class="paper">
          <strong>{{ paper.title }}</strong>
          <span>{{ paper.questionCount }} 题 / {{ paper.totalScore }} 分</span>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup lang="ts">
import { api, saveSession, type Course } from '@eduation/shared'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { onMounted, reactive, ref } from 'vue'

const stats = reactive({ students: 0, income: 0, rating: 0, completionRate: '0%' })
const courses = ref<Course[]>([])
const papers = ref<any[]>([])

async function load() {
  Object.assign(stats, await api.teacherStats())
  courses.value = (await api.courses()).records.filter((item) => item.teacherName.includes('教师'))
  papers.value = await api.papers()
}

async function publish() {
  await api.publishCourse(1)
  ElMessage.success('课程已提交运营审核')
}

async function demoLogin() {
  saveSession(await api.login('teacher', '123456'))
  ElMessage.success('已登录教师账号')
}

onMounted(load)
</script>
