import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './views/HomePage.vue'
import PostPage from './views/PostPage.vue'
import PortfolioPage from './views/PortfolioPage.vue'
import Login from './views/Login.vue'
import test from './views/test.vue'
import Detail from './views/Detail.vue'

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
			path: '/test',
			name: 'test',
			component: test
		},
		{
			path: '/detail/:id',
			name: 'detail',
			component: Detail
		}
	]
})
