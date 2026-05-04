<template>
  <main class="learn">
    <nav class="nav">
      <strong>Edaution 学习中心</strong>
      <div class="actions">
        <el-button @click="demoLogin">演示登录</el-button>
        <el-button type="primary" :icon="ShoppingCart" @click="buy">购买推荐课程</el-button>
      </div>
    </nav>
    <section class="layout">
      <div class="panel">
        <div class="title">我的课程</div>
        <div v-for="course in learning" :key="course.courseId" class="learn-row">
          <div>
            <strong>{{ course.courseTitle }}</strong>
            <span>最近学习：{{ course.lastLearnAt }}</span>
          </div>
          <el-progress :percentage="course.percent" />
          <el-button size="small" @click="save(course.courseId)">继续学习</el-button>
        </div>
      </div>
      <div class="panel">
        <div class="title">考试</div>
        <div v-for="paper in papers" :key="paper.id" class="exam">
          <strong>{{ paper.title }}</strong>
          <span>{{ paper.questionCount }} 题</span>
          <el-button size="small" type="primary" @click="submit(paper.id)">提交演示答卷</el-button>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup lang="ts">
import { api, saveSession } from '@eduation/shared'
import { ElMessage } from 'element-plus'
import { ShoppingCart } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue'

const learning = ref<any[]>([])
const papers = ref<any[]>([])

async function load() {
  learning.value = await api.myCourses()
  papers.value = await api.papers()
}

async function buy() {
  const order = await api.createOrder(1)
  await api.mockPay(order.id)
  ElMessage.success('模拟支付成功，课程已加入学习中心')
}

async function demoLogin() {
  saveSession(await api.login('student', '123456'))
  ElMessage.success('已登录学生账号')
}

async function save(courseId: number) {
  await api.saveProgress({ courseId, lessonId: 2, percent: 72 })
  ElMessage.success('学习进度已保存')
}

async function submit(paperId: number) {
  const result = await api.submitExam(paperId)
  ElMessage.success(`提交成功，得分 ${result.score}`)
}

onMounted(load)
</script>
