<template>
  <div>
      <div v-for="comment in comments" :key=comment.username>
          {{comment.member_name}} => {{comment.comment_text}} {{comment.comment_date}}
      </div>
  </div>
</template>

<script>
export default {
    name: 'comment',
    props:{
        comments:{type:Array},
    }
}
</script>

<style>

</style>