<template>
  <v-card>
    <v-img :src="imgSrc" height="200px">
    </v-img>
    <v-card-title primary-title>
      <div>
		  <!--
        <div v-if="title.length<10" class="headline">{{title}}</div>
		<div v-else class="headline">{{title.substring(0,10)+"..."}}</div>
		-->

		<div v-line-clamp="lineTitle">{{title}}</div>

		<!--
        <span style="font-size:1.5vw" class="grey--text">{{body}}</span>
		-->
		<span v-line-clamp="lineBody" style="font-size:1.5vw" class="grey--text">{{body}}</span>

      </div>
    </v-card-title>
  </v-card>
</template>

<script>

const VueLineClamp = window['vue-line-clamp'];

export default {
	name: 'Portfolio',
	props: {
		date: {type: String},
		title: {type: String},
		body: {type: String},
		imgSrc: {type: String},
	},
	data() {
		return {
			//
			lineTitle: 1,
			lineBody: 4
		}
	}
}
</script>

