import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import Login from '@/components/Login.vue'
import MainPage from '@/components/CategorizedExercises.vue'
import LoginSuccess from '@/components/LoginSuccess.vue'
import HardBasedExercises from '@/components/HardBasedExercises.vue'
import History from '@/components/History.vue'
import LoginFailure from '@/components/LoginFailure.vue'
import JudgeResult from '@/components/JudgeResult.vue'
import PersonInfo from '@/components/PersonInfo.vue'
import AdminLogin from '@/components/AdminLogin.vue'
import Register from '@/components/Register.vue'
import AdminUser from '@/components/AdminUser.vue'
import AdminProblem from '@/components/AdminProblemView.vue'
import AdminAddProblem from '@/components/AdminAddProblem.vue'
import ResetPassword from '@/components/ResetPassword.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/categorized_exercises',
    name: 'categorized_exercises',
    component: MainPage
  }, 
  {
    path: '/success',
    name: 'success',
    component: LoginSuccess
  },
  {
    path: '/hard_based_exercises',
    name: 'hard_based_exercises',
    component: HardBasedExercises
  },
  {
    path: '/history',
    name: 'history',
    component: History
  },
  {
    path: '/failure',
    name: 'failure',
    component: LoginFailure
  },
  {
    path: '/judge_result',
    name: 'judge_result',
    component: JudgeResult
  },
  {
    path: '/person_info',
    name: 'person_info',
    component: PersonInfo
  },
  {
    path: '/admin_login',
    name: 'admin_login',
    component: AdminLogin
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/admin_user',
    name: 'admin_user',
    component: AdminUser
  },
  {
    path: '/admin_problem',
    name: 'admin_problem',
    component: AdminProblem
  },
  {
    path: '/admin_add_problem',
    name: 'admin_add_problem',
    component: AdminAddProblem
  },
  {
    path: '/reset_password',
    name: 'reset_password',
    component: ResetPassword
  }
]

const router = createRouter({
  routes,
  history: createWebHistory(process.env.BASE_URL),
  scrollBehavior() {
    return { top: 0 }
  }
})

export default router
