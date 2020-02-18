<template>
  <div>
    <strong>최신 뉴스 현황</strong>
    <hr><br>
    <div v-for=" i in AllList.length > 6 ? 6 : AllList.length" :key="i">
		 	<span v-line-clamp="1">[{{AllList[i-1].brand}}] {{AllList[i-1].title}}</span>
    </div>
  </div>
</template>



<script>
import http from '../services/http-common.js'

export default {
	name: "newsstatus",
	data() {
		return{
			AllList: []
		}
	},
	methods:{
		getAllNewsRecent(){
			http
				.get("/getAllNewsRecent")
				.then(response => (this.AllList = response.data))
				.catch(() => {
					this.errored = true;
				});
		},
	},
	mounted(){
		this.getAllNewsRecent()
	}
}
</script>

<style>

</style>
