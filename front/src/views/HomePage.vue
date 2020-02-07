<template>
  <div>
	  <div class="keyheader">
		<div v-show="number===0" id="chartdiv1">
		</div>
		<div v-show="number===1" id="chartdiv2">
		</div>
		<div v-show="number===2" id="chartdiv3">
		</div>
		<div v-show="number===3" id="chartdiv4">
		</div>
		<!-- <div class="under" :Alllcount=allcout :todaycount=todaycount>
		</div> -->
	</div>
	<v-container>

	  <v-row>
		<v-col 
		sm=12
		md=9>
	    	<NewsList @brancdCheck-event="brandCheck"></NewsList>
		</v-col>
		<v-col
		class="d-sm-none d-md-flex"
		md=3>
			<div class="side">
				<div class="sidehead">
					NEWSPACE?
				</div>
				<div class="sidebody">
					<strong>당신을 위한 새로운 공간</strong>
				</div>
				<div class="sidebody">
					<strong>삼성, LG, SK의 기업 최신 뉴스</strong> 업데이트를 속도를 따라 신속하게
				</div>
				<div class="sidebody">
					검색 기능, <strong>텍스트 마이닝</strong>을 통한 뉴스의 중요 키워드,
					<strong>관심 기업</strong>의 동향을 빠르게 파악할 수 있는 새로운 뉴스 사이트
				</div>
			</div>
		</v-col>
	  </v-row>
	</v-container>
  </div>
</template>

<script>
import NewsList from '../components/NewsList'
import http from '../services/http-common.js'
import Info from '../services/getInfo';
import store from "../store";

export default {
	name: 'HomePage',
	data(){
		return {
			number : 0,
			text : "전체"
		}
	},
	components: {
    	NewsList,
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
					this.start(keywords)
				})
				.catch(e => {
					console.log(e);
				});
		},
		start(keywords){
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

				var chart1 = am4core.create("chartdiv1", am4plugins_wordCloud.WordCloud);
				var chart2 = am4core.create("chartdiv2", am4plugins_wordCloud.WordCloud);
				var chart3 = am4core.create("chartdiv3", am4plugins_wordCloud.WordCloud);
				var chart4 = am4core.create("chartdiv4", am4plugins_wordCloud.WordCloud);

				chart1.fontFamily = "Courier New";
				chart2.fontFamily = "Courier New";
				chart3.fontFamily = "Courier New";
				chart4.fontFamily = "Courier New";
				var series1 = chart1.series.push(new am4plugins_wordCloud.WordCloudSeries());
				var series2 = chart2.series.push(new am4plugins_wordCloud.WordCloudSeries());
				var series3 = chart3.series.push(new am4plugins_wordCloud.WordCloudSeries());
				var series4 = chart4.series.push(new am4plugins_wordCloud.WordCloudSeries());
				series1.randomness = 0.1;
				series1.rotationThreshold = 0.5;
				series1.minFontSize = am4core.percent(5)
				series1.maxFontSize = am4core.percent(25)
				series2.randomness = 0.1;
				series2.rotationThreshold = 0.5;
				series2.minFontSize = am4core.percent(5)
				series2.maxFontSize = am4core.percent(25)
				series3.randomness = 0.1;
				series3.rotationThreshold = 0.5;
				series3.minFontSize = am4core.percent(5)
				series3.maxFontSize = am4core.percent(25)
				series4.randomness = 0.1;
				series4.rotationThreshold = 0.5;
				series4.minFontSize = am4core.percent(5)
				series4.maxFontSize = am4core.percent(25)
				
				series1.data = keywords[0]
				series2.data = keywords[1]
				series3.data = keywords[2]
				series4.data = keywords[3]

				series1.dataFields.word = "keyword";
				series1.dataFields.value = "count";
				series2.dataFields.word = "keyword";
				series2.dataFields.value = "count";
				series3.dataFields.word = "keyword";
				series3.dataFields.value = "count";
				series4.dataFields.word = "keyword";
				series4.dataFields.value = "count";

				series1.heatRules.push({
				"target": series1.labels.template,
				"property": "fill",
				"min": am4core.color("#0000CC"),
				"max": am4core.color("#CC00CC"),
				"dataField": "value"
				});
				series2.heatRules.push({
				"target": series2.labels.template,
				"property": "fill",
				"min": am4core.color("#AADDDD"),
				"max": am4core.color("#1428A0"),
				"dataField": "value"
				});
				series3.heatRules.push({
				"target": series3.labels.template,
				"property": "fill",
				"min": am4core.color("#AA00CC"),
				"max": am4core.color("#EE0034"),
				"dataField": "value"
				});
				series4.heatRules.push({
				"target": series4.labels.template,
				"property": "fill",
				"min": am4core.color("#CCAACC"),
				"max": am4core.color("#00CC33"),
				"dataField": "value"
				});

				series1.labels.template.url = "http://192.168.31.86:8080/search/{word}";
				series1.labels.template.urlTarget = "_self";
				series1.labels.template.tooltipText = "{word}: {value}";
				series2.labels.template.url = "http://192.168.31.86:8080/search/{word}";
				series2.labels.template.urlTarget = "_self";
				series2.labels.template.tooltipText = "{word}: {value}";
				series3.labels.template.url = "http://192.168.31.86:8080/search/{word}";
				series3.labels.template.urlTarget = "_self";
				series3.labels.template.tooltipText = "{word}: {value}";
				series4.labels.template.url = "http://192.168.31.86:8080/search/{word}";
				series4.labels.template.urlTarget = "_self";
				series4.labels.template.tooltipText = "{word}: {value}";

				var hoverState1 = series1.labels.template.states.create("hover");
				hoverState1.properties.fill = am4core.color("#FF0000");
				var hoverState2 = series2.labels.template.states.create("hover");
				hoverState2.properties.fill = am4core.color("#FF0000");
				var hoverState3 = series3.labels.template.states.create("hover");
				hoverState3.properties.fill = am4core.color("#FF0000");
				var hoverState4 = series4.labels.template.states.create("hover");
				hoverState4.properties.fill = am4core.color("#FF0000");

				// var subtitle = chart.titles.create();
				// subtitle.text = "(click to open)";

				// var title = chart.titles.create();
				// title.fontSize = 20;
				// title.fontWeight = "800";
		},
		brandCheck(number){
			if(number===0) this.text="전체"
			if(number===1) this.text="SAMSUNG"
			if(number===2) this.text="LG"
			if(number===3) this.text="SK"
			this.number = number;
		}
	},
	beforeMount() {
		Info();
	},
	mounted(){
		this.getKeyword()
	}
}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');

.keywordheader{
	margin: 29px 50px 10px 0px;
	height: 220px;
}
.side{
	position: sticky;
	top:100px;
	margin-top: 81px;
	width: 300px;
	height: 500px;
	text-align: center;
	padding: 30px 20px 0 20px;
  background-image: url("../components/images/sideback.jpg")
}
.under{
	width: 100%;
	height: 50px;
	background-color:#E5E5E5;
}
#chartdiv1, #chartdiv2, #chartdiv3, #chartdiv4 {
  width: 100%;
  height: 350px;
}
.sidehead{
	margin-bottom: 10px;
	color: white;
	font-size: 20px;
}
.sidebody{
	font-size: 15px;
	color: #E5E5E5;
	margin-top: 20px;
	margin-bottom: 10px;
}
strong{
	color: rgb(74, 204, 255);
}
</style>
