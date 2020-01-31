<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import ImgBanner from '../components/ImgBanner'
import RepositoryList from '../components/RepositoryList'
import NewsList from '../components/NewsList'
import Info from '../services/getInfo';

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
    	RepositoryList,
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
