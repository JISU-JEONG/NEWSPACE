import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './views/HomePage.vue'
import PostPage from './views/PostPage.vue'
import PortfolioPage from './views/PortfolioPage.vue'
import SearchPage from './views/SearchPage.vue'
import Detail from './views/Detail.vue'
import Home from './views/login/Home.vue'
import Signup from './views/login/Signup.vue'
import Login from './views/login/Login.vue'
import SocialSignup from './views/login/Social-Signup.vue'

Vue.use(Router)

export default new Router({
	mode: 'history',
	base: process.env.BASE_URL,
	routes: [
		{
			path: '/',
			name: 'home',
			component: HomePage
		},
		{
			path: '/post',
			name: 'post',
			component: PostPage
		},
		{
			path: '/portfolio',
			name: 'portfolio',
			component: PortfolioPage
		},
		{
			path: '/login',
			name: 'login',
			component: Login
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
			path: "/Signup",
			name: "signup",
			component: Signup
		  },
		  {
			path: "/Login",
			name: "Login",
			component: Login
		  },
		  {
			path: "/SocialSignup",
			name: "SocialSignup",
			component: SocialSignup
		  }
	]
})
