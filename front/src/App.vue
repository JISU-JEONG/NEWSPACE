<template>
	<v-app>
		<Navigation />
		<v-content class="background">
			<router-view/>
		</v-content>
		<AppFooter/>
	</v-app>
</template>

<script>
import '@mdi/font/css/materialdesignicons.css'
import Navigation from './components/Navigation'
import AppFooter from './components/AppFooter'
export default {
	name: 'App',
	data() {
		return {
			//
		}
	},
	icons: {
		iconfont: 'mdi', // default - only for display purposes
	},
	components: {
		Navigation,
		AppFooter,
	}
}
</script>

<style>

</style>