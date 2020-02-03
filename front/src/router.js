import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './views/HomePage.vue'
import SearchPage from './views/SearchPage.vue'
import Detail from './views/Detail.vue'
import SignupPage from './views/SignupPage.vue'
import LoginPage from './views/LoginPage.vue'
import SocialSignup from './views/SocialSignupPage.vue'
import Profile from './views/Profile.vue'

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
			component: SignupPage
		  },
		  {
			path: "/login",
			name: "login",
			component: LoginPage
		  },
		  {
			path: "/SocialSignup",
			name: "SocialSignup",
			component: SocialSignup
		  },
		  {
		  	path: "/Profile",
		  	name: "Profile",
		  	component: Profile
		  }
	]
})
