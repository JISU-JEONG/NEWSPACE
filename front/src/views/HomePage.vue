<template>
  <div>
		<div class="newsbox">
	    <NewsList></NewsList>
		</div>
		<div class="subbox">
			asd
		</div>
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
	beforeMount() {
		info();
	}
}
</script>

<style scoped>
.newsbox{
	display: inline-block;
	padding: 2% 2%;
	width: 70%;
}
.subbox{
	display: inline-block;
	width: 30%;
}
</style>
