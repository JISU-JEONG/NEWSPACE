<template>
	<v-app>
		<v-app-bar
		color="blue"
		dense
		dark
		app
		class="header">
			<Sidebar class="hidden-sm-and-up">
			</Sidebar>
			<Navigation class="hidden-xs-only">
			</Navigation>
		</v-app-bar>
		<v-content>
			<router-view/>
		</v-content>
	</v-app>
</template>

<script>
import store from './store';
import '@mdi/font/css/materialdesignicons.css'
import Sidebar from './components/Sidebar'
import Navigation from './components/Navigation'

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