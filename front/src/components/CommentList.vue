<template>
  <div>
      <div v-for="comment in comments" :key=comment.username>
          <v-card flat>
              <v-card-title class="cardtop">
                  {{comment.member_name}}
              </v-card-title>
              <v-card-text class="commentdate">
                {{comment.comment_date}}
                <button v-if="comment.member_id==member_id" @click="deleteComment(comment)">X</button>
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
import axios from 'axios'

export default {
    name: 'comment',
    props:{
        comments:{type:Array},
    },
    data(){
        return{
            member_id : 0,
        }
    },
    methods: {
        deleteComment(comment) {
            var flag = confirm("댓글을 삭제하시겠습니까?");
            if(!flag) return
            const storage = localStorage;
            const token = {
                headers: {
                  "login-token": storage.getItem("login-token")
                }
              }
            console.log(storage.getItem("login-token"))
            axios.delete(`http://192.168.31.85:8080/deleteComment/${comment.comment_id}`, token)
                .then(response => {
                    // const target = this.todos.find(el => {
                    //     return el === todo
                    // })
                    // const idx = this.todos.indexOf(target)
                    const idx = this.comments.indexOf(comment)
                    if (idx > -1) {
                        this.comments.splice(idx, 1)
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
    },
    beforeMount(){
        this.member_id = localStorage.getItem("member_id");
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