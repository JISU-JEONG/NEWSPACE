<template>
  <div>
      <div v-for="comment in comments" :key=comment.username>
          <v-card flat>
              <v-card-title class="cardtop">
                  {{comment.member_name}}
              </v-card-title>
              <v-card-text class="commentdate">
                {{comment.comment_date}}
              </v-card-text>
              <v-card-text class="commentbody black--text" >
                <strong>{{comment.comment_text}}</strong>
              </v-card-text>
          </v-card>
        <hr>
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
.cardtop{
    padding-bottom: 5px;
    font-size: 25px;
}
.commentbody{
    font-size: 15px;
    color: black;
    padding-top: 0;
}
.commentdate{
    font-size: 12px
}
</style>