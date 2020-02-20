<template>
  <div>
	  <div>
			<div v-if="keywords.length !== 0" class="keyheader">
					<Cloudchart
				:keywords="keywords"
				:num="number"
				/>
			</div>
				<div v-if="flag" class="under" >
					{{todaytext}} : <strong> {{ commaToay }} </strong> 건 | {{alltext}} : <strong>{{ commaAll }}</strong> 건
				</div>
		</div>
	<v-container>
	  <v-row>
		<v-col 
		sm=12
		md=9>
	    <NewsList @brancdCheck-event="brandCheck"></NewsList>
		</v-col>
		<v-col
		class="d-none d-sm-none d-md-flex"
		md=3>
			<div class="side">
				<Sidebanner/>
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
import Cloudchart from "../components/Cloudchart"
import Sidebanner from '../components/Sidebanner'
export default {
	name: 'HomePage',
	data(){
		return {
			number : 0,
			todaytext : "금일 뉴스",
			todaycount : null,
			alltext : "전체 뉴스",
			allcount : null,
			status : [],
			keywords:[],
			flag : false,
		}
	},
	components: {
		NewsList,
		Cloudchart,
		Sidebanner,
	},
	computed: {
		commaToay(){
			return this.addComma(this.todaycount)
		},
		commaAll(){
			return this.addComma(this.allcount)
		}
		
	},
	methods: {
		getKeyword(){
			http.get('/getChartKeyword')
				.then(response => {
					const keywords = response.data
					this.keywords = keywords
				})
				.catch(e => {
					console.log(e);
				});
		},
		brandCheck(number){
			if(number===0) {
				this.todaytext = "금일 뉴스"
				this.todaycount = this.status[4].count
				this.alltext = "전체 뉴스"
				this.allcount = this.status[0].count
			}
			if(number===1){
				this.todaytext = "금일 삼성 뉴스"
				this.todaycount = this.status[5].count
				this.alltext = "전체 삼성 뉴스"
				this.allcount = this.status[1].count
			} 
			if(number===2) {
				this.todaytext = "금일 LG 뉴스"
				this.todaycount = this.status[6].count
				this.alltext = "전체 LG 뉴스"
				this.allcount = this.status[2].count
			}
			if(number===3) {
				this.todaytext = "금일 SK 뉴스"
				this.todaycount = this.status[7].count
				this.alltext = "전체 SK 뉴스"
				this.allcount = this.status[3].count
			}
			this.number = number;
		},
		getStatus(){
			http.get('/getNewsStatus')
				.then(response => {
						this.status = response.data
						this.todaytext = "금일 뉴스"
						this.todaycount = this.status[4].count
						this.alltext = "전체 뉴스"
						this.allcount = this.status[0].count
						this.flag = true
					})
					.catch(e => {
						console.log(e);
					});

		},
		addComma(number) {
			var num = String(number);
			if (!num) return 0;
			if (num.length <= 3) {
				return num;
			}

			var count = Math.floor((num.length - 1) / 3);

			var result = "";
			for (var i = 0; i < count; i++) {
				var length = num.length;
				var strCut = num.substr(length - 3, length);

				num = num.slice(0, length - 3);
				result = "," + strCut + result;
			}
			result = num + result;
			return result;
		},
	},
	beforeMount() {
		Info();
		this.getKeyword()
		this.getStatus()
	},
	mounted(){
		
	},
	beforeDestroy(){
		am4core.disposeAllCharts();
	}

}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap');
.keyheader{
	padding: 20px 30px;
}
.keywordheader{
	margin: 29px 50px 10px 0px;
	height: 220px;
}
.side{
	/* position: sticky; */
	top:100px;
	margin-top: 81px;
	width: 300px;
	height: 500px;
}
.under{
	width: 100%;
	height: 50px;
	background-color:#E5E5E5;
	text-align: center;
	line-height: 50px;
	font-size: 15px;
}
.sidehead{
	margin-bottom: 10px;
	color: white;
	font-size: 20px;
}

.under strong{
	font-size: 25px;
	color: #FF5500;
}

</style>
