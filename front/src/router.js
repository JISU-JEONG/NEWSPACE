import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './views/HomePage.vue'
import SearchPage from './views/SearchPage.vue'
import Detail from './views/Detail.vue'
import SignupPage from './views/SignupPage.vue'
import LoginPage from './views/LoginPage.vue'
import SocialSignup from './views/SocialSignupPage.vue'

Vue.use(Router)

const routes = [
	{
		path: '/',
		name: 'home',
		component: HomePage
	},
	{
		path: '/search/:searchValue',
		name: 'search',
		component: SearchPage
	},
	{
		path: '/detail/:id',
		name: 'detail',
		component: Detail
	},
  {
    path: "/signup",
    name: "signup",
    component: SignupPage,
    meta: {
      needBlockAuthUser: true
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginPage,
    meta: {
      needBlockAuthUser: true
    }
  },
  {
    path: "/SocialSignup",
    name: "SocialSignup",
    component: SocialSignup,
    meta: {
      needBlockAuthUser: true
    }
  }
]

const router = new Router({
  mode: 'history',
  routes,
  base: process.env.BASE_URL,
})

function blockAuthUser(to, from, next) {
  if (localStorage.getItem("loginStatus") != null &&
      localStorage.getItem("login-token") != null) {
        next('/') // 로그인된 유저는 접근 불가, 메인으로 이동
  } else {
    next() // 로그인 안된 유저는 해당 페이지로 이동.
  }
}

router.beforeEach((to, from, next) => {
  if (to.matched.some(m => m.meta.needBlockAuthUser)) {
    blockAuthUser(to, from, next)  // 로그인 유저 막는 페이지인지 체크
  } else {
    next() // 로그인 유저 막는 페이지가 아니라면 바로 페이지 이동.
  }
})



export default router
