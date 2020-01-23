<template>
	<v-app>
		<v-app-bar
		color="blue"
		dense
		dark
		app
		class="header">
			<Navigation class="hidden-xs-only"></Navigation>
		</v-app-bar>
		<v-content>
			<SearchForm/>
			<router-view/>
		</v-content>
	</v-app>
</template>

<script>
import store from './store';
import '@mdi/font/css/materialdesignicons.css'
import Sidebar from './components/Sidebar'
import Navigation from './components/Navigation'
import SearchForm from './components/SearchForm'

export default {
	name: 'App',
	store,
	data() {
		return {
			//
		}
	},
	icons: {
		iconfont: 'mdi', // default - only for display purposes
	},
	components: {
		Sidebar,
		Navigation,
		SearchForm,
	}
}
</script>

<style>

.header{
	position:fixed;
	left:0;
	top:0;
	width:100vw;
	z-index:200;
}

</style>