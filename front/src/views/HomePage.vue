<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import ImgBanner from '../components/ImgBanner'
import PortfolioList from '../components/PortfolioList'
import PostList from '../components/PostList'
import RepositoryList from '../components/RepositoryList'
import NewsList from '../components/NewsList'
const storage = sessionStorage;

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
		PortfolioList,
		PostList,
    	RepositoryList,
    	NewsList,
	},
	methods: {
		getImgUrl(img) {
			return require('../assets/' + img)
		}
	},
	mounted(){
		this.$store.dispatch('login',localStorage.getItem("login-token"))
	}
	
}
</script>

<style scoped>

</style>
