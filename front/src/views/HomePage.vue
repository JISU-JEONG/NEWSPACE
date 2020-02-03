<template>
  <div>
    <NewsList></NewsList>
  </div>
</template>

<script>
import NewsList from '../components/NewsList'
import Info from '../services/getInfo';
import store from "../store";

export default {
	name: 'HomePage',
	components: {
    	NewsList,
	},
	methods: {

	},
	beforeMount() {
		Info();
	}
}
</script>

<style scoped>
</style>
