import axios from 'axios'

export interface ApiResponse<T> {
  code: number
  message: string
  data: T
  traceId: string
}

export interface Course {
  id: number
  title: string
  category: string
  teacherName: string
  price: number
  coverUrl: string
  status: string
  lessons: number
  students: number
  rating: number
}

export interface Metric {
  label: string
  value: string
  trend: string
}

export const http = axios.create({
  baseURL: (import.meta as any).env?.VITE_API_BASE || 'http://localhost:8080',
  timeout: 8000
})

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('EDU_TOKEN')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export async function unwrap<T>(request: Promise<{ data: ApiResponse<T> }>): Promise<T> {
  const response = await request
  if (response.data.code !== 0) {
    throw new Error(response.data.message)
  }
  return response.data.data
}

export const api = {
  login: (username: string, password: string) => unwrap<any>(http.post('/api/auth/login', { username, password })),
  overview: () => unwrap<any>(http.get('/api/statistics/overview')),
  teacherStats: () => unwrap<any>(http.get('/api/statistics/teacher')),
  courses: () => unwrap<{ records: Course[] }>(http.get('/api/course/courses')),
  courseDetail: (id: number) => unwrap<any>(http.get(`/api/course/courses/${id}`)),
  approveCourse: (id: number) => unwrap<any>(http.post(`/api/course/courses/${id}/approve`)),
  publishCourse: (id: number) => unwrap<any>(http.post(`/api/course/courses/${id}/publish`)),
  orders: () => unwrap<any[]>(http.get('/api/order/orders')),
  createOrder: (courseId: number) => unwrap<any>(http.post('/api/order/orders', { courseId })),
  mockPay: (orderId: number) => unwrap<any>(http.post(`/api/order/orders/${orderId}/mock-pay`)),
  myCourses: () => unwrap<any[]>(http.get('/api/learning/my-courses')),
  saveProgress: (payload: Record<string, unknown>) => unwrap<any>(http.post('/api/learning/progress', payload)),
  papers: () => unwrap<any[]>(http.get('/api/exam/papers')),
  questions: () => unwrap<any[]>(http.get('/api/exam/questions')),
  submitExam: (paperId: number) => unwrap<any>(http.post('/api/exam/submit', { paperId })),
  banners: () => unwrap<any[]>(http.get('/api/marketing/banners')),
  campaigns: () => unwrap<any[]>(http.get('/api/marketing/campaigns')),
  notices: () => unwrap<any[]>(http.get('/api/message/notices')),
  menus: (role: string) => unwrap<any[]>(http.get('/api/system/menus', { params: { role } }))
}

export function saveSession(payload: any) {
  localStorage.setItem('EDU_TOKEN', payload.accessToken)
  localStorage.setItem('EDU_USER', JSON.stringify(payload.user))
}

export function currentUser() {
  const raw = localStorage.getItem('EDU_USER')
  return raw ? JSON.parse(raw) : null
}
