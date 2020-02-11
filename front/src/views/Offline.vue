<template>
  <v-container>
    <offline @detected-condition="handleConnectivityChange"> </offline>

    <div v-if="!status">
      <div id="quiz">
        <h1>Un quiz ?</h1>
        <v-list-item-group>
          <h1>{{ questions[index].question }}</h1>
          <v-list-item
            v-for="(item, index) in questions[index].answers"
            :key="index"
          >
            <v-list-item-content>
              <v-list-item-title @click="action(index)">{{
                item
              }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <h1 v-show="finish">{{ index }} / {{ score }}</h1>
        <button v-show="finish" @click="reset()">처음으로 돌아가기</button>
      </div>
      <minesweeper-game :rows="8" :cols="15" :bombs="30"></minesweeper-game>
    </div>
    <div v-else>
      <tetris></tetris>
    </div>
  </v-container>
</template>

<script>
import offline from "v-offline";
import quizdata from "../../public/quiz_data";
import tetris from "./tetris/tetris.vue";

export default {
  data() {
    return {
      status: true,
      finish: false,
      index: 0,
      score: 0,
      questions: quizdata
    };
  },
  components: {
    offline,
    tetris
  },
  methods: {
    handleConnectivityChange(status) {
      console.log(status);
      if (status) {
        this.status = true;
      } else {
        this.status = false;
      }
    },
    action(index) {
      if (this.index == this.questions.length - 1) {
        this.finish = true;
      } else {
        this.index++;
      }

      if (index === this.questions[this.index].ok) {
        this.score++;
      }
    },
    reset() {
      this.index = 0;
      this.score = 0;
      this.finish = false;
    }
  },
  watch: {
    index(val) {
      console.log("index : " + val);
    },
    score(val) {
      console.log("score : " + val);
    }
  }
};
</script>
