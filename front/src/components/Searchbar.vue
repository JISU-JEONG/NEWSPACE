<template>
<div>
  <form class="form-body" @submit.prevent="searchEvent()">
    <v-icon>mdi-magnify</v-icon>
    <input class="search-input" @keyup.esc="clearInput()" v-model="inputValue" type="text" placeholder="검색어를 입력하세요">
    <span class="clear-btn" @click="clearInput()" v-if="inputValue">clear</span>
    <input class="submit-input" type="submit" value="search">
  </form>
</div>
</template>

<script>
export default {
  name: 'SearchBar',
  data() {
    return {
      inputValue: ''
    }
  },
  methods: {
    clearInput() {
      this.inputValue = ''
    },
    searchEvent() {
      if (!this.inputValue) {
        // TODO : 비어있는 상태로 검색시 뭔가 행동이 있으면 좋겠다 - placeholder가 흔들린다거나 등
        console.log('검색어를 입력하세요')
        return
      }
      console.log('이곳에 검색 코드 들어갑니다.')
      this.inputValue = ''
    }
  }
}
</script>

<style scoped>
  .form-body {
    position: relative;
    margin: 15px auto 0 auto ;
    max-width: 500px;
    text-align: center;
  }
  .search-input {
    padding: 5px 10px;
    width: 80%;
    outline: none;
    border: none;
    border-bottom: solid 2px black;
    box-sizing: border-box;
  }
  .submit-input {
    height: 35px;
    width: 70px;
    border: none;
    background-color: black;
    color: white;
    font-size: 17px;
    border-radius: 3px;
    box-sizing: border-box;
  }
  .clear-btn {
    position: absolute;
    cursor: pointer;
    color: gray;
    font-size: 20px;
    top: 5%;
    right: 17%;
  }
</style>