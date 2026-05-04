<template>
  <main>
    <section class="hero">
      <nav>
        <strong>Edaution</strong>
        <div>
          <a href="#courses">课程</a>
          <a href="#campaigns">活动</a>
          <el-button type="primary" @click="login">登录体验</el-button>
        </div>
      </nav>
      <div class="hero-copy">
        <h1>在线教育系统</h1>
        <p>从课程运营、教师授课到学生学习的一体化平台。</p>
        <el-button size="large" type="primary" :icon="Reading">查看课程</el-button>
      </div>
    </section>
    <section id="courses" class="section">
      <div class="section-head">
        <h2>精选课程</h2>
        <span>覆盖研发、产品、职业认证与企业内训。</span>
      </div>
      <div class="courses">
        <article v-for="course in courses" :key="course.id" class="course">
          <img :src="imageFor(course.id)" :alt="course.title">
          <div>
            <el-tag>{{ course.category }}</el-tag>
            <h3>{{ course.title }}</h3>
            <p>{{ course.teacherName }} · {{ course.lessons }} 课时 · {{ course.students }} 人学习</p>
            <strong>¥{{ course.price }}</strong>
          </div>
        </article>
      </div>
    </section>
    <section id="campaigns" class="band">
      <div v-for="campaign in campaigns" :key="campaign.title">
        <span>{{ campaign.tag }}</span>
        <strong>{{ campaign.title }}</strong>
        <p>{{ campaign.discount }}</p>
      </div>
    </section>
  </main>
</template>

<script setup lang="ts">
import { api, saveSession, type Course } from '@eduation/shared'
import { ElMessage } from 'element-plus'
import { Reading } from '@element-plus/icons-vue'
import { onMounted, ref } from 'vue'

const courses = ref<Course[]>([])
const campaigns = ref<any[]>([])

function imageFor(id: number) {
  const images: Record<number, string> = {
    1: 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80',
    2: 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=900&q=80',
    3: 'https://images.unsplash.com/photo-1552664730-d307ca884978?auto=format&fit=crop&w=900&q=80'
  }
  return images[id] || images[1]
}

async function login() {
  const session = await api.login('student', '123456')
  saveSession(session)
  ElMessage.success('已使用学生演示账号登录')
}

onMounted(async () => {
  courses.value = (await api.courses()).records
  campaigns.value = await api.campaigns()
})
</script>
