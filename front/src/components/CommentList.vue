<template>
  <div>
    <v-card flat v-for="comment in comments" :key="comment.comment_date">
      <v-card-title class="cardtop pt-2">
        {{comment.member_name}}
        <span class="comment-date ml-3 mr-5">{{comment.comment_date}}</span>
        <v-btn
          absolute
          right
          icon
          v-if="comment.member_id==$store.state.member_id"
          @click="deleteComment(comment)" 
        >
          <v-icon>fa-times</v-icon>
        </v-btn>
      </v-card-title>        
      <v-card-text class="commentbody pb-2">
        <strong>{{comment.comment_text}}</strong>
      </v-card-text>
    </v-card>
    <hr>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "comment",
  props: {
    comments: { type: Array }
  },
  data() {
    return {
      member_id: 0
    };
  },
  methods: {
    deleteComment(comment) {
      var flag = confirm("댓글을 삭제하시겠습니까?");
      if (!flag) return;
      const storage = localStorage;
      const token = {
        headers: {
          "login-token": storage.getItem("login-token")
        }
      };
      axios
        .delete(
          `http://192.168.31.85:8080/api/comment/${comment.comment_id}`,
          token
        )
        .then(response => {
          const idx = this.comments.indexOf(comment);
          if (idx > -1) {
            this.comments.splice(idx, 1);
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  mounted() {
    const payload = {
      token: localStorage.getItem("login-token"),
      member_id: localStorage.getItem("member_id")
    };
    this.$store.dispatch("login", payload);
  }
};
</script>

<style scoped>
  .cardtop {
    padding-bottom: 5px;
    font-size: 16px;
  }
  .commentbody {
    font-size: 16px;
    color: black;
    padding-top: 0;
  }
  .comment-date {
    font-size: 12px;
  }
</style>