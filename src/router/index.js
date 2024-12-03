import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import Login from '@/components/Login.vue'
import MainPage from '@/components/CategorizedExercises.vue'
import LoginSuccess from '@/components/LoginSuccess.vue'
import HardBasedExercises from '@/components/HardBasedExercises.vue'
import History from '@/components/History.vue'
import LoginFailure from '@/components/LoginFailure.vue'
import PersonInfo from '@/components/PersonInfo.vue'
import AdminLogin from '@/components/AdminLogin.vue'
import Register from '@/components/Register.vue'
import AdminUser from '@/components/AdminUser.vue'
import AdminProblem from '@/components/AdminProblemView.vue'
import AdminAddProblem from '@/components/AdminAddProblem.vue'
import ResetPassword from '@/components/ResetPassword.vue'
import ProblemPage from '@/components/ProblemPage.vue'
import axios from 'axios'

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
    component: MainPage,
    meta: { requiresUser: true }
  },
  {
    path: '/success',
    name: 'success',
    component: LoginSuccess
  },
  {
    path: '/hard_based_exercises',
    name: 'hard_based_exercises',
    component: HardBasedExercises,
    meta: { requiresUser: true }
  },
  {
    path: '/history',
    name: 'history',
    component: History,
    meta: { requiresUser: true }
  },
  {
    path: '/failure',
    name: 'failure',
    component: LoginFailure
  },
  {
    path: '/person_info',
    name: 'person_info',
    component: PersonInfo,
    meta: { requiresUser: true }
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
    component: AdminUser,
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin_problem',
    name: 'admin_problem',
    component: AdminProblem,
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin_add_problem',
    name: 'admin_add_problem',
    component: AdminAddProblem,
    meta: { requiresAdmin: true }
  },
  {
    path: '/reset_password',
    name: 'reset_password',
    component: ResetPassword
  },
  {
    path:'/problem_page',
    name: 'problem_page',
    component: ProblemPage,
    meta: { requiresUser: true }
  }
]

const router = createRouter({
  routes,
  history: createWebHistory(process.env.BASE_URL),
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach((to, from, next) => {
    const username = localStorage.getItem('username');
    // 如果用户名为空，说明未登录
    if (to.matched.some(record => record.meta.requiresAdmin)) {
        if (username == null || username == '') {
            next({ name: 'admin_login' });
        } else {
            axios.get('/api/isAdminLogin', {
                params: {
                    username: username
                }
            })
            .then(res => {
                if (res.result == 'success') {
                    next();
                } else {
                    next({ name: 'admin_login' });
                }
            })
            .catch(err => {
                console.log(err);
                next({ name: 'admin_login' });
            });
        }
    } else if (to.matched.some(record => record.meta.requiresUser)) {
        if (username == null || username == '') {
            next({ name: 'login' });
        } else {
            axios.get('/api/isUserLogin', {
                params: {
                    username: username
                }
            })
            .then(res => {
                if (res.result == 'success') {
                    next();
                } else {
                    next({ name: 'login' });
                }
            })
            .catch(err => {
                console.log(err);
                next({ name: 'login' });
            });
        }
    } else {
        next(); // Always call next() to proceed with the navigation
    }
});


export default router
