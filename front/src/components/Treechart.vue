<template>
  <div id="chartdiv"></div>
</template>

<script>
export default {
		name :"treechart",
		data(){
			return{
				chart: null
			}
		},
		props:{
			keyword : {type:Array}
		},
    methods: {
				chartUpdate(){
					var kdata = []
					for(let i=0; i < this.keyword.length;i++)
					{
						kdata.push({name:this.keyword[i], value:10})
					}
					this.chart.data = [
						{
							name: "Keyword",
							value: 15,
							children :kdata
						}
					]
				},
				showChart() {
					am4core.disposeAllCharts();
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

					var chart = am4core.create("chartdiv", am4plugins_forceDirected.ForceDirectedTree);
					var networkSeries = chart.series.push(new am4plugins_forceDirected.ForceDirectedSeries())

					var kdata = []
					for(let i=0; i < this.keyword.length;i++)
					{
						kdata.push({name:this.keyword[i], value:10})
					}
					chart.data = [
						{
							name: "Keyword",
							value: 15,
							children :kdata
						}
					]
					networkSeries.dataFields.value = "value";
					networkSeries.dataFields.name = "name";
					networkSeries.dataFields.children = "children";
					// networkSeries.nodes.template.tooltipText = "{name}:{value}";
					networkSeries.nodes.template.fillOpacity = 1;

					networkSeries.nodes.template.label.text = "{name}"
					networkSeries.fontSize = 20;

					networkSeries.links.template.strokeWidth = 1;

					networkSeries.maxLevels = 1;

					var hoverState = networkSeries.links.template.states.create("hover");
					hoverState.properties.strokeWidth = 3;
					hoverState.properties.strokeOpacity = 1;

					networkSeries.nodes.template.events.on("over", function(event) {
					event.target.dataItem.childLinks.each(function(link) {
							link.isHover = true;
					})
					if (event.target.dataItem.parentLink) {
							event.target.dataItem.parentLink.isHover = true;
					}

					})

					networkSeries.nodes.template.events.on("out", function(event) {
					event.target.dataItem.childLinks.each(function(link) {
							link.isHover = false;
					})
					if (event.target.dataItem.parentLink) {
							event.target.dataItem.parentLink.isHover = false;
					}
				})
				var subtitle = chart.titles.create();
				subtitle.text = "(click to open)";
				subtitle.fontSize = 20;
				var title = chart.titles.create();
				title.text = "사용자 선택 키워드"
				title.fontSize = 40;
        		title.fontWeight = "800";
				this.chart = chart
		}	
	},
	watch: {
		keyword: function(){
			this.chartUpdate()
		}
	},
	mounted(){
		this.showChart()
	},
	beforeDestroy(){
		am4core.disposeAllCharts();
	}
}

</script>

<style scoped>
#chartdiv {
  width: 100%;
  height:650px;
  max-width:100%;
	background-color: #e2e2e280;
	box-shadow: 1px 3px 7px grey;
}
</style>