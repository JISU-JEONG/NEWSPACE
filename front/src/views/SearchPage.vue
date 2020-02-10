<template>
  <v-container>
    <v-overlay :value="loading">
      <v-progress-circular indeterminate size="250" width="10" color="blue-grey lighten-5"></v-progress-circular>
    </v-overlay>
    <transition name="list">
      <v-container class="main_web" v-show="show">
        <div id="chartdiv"></div>
        <!-- <h1>{{searchValue}} 검색결과</h1> -->
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
      this.startGraph()
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
    },
    startGraph(){
        // Themes begin
        am4core.useTheme(am4themes_animated);
        // Themes end
        var label;

        var chart = am4core.create("chartdiv", am4charts.XYChart);
        console.log(chart)
        chart.paddingRight = 1;
        chart.dateFormatter.dateFormat = "yyyy-MM";
        var data = [];
        http.get(`/getSearchChartKeyword/${this.$route.params.searchValue}`)
        .then((response) => {
                const Gdata = response.data
                console.log(Gdata)
                for(let i=0;i<12;i++)
                {
                  const day = Gdata[i].date.split('-')
                  data.push({date:new Date(day[0],day[1],0), value:Gdata[i].value})
                }
              chart.data = data;
              // console.log(chart.data)
              var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
              dateAxis.renderer.grid.template.location = 0;
              var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
              valueAxis.tooltip.disabled = true;
              // valueAxis.renderer.minWidth = 35;
              var series = chart.series.push(new am4charts.LineSeries());
              series.dataFields.dateX = "date";
              series.dataFields.valueY = "value";
              series.tooltipText = "{valueY}";
              series.tooltip.pointerOrientation = "vertical";
              series.tooltip.background.fillOpacity = 0.5;
              series.tensionX = 0.9;
              // series.columns.template.width = am4core.percent(100);


              // chart.scrollbarY = new am4core.Scrollbar();

              series.events.on("ready", function () {

                  label = series.createChild(am4core.Label);
                  label.strokeOpacity = 0;
                  label.fontSize = 20;
                  label.fill = series.stroke;
                  label.fillOpacity = 1;
                  label.padding(0, 0, 5, 0);

                  label.path = series.segments.getIndex(0).strokeSprite.path;

                  series.segments.getIndex(0).strokeSprite.events.on("propertychanged", function (event) {
                      if (event.property == "path") {
                          label.path = series.segments.getIndex(0).strokeSprite.path;
                      }
                  })
                  animateForward();

              }, 1000)

              var vm = this
              function animateForward() {
                  label.text = "'"+vm.searchValue+"'" + ' 검색결과'
                  var animation = label.animate({ property: "locationOnPath", from: 0, to: 1 }, 12000);
                  animation.events.on("animationended", animateBackwards);
              }

              function animateBackwards() {
                  label.text = "'"+vm.searchValue+"'" + ' 검색결과'
                  var animation = label.animate({ property: "locationOnPath", from: 1, to: 0 }, 8000);
                  animation.events.on("animationended", animateForward);
              }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.onScroll)
  },
}
</script>

<style scoped>
  .list-enter-active {
    transition: all 1s;
  }
  .list-enter {
    opacity: 0;
    transform: translateY(30px);
  }
  #chartdiv {
  width: 100%;
  height: 400px;
  }
</style>

