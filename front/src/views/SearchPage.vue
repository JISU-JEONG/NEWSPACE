<template>
  <v-container>
    <h1>SearchPage</h1>
    <span>{{searchValue}}</span>

    <v-container>
      <v-row>
        <v-col col="4">
          <v-card v-for="i in samsungNews.length > 10 ? 5:samsungNews.length" :key="i">
            <v-container>
              <h3>{{samsungNews[i-1].title}}</h3>
            </v-container>
          </v-card>
        </v-col>
        <v-col col="4">
          <v-card v-for="i in lgNews.length" :key="i">
            <v-container>
              <h3>{{lgNews[i-1].title}}</h3>
            </v-container>
          </v-card>
        </v-col>
        <v-col col="4">
          <v-card v-for="i in skNews.length > 10 ? 5:skNews.length" :key="i">
            <v-container>
              <h3>{{skNews[i-1].title}}</h3>
            </v-container>
          </v-card>
        </v-col>
      </v-row> 
    </v-container>

  </v-container>
</template>

<script>
import http from '../http-common'

export default {
  data() {
    return {
      samsungNews: [],
      lgNews: [],
      skNews: [],
      searchValue: this.$route.params.searchValue
    }
  },
  computed: {
  },
  methods: {
    searchNews() {
      http.get(`http://192.168.31.85:8080/findNews/${this.$route.params.searchValue}`)
        .then((response) => {
            this.samsungNews = response.data[0]
            this.lgNews = response.data[1]
            this.skNews = response.data[2]
          console.log(response.data)
        }) 
        .catch((error) => {
          console.log(error)
        })
    }
  },
  mounted() {
    this.searchNews()
  }
}
</script>

<style>

</style>