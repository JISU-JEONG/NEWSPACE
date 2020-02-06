<template>
  <v-container>
    <v-overlay :value="loading">
      <v-progress-circular indeterminate size="250" width="10" color="blue-grey lighten-5"></v-progress-circular>
    </v-overlay>
    <transition name="list">
      <v-container class="main_web" v-if="show">
        <h1>{{searchValue}} 검색결과</h1>
        <v-tabs
          v-model="tab"
          background-color="transparent"
          grow
        >
          <v-tab v-for="company in Object.keys(savedNews)" :key="company">{{ `${company.slice(0, -4)} (${savedNews[company].length})` }}</v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
          <v-tab-item v-for="company in Object.keys(showingNews)" :key="company">
            <span v-if="savedNews[company].length > 0" >
              <div class="box"
                v-for="i in showingNews[company].length" :key="i"
              >
                <News
                  :news = showingNews[company][i-1]
                  :company = company
                >
                </News>
                
              </div>
            </span>
            <span v-else style="font-size:40px; position:absolute; top:50px; left:30px;">{{`${company.slice(0, -4)}는 ${searchValue} 검색 결과가 없네요`}}</span>
          </v-tab-item>
        </v-tabs-items>
      </v-container>
    </transition>
    <v-container v-if="empty">
      <h1>{{searchValue}} 검색 결과가 없습니다.</h1>
    </v-container>
  </v-container>
</template>

<script>
import News from "../components/News"
import http from "../services/http-common"

export default {
  components: {
    News,
  },
  data() {
    return {
      loading: false,
      show: null,
      empty: null,
      limit: 15, 
      canLoadNews: [2, 2, 2, 2], // ALl, samsung, lg, sk - 인덱스 값 and 뉴스를 더 불러올수 있는지 
      showingNews: {
        AllList:[],
        Samsunglist: [],
        LGlist: [],
        SKlist: [],
      },
      savedNews: {
        AllList:[],
        Samsunglist: [],
        LGlist: [],
        SKlist: [],
      },
      tab: null,
      searchValue: this.$route.params.searchValue
    }
  },
  mounted() {
    this.searchNews()
    window.addEventListener('scroll', this.onScroll)
    
  },   
  watch: {
    '$route': 'searchNews'
  },
  methods: {
    searchNews() {
      this.show = null
      this.loading = true
      this.empty = false
      this.searchValue = this.$route.params.searchValue
      http.get(`/findNews/${this.$route.params.searchValue}`)
        .then((response) => {
          if (response.data.some(list => list.length > 0)) {
            this.loading = false
            this.savedNews.AllList = response.data[0]
            this.savedNews.Samsunglist = response.data[1]
            this.savedNews.LGlist = response.data[2]
            this.savedNews.SKlist = response.data[3]
            this.showingNews.AllList = this.savedNews.AllList.slice(0, 15)
            this.showingNews.Samsunglist = this.savedNews.Samsunglist.slice(0, 15)
            this.showingNews.LGlist = this.savedNews.LGlist.slice(0, 15)
            this.showingNews.SKlist = this.savedNews.SKlist.slice(0, 15)
            this.show = true
          } else {
            this.loading = false
            this.empty = true
          }
        }) 
        .catch((error) => {
          console.log(error)
        })
    },
    onScroll() {
      if (window.scrollY + document.documentElement.clientHeight > document.documentElement.scrollHeight - 300) {
        if (this.canLoadNews[this.tab]){
          console.log('스크롤이 내려왔읍니다.', this.tab)
          this.moveToShowingNews()
        }
      }
    },
    moveToShowingNews() {
      switch (this.tab) {
        case 0:
          if (this.canLoadNews[0]) {
            console.log('모든 뉴스를 더 불러옵니다.')
            const loadNews = this.savedNews.AllList.slice(this.limit * (this.canLoadNews[0] - 1), this.limit * this.canLoadNews[0])
            this.showingNews.AllList = this.showingNews.AllList.concat(loadNews)
            if (loadNews.length === this.limit) {
              this.canLoadNews[0] += 1
            } else {
              this.canLoadNews[0] = false
            }
            break
          }
        case 1:
          if (this.canLoadNews[1]) {
            console.log('삼성 뉴스를 더 불러옵니다.')
            const loadNews = this.savedNews.Samsunglist.slice(this.limit * (this.canLoadNews[1] - 1), this.limit * this.canLoadNews[1])
            this.showingNews.Samsunglist = this.showingNews.Samsunglist.concat(loadNews)
            if (loadNews.length === this.limit) {
              this.canLoadNews[1] += 1
            } else {
              this.canLoadNews[1] = false
            }
            break
          }
        case 2:
          if (this.canLoadNews[2]) {
            console.log('LG 뉴스를 더 불러옵니다.')
            const loadNews = this.savedNews.LGlist.slice(this.limit * (this.canLoadNews[2] - 1), this.limit * this.canLoadNews[2])
            this.showingNews.LGlist =this.showingNews.LGlist.concat(loadNews)
            if (loadNews.length === this.limit) {
              this.canLoadNews[2] += 1
            } else {
              this.canLoadNews[2] = false
            }
            break
          }
        case 3:
          if (this.canLoadNews[3]) {
            console.log('SK 뉴스를 더 불러옵니다.')
            const loadNews = this.savedNews.SKlist.slice(this.limit * (this.canLoadNews[3] - 1), this.limit * this.canLoadNews[3])
            this.showingNews.SKlist = this.showingNews.SKlist.concat(loadNews)
            if (loadNews.length === this.limit) {
              this.canLoadNews[3] += 1
            } else {
              this.canLoadNews[3] = false
            }
            break
          }
      }   
    }
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.onScroll)
  },
}
</script>

<style>
  .list-enter-active {
    transition: all 1s;
  }
  .list-enter {
    opacity: 0;
    transform: translateY(30px);
  }
</style>