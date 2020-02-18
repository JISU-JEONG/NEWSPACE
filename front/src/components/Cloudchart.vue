<template>
<div>
  <div id="chartdiv"></div>
</div>
</template>

<script>
export default {
  name: "cloudchart",
  data(){
    return{
      chart : null,
      series : null
    }
  },
  props:{
    keywords :{type: Array},
    num : {type: Number, default: 0},
  },
  watch: {
		num: function(){
			this.chartUpdate()
    }
  },
  methods:{
    chartUpdate(){
      this.series.data = this.keywords[this.num]
      if(this.num===0){
        this.series.heatRules.push({
				"target": this.series.labels.template,
				"property": "fill",
				"min": am4core.color("#0000CC"),
				"max": am4core.color("#CC00CC"),
				"dataField": "value"
				});
      }
      else if(this.num===1){
        this.series.heatRules.push({
				"target": this.series.labels.template,
				"property": "fill",
				"min": am4core.color("#AADDDD"),
				"max": am4core.color("#1428A0"),
				"dataField": "value"
				});
      }
      else if(this.num===2){
        this.series.heatRules.push({
				"target": this.series.labels.template,
				"property": "fill",
				"min": am4core.color("#AA00CC"),
				"max": am4core.color("#EE0034"),
				"dataField": "value"
				});
      }
      else{
        this.series.heatRules.push({
				"target": this.series.labels.template,
				"property": "fill",
				"min": am4core.color("#CCAACC"),
				"max": am4core.color("#00CC33"),
				"dataField": "value"
				});
      }
    },
    showChart(){
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
				am4core.disposeAllCharts();
				// Themes begin
				am4core.useTheme(am4themes_animated);
				// Themes end
				var chart = am4core.create("chartdiv", am4plugins_wordCloud.WordCloud);

				chart.fontFamily = "Courier New";
				var series = chart.series.push(new am4plugins_wordCloud.WordCloudSeries());
				series.randomness = 0.1;
				series.rotationThreshold = 0.5;
				series.minFontSize = am4core.percent(5)
				series.maxFontSize = am4core.percent(25)
        series.data = this.keywords[0]
				series.dataFields.word = "keyword";
				series.dataFields.value = "count";

				series.heatRules.push({
				"target": series.labels.template,
				"property": "fill",
				"min": am4core.color("#0000CC"),
				"max": am4core.color("#CC00CC"),
				"dataField": "value"
				});

				series.labels.template.url = "http://192.168.31.85:8081/search/{word}";
				series.labels.template.urlTarget = "_self";
        series.labels.template.tooltipText = "{word}: {value}";
        
				var hoverState= series.labels.template.states.create("hover");
				hoverState.properties.fill = am4core.color("#FF0000");

				// var subtitle = chart.titles.create();
				// subtitle.text = "(click to open)";

				// var title = chart.titles.create();
				// title.fontSize = 20;
        		// title.fontWeight = "800";
        this.chart = chart
        this.series = series
    }
  },
  mounted(){
    this.showChart()
    this.chartUpdate()
  }
}
</script>

<style>
#chartdiv {
  width: 100%;
  height: 350px;
}
</style>