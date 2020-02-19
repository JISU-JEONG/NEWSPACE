<template>
  <div id="intro">
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
			
			<v-dialog
        v-model="dialog"
        width="500"
      	>
        <template v-slot:activator="{ on }">
          <div class="side-btn" 
						v-on="on">
					  주요 대상
				</div>
        </template>
        <v-card>
          <v-card-title
            class="headline grey lighten-2"
            primary-title
          >
           주요 대상자
          </v-card-title>
  
          <v-card-text>
						<br>
            SAMSUNG, LG, SK의 동향에 관심이 있으며 최신 뉴스들을 모아 보고 싶은 사용자
						<br>
						ex) 취준생, 주식 투자자, SSAFY 학생 등등
          </v-card-text>
  
          <v-divider></v-divider>
  
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="dialog = false"
            >
              닫기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
			<div class="side-btn" @click="guide">NEWSPACE 사용방법</div>
			<div class="side-btn" @click="$router.push({ path: '/AboutUs' })">ABOUT US</div>
	</div>  
</template>

<script>
import Driver from 'driver.js' // import driver.js
import 'driver.js/dist/driver.min.css' // import driver.js css

export default {
	name:"sidebanner",
	data(){
		return{
			driver: null,
			dialog: false,
		}
	},
	methods:{

		guide() {
			window.onscroll = function(){window.scrollTo(100,500);};
			const steps = [
				{
					element: '#intro',
					popover: {
						title: 'Introduce',
						description: '뉴스페이스 페이지 소개 입니다.',
						position: 'left'
					},
					onNext: () => {window.onscroll = function(){window.scrollTo(100,0);};},
 					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,500);};},
				},
				{
					element: '#chartdiv',
					popover: {
						title: 'CloudChart',
						description: '최근 한달 뉴스 주요키워드를 보여줍니다.',
						position: 'bottom', 
						offset: 750,
					},
					onNext: () => {window.onscroll = function(){window.scrollTo(0,0);};},
 					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,500);};}, 
				},
				{
					element: '#navbar',
					popover: {
						title: 'Page Search',
						description: '검색하고 싶은 키워드를 검색합니다.',
						position: 'bottom',
						offset: 750
					},
					onNext: () => {window.onscroll = function(){window.scrollTo(0,0);};},
					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,0);};},
				},
				{
					element: '#sidenav',
					popover: {
						title: 'Side Bar',
						description: '로그인 및 키워드별 최근 뉴스를 보여줍니다.',
						position: 'left'
					},
					onNext: () => {window.onscroll = function(){window.scrollTo(0,500);};},
					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,0);};},
				},
				{
					element: '#newstab',
					popover: {
						title: 'NewsList',
						description: '삼성 LG SK의 최신 뉴스를 보여줍니다.',
						position: 'right'
					},
					onNext: () => {window.onscroll = function(){window.scrollTo(0,500);};},
					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,0);};},
				},
				{
					element: '#chatting',
					opacity: 0,
					popover: {
						title: 'Message',
						description: '다른 사용자와 의견을 공유할수 있습니다.',
						position: 'top'
					},
					onPrevious: () => {window.onscroll = function(){window.scrollTo(0,500);};},
				}
				]
    this.driver.defineSteps(steps)
    this.driver.start()
    }
	},
	mounted(){
		window.onscroll = function(){window.scrollTo();};
		this.driver = new Driver({
				allowClose: false,
				doneBtnText: '확인',              // Text on the final button
				closeBtnText: '닫기',             // Background color for the staged behind highlighted element
				nextBtnText: '다음',              // Next button text for this step
				prevBtnText: '이전',
				stageBackground: 'rgb(255,255,255,0.6)',
				onReset: (Element) => {
					window.onscroll = function(){window.scrollTo();};
				}, 
			})
		},
	beforeDestroy(){
		window.onscroll = function(){window.scrollTo();};
		this.driver.reset()
	}
		
}
</script>

<style scoped>
.sidebody{
	font-size: 15px;
	color: #E5E5E5;
	margin-top: 20px;
	margin-bottom: 10px;

}
.sidebody strong{
	color: rgb(74, 204, 255);
}
#intro{
	/* position: sticky; */
	text-align: center;
	height: 500px;
	padding: 30px 20px 0 20px;
	box-shadow: 1px 2px 7px lightgray;
  background-image: url("../components/images/sideback.jpg");
}
.side-btn {
  width: 100%;
  height: 7%;
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(11,43,73,.3);
  border: 1px solid rgba(11,43,73,.6);
  font-size: 14px;
  font-weight: 300;
  color: white;
  cursor: pointer;
}
</style>