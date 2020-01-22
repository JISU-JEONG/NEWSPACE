<template>
  <fieldset class="main-search">
    <legend>전체 검색</legend>
    <label class="search-word" for="main-search">
      <span class="icon">검색 :</span>
      <input v-model="seacrValue" id="main-search" class="HomeSearchInput" name="main-search" type="text" maxlength="20" placeholder="검색어를 입력하세요"/>
      <span class="clear-btn">CLEAR</span>
    </label>
    <input class="btn-search" type="submit" value="search"/>
    <p>{{searchValue}}</p>
  </fieldset>
</template>

<script>
export default {
  data() {
    return {
      searchValue: ''
    }
  }
}
</script>

<style scoped>
    fieldset {
      border: none;
    }
    legend {
      display: none;
    }
    .main-search {
      position: relative;
      height: 50px;
      width: 600px;
      box-sizing: border-box;
      border-bottom: solid 2px black;
      margin: 20px auto 0 auto;
    }
    .search-word {
      position: absolute;
      top: 0;
      right: 70px;
      left: 50px;
    }
    .icon {
      position: absolute;
      top: 30%;
      left: -45px;
    }
    .HomeSearchInput {
      height: 60px;
      width: 500px;
      font-size: 20px;
      border: none;
      outline: none;
    }
    .clear-btn {
      position: absolute;
      top: 30%;
      right: 0;
      cursor: pointer;
    }
    .btn-search {
      width: 80px;
      height: 50px;
      box-sizing: border-box;
      position: absolute;
      right: -40px;
      border: solid 10px black;
      border-radius: 5px;
      background-color: black;
      outline: none;
      cursor: pointer;
      color: white;
      font-size: 20px;
    }
</style>