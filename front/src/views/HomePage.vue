<template>
  <div>
      <chat />	
	  <v-row>
		<v-col 
		xs=12
		sm=7>
	    	<NewsList @brancdCheck-event="brandCheck"></NewsList>
		</v-col>
		<v-col
		class="d-none d-sm-flex"
		sm=5>
			<v-row class="keywordheader">
				<v-col cols="12" class="keyhead">
					<strong>최신 뉴스 키워드</strong>
				</v-col>
				<v-col cols="12" class="side">
					<!-- <vue-word-cloud
					:words="keyword"
					:color="([ , weight]) => selectColor(weight)"
					:font-size-ratio=3
					:animation-duration=500
					font-family="Roboto"
				/> -->
				 	<div id="chartdiv">
					</div>
				</v-col>
			</v-row>
		</v-col>
	  </v-row>
  </div>
</template>

<script>
import NewsList from '../components/NewsList'
import http from '../services/http-common.js'
import Info from '../services/getInfo';
import store from "../store";
import VueWordCloud from 'vuewordcloud';
import Chat from "../components/Chat"


export default {
	name: 'HomePage',
	data(){
		return {
			keyword : [],
			number : 0,
		}
	},
	components: {
		[VueWordCloud.name]: VueWordCloud,
		NewsList,
		Chat,
	},
	methods: {
		selectColor(weight){
			if(weight > 10) {
				console.log('brown')
				return 'brown'
			}
			else if (weight > 7) return 'green'
			else if(weight > 5) return 'RoyalBlue' 
			else return 'Indigo'
		},
		getKeyword(){
			http.get('/getChartKeyword')
				.then(response => {
					const keywords = response.data
					this.keyword = []
					this.start(keywords[this.number])
					for(let i=0;i<keywords.length;i++)
					{
						this.keyword.push(keywords[i])
					}
				})
				.catch(e => {
					console.log(e);
				});
		},
		start(keyword){
							/**
				 * ---------------------------------------
				 * This demo was created using amCharts 4.
				 * 
				 * For more information visit:
				 * https://www.amcharts.com/
				 * 
				 * Documentation is available at:
				 * https://www.amcharts.com/docs/v4/
				 * ---------------------------------------
				 */

				// Themes begin
				am4core.useTheme(am4themes_animated);
				// Themes end

				var chart = am4core.create("chartdiv", am4plugins_wordCloud.WordCloud);
				chart.fontFamily = "Courier New";
				var series = chart.series.push(new am4plugins_wordCloud.WordCloudSeries());
				series.randomness = 0.1;
				series.rotationThreshold = 0.5;
				
				series.data = keyword

				series.dataFields.word = "keyword";
				series.dataFields.value = "count";

				series.heatRules.push({
				"target": series.labels.template,
				"property": "fill",
				"min": am4core.color("#0000CC"),
				"max": am4core.color("#CC00CC"),
				"dataField": "value"
				});

				series.labels.template.url = "http://192.168.31.86:8080/search/{word}";
				series.labels.template.urlTarget = "_self";
				series.labels.template.tooltipText = "{word}: {value}";

				var hoverState = series.labels.template.states.create("hover");
				hoverState.properties.fill = am4core.color("#FF0000");

				// var subtitle = chart.titles.create();
				// subtitle.text = "(click to open)";

				// var title = chart.titles.create();
				// title.fontSize = 20;
				// title.fontWeight = "800";
		},
		brandCheck(number){
			console.log(number)
			this.start(this.keyword[number])
		}
	},
	beforeMount() {
		Info();
	},
	mounted(){
		this.getKeyword()
	},
}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

.keywordheader{
	margin: 29px 50px 10px 0px;
	height: 220px;
}
.keyhead{
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 20px;
	text-align: center;

}
.side{
	border: solid 1px grey;
    /* background-color: #F5F5F5; */
}
#chartdiv {
  width: 100%;
  height: 400px;
}
</style>
