<template>
	<form @submit.prevent="CommentCreate">
      <label for="text">댓글: </label>
      <input
      v-model="text"
      type="text" id="text">
      <button type="submit">등록</button>
  </form>
</template>

<script>
export default {
    name: 'commentForm',
    data(){
        return{
            text: ''
        }
    },
    methods: {
        CommentCreate() {
            this.$emit('commentCreate-event', this.text)
            this.text = ''
        }
    }
}
</script>

<style>

</style>