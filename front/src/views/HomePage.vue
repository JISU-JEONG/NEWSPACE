<template>
  <div>
    <!-- Searchbar -->
    <!-- <SearchBar/> -->
    <!-- NewsList -->
    <v-layout my-5>
      <v-flex xs12>
        <h2 class="headline my-5 text-xs-center">NewsList</h2>
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
// import SearchBar from '../components/SearchBar'

export default {
	name: 'HomePage',
	components: {
		ImgBanner,
		PortfolioList,
		PostList,
    RepositoryList,
    NewsList,
    // SearchBar,
	},
	methods: {
		getImgUrl(img) {
			return require('../assets/' + img)
		}
	},
}
</script>
