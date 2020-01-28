<template>
	<v-app>
    <Navigation />
		<v-content>
			<router-view/>
		</v-content>
	</v-app>
</template>

<script>
import '@mdi/font/css/materialdesignicons.css'
import Navigation from './components/Navigation'

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
.background{
	background-color: white;
}
</style>