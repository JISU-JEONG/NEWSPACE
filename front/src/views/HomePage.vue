<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import ImgBanner from '../components/ImgBanner'
import NewsList from '../components/NewsList'
import Info from '../services/getInfo';

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
    	NewsList,
	},
	methods: {
		getImgUrl(img) {
			return require('../assets/' + img)
		},
	},
	beforeMount() {
		Info();
	}
}
</script>

<style scoped>
</style>
