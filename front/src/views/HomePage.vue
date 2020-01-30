<template>
  <div>
      <!-- NewsList -->
      <v-layout my-5>
        <v-flex xs12>
          <NewsList></NewsList>
        </v-flex>
      </v-layout>
  </div>
</template>

<script>
import ImgBanner from '../components/ImgBanner'
import PortfolioList from '../components/PortfolioList'
import PostList from '../components/PostList'
import RepositoryList from '../components/RepositoryList'
import NewsList from '../components/NewsList'

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
