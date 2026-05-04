<template>
  <el-container class="shell">
    <el-aside width="232px" class="aside">
      <div class="brand">Edaution Admin</div>
      <el-menu default-active="dashboard" background-color="#111827" text-color="#cbd5e1" active-text-color="#fff">
        <el-menu-item index="dashboard"><el-icon><DataBoard /></el-icon><span>运营工作台</span></el-menu-item>
        <el-menu-item index="courses"><el-icon><Reading /></el-icon><span>课程审核</span></el-menu-item>
        <el-menu-item index="orders"><el-icon><Tickets /></el-icon><span>订单管理</span></el-menu-item>
        <el-menu-item index="system"><el-icon><Setting /></el-icon><span>系统权限</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <div><strong>运营管理后台</strong><span>课程、订单、用户和数据统一运营</span></div>
        <div class="actions">
          <el-button @click="demoLogin">演示登录</el-button>
          <el-button type="primary" :icon="Refresh" @click="load">刷新数据</el-button>
        </div>
      </el-header>
      <el-main class="workspace">
        <section class="metrics">
          <div v-for="metric in overview.metrics" :key="metric.label" class="metric">
            <span>{{ metric.label }}</span>
            <strong>{{ metric.value }}</strong>
            <em>{{ metric.trend }}</em>
          </div>
        </section>
        <section class="grid">
          <div class="panel wide">
            <div class="panel-title">待审核课程</div>
            <el-table :data="courses" height="330">
              <el-table-column prop="title" label="课程" min-width="220" />
              <el-table-column prop="category" label="分类" width="120" />
              <el-table-column prop="teacherName" label="教师" width="120" />
              <el-table-column prop="status" label="状态" width="120">
                <template #default="{ row }"><el-tag :type="row.status === 'PUBLISHED' ? 'success' : 'warning'">{{ row.status }}</el-tag></template>
              </el-table-column>
              <el-table-column label="操作" width="130">
                <template #default="{ row }"><el-button size="small" type="primary" @click="approve(row.id)">通过</el-button></template>
              </el-table-column>
            </el-table>
          </div>
          <div class="panel">
            <div class="panel-title">订单动态</div>
            <el-timeline>
              <el-timeline-item v-for="order in orders" :key="order.id" :timestamp="order.status">{{ order.courseTitle }} ¥{{ order.amount }}</el-timeline-item>
            </el-timeline>
          </div>
        </section>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { api, saveSession, type Course } from '@eduation/shared'
import { ElMessage } from 'element-plus'
import { Refresh } from '@element-plus/icons-vue'
import { onMounted, reactive, ref } from 'vue'

const overview = reactive({ metrics: [] as any[] })
const courses = ref<Course[]>([])
const orders = ref<any[]>([])

async function load() {
  overview.metrics = (await api.overview()).metrics
  courses.value = (await api.courses()).records
  orders.value = await api.orders()
}

async function approve(id: number) {
  await api.approveCourse(id)
  ElMessage.success('课程已通过审核')
}

async function demoLogin() {
  saveSession(await api.login('admin', '123456'))
  ElMessage.success('已登录运营管理员账号')
}

onMounted(load)
</script>
