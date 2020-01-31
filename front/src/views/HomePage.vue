<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import NewsList from '../components/NewsList'
import Info from '../services/getInfo';

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
    	NewsList,
	},
	methods: {

	},
	beforeMount() {
		Info();
	}
}
</script>

<style scoped>
</style>
