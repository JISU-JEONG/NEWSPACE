<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import NewsList from '../components/NewsList'
import info from '../services/getInfo';
import store from "../store";

export default {
	name: 'HomePage',
	components: {
    	NewsList,
	},
	methods: {

	},
	mounted(){
		console.log("sica : " + localStorage.getItem("member_id"));

	},
	beforeMount() {
		info();
	}
}
</script>

<style scoped>
</style>
