<template>
  <v-container>
    <v-overlay :value="loading">
      <v-progress-circular indeterminate size="300"></v-progress-circular>
    </v-overlay>
    <transition name="list">
      <v-container class="main_web" v-if="show">
        <h1>{{searchValue}} 검색결과</h1>
        <v-tabs
          v-model="tab"
          background-color="transparent"
          grow
        >
          <v-tab v-for="company in Object.keys(newsObject)" :key="company">{{ `${company.slice(0, -4)} (${newsObject[company].length})` }}</v-tab>
        </v-tabs>
        <v-tabs-items v-model="tab">
          <v-tab-item v-for="company in Object.keys(newsObject)" :key="company">
            <span v-if="newsObject[company].length > 0" >
              <div class="box"
                v-for="i in newsObject[company].length > 20 ? 20 : newsObject[company].length" :key="i"
              >
                <News
                  :news = newsObject[company][i-1]
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
    <v-container class="main_web" v-if="empty">
      <h1>{{searchValue}} 검색 결과가 없네요. 좀 제대로 검색해보세요</h1>
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
      newsObject: {
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
  },   
  watch: {
    '$route': 'searchNews'
  },
  methods: {
    searchNews() {
      this.show = null
      this.loading = true
      this.empty = null
      this.searchValue = this.$route.params.searchValue
      http.get(`/findNews/${this.$route.params.searchValue}`)
        .then((response) => {
          if (response.data.some(list => list.length > 0)) {
            this.loading = false
            this.newsObject.AllList = response.data[0]
            this.newsObject.Samsunglist = response.data[1]
            this.newsObject.LGlist = response.data[2]
            this.newsObject.SKlist = response.data[3]
            this.show = true
          } else {
            this.loading = false
            this.empty = true
          }
        }) 
        .catch((error) => {
          console.log(error)
        })
    }
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