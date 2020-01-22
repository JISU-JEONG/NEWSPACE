<template>
  <div>
      <!-- Searchbar -->
    	<Searchbar/>
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
import Searchbar from '../components/Searchbar'

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
		PortfolioList,
		PostList,
    RepositoryList,
    Searchbar,
    NewsList
	},
	methods: {
		getImgUrl(img) {
			return require('../assets/' + img)
		}
	},
}
</script>
