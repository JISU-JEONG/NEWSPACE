<template>
  <v-container>
    <v-container v-if="loading">
      <v-card>
        <h1>로딩중이다!!!!!!!!기다려!!!!!!!!!!!</h1>
      </v-card>
    </v-container>

  <v-container class="main_web" v-if="show">
    <h1>{{searchValue}} 검색결과</h1>
    <v-tabs
      v-model="tab"
      background-color="transparent"
      grow
    >
      <v-tab>
        all
      </v-tab>
      <v-tab>
        Samsung
      </v-tab>
      <v-tab>
        LG
      </v-tab>
      <v-tab>
        SK
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item>
        <div class="box"
          v-for="i in AllList.length > 20 ? 20 : AllList.length" :key="i"
        >
        <News
          :news = AllList[i-1]
        >
        </News>
        </div>
      </v-tab-item>
        <v-tab-item>
          <div class="box"
            v-for="i in Samsunglist.length > 20 ? 20 : Samsunglist.length" :key="i"
          >
            <News
              :news = Samsunglist[i-1]
            >
            </News>
          </div>
        </v-tab-item>
        <v-tab-item>
          <div class="box"
          v-for="i in LGlist.length > 20 ? 20 : LGlist.length" :key="i"
        >
          <News
            :news = LGlist[i-1]
          >
          </News>
        </div>
      </v-tab-item>
      <v-tab-item>
        <div class="box"
          v-for="i in SKlist.length > 20 ? 20 : SKlist.length" :key="i"
        >
          <News
            :news = SKlist[i-1]
          >
          </News>
        </div>
      </v-tab-item>
    </v-tabs-items>
  </v-container>

  </v-container>
</template>

<script>
import News from "../components/News"
import http from '../http-common'

export default {
  components: {
    News,
  },
  data() {
    return {
      loading: false,
      show: null,
      Samsunglist: [],
      LGlist: [],
      SKlist: [],
      AllList:[],
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
      this.searchValue = this.$route.params.searchValue
      http.get(`http://192.168.31.85:8080/findNews/${this.$route.params.searchValue}`)
        .then((response) => {
            this.loading = false
            this.AllList = response.data[0]
            this.Samsunglist = response.data[1]
            this.LGlist = response.data[2]
            this.SKlist = response.data[3]
            this.show = true
        }) 
        .catch((error) => {
          console.log(error)
        })
    }
  },
}
</script>

<style>

</style>