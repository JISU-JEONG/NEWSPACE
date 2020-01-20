<template>
  <div>
    <ImgBanner imgSrc="https://wallpapercave.com/wp/wp3482226.jpg">
      <div style="line-height:1.2em; text-align:center; font-size:5vw" slot="text">One Day<br> One Commit</div>
    </ImgBanner>
    <v-container>
      <!-- About Me -->
      <v-layout my-5>
        <v-flex xs8>
          <h2 class="text-center text-sm-left headline mb-3">About Me</h2>
          <p class="text-center text-sm-left mr-4">안녕하세요!<br/>저는 정영길입니다!<br/>반갑습니다!</p>
        </v-flex>

        <v-flex hidden-xs-only>
          <v-img :src="getImgUrl('profile.png')" aspect-ratio="1.5"/>
        </v-flex>
      </v-layout>
      <!-- 600px //-->
      <!-- Portfolio -->
      <v-layout my-5>
        <v-flex xs12>
          <h2 class="headline my-5 text-xs-center">Portfolio</h2>
          <PortfolioList></PortfolioList>
        </v-flex>
      </v-layout>

      <!-- Post -->
      <v-layout my-5>
        <v-flex xs12>
          <h2 class="headline my-5 text-xs-center">Post</h2>
          <PostList></PostList>
        </v-flex>
      </v-layout>


      <!-- NewsList -->
      <v-layout my-5>
        <v-flex xs12>
          <h2 class="headline my-5 text-xs-center">NewsList</h2>
          <NewsList></NewsList>
        </v-flex>
      </v-layout>


      <!-- Github -->
      <v-layout my-5>
        <v-flex xs12>
          <h2 class="headline my-5 text-xs-center">Project</h2>
          <RepositoryList></RepositoryList>
        </v-flex>
      </v-layout>
    </v-container>
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
    NewsList
	},
	methods: {
		getImgUrl(img) {
			return require('../assets/' + img)
		}
	},
}
</script>
