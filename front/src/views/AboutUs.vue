<template>
  <div class="earth">
    <div class="room">
      <div class="wall">
        <div class="team-name">A.M.T</div> <br>
        <div style="position:relative">
          <div class="team-name-detail-fake">A.I Machine-learning Team</div>
          <div class="team-name-detail-real">All Man Team</div>
        </div>
      </div>
      <div class="wall left"></div>
      <div class="wall right">
        웹마스터 영길이 캐리한 팀입니다. <br>
        다음 프로젝트 캐리받을 사람 선착순 두명 받습니다. <br>
        그 다음 두명은 커피 하루 한잔 사준다는 전제하에 팀원으로 받아드립니다.
        <div class="taewoo-text"></div>
        <div class="mouse-hole"></div>
      </div>
    </div>
    <div class="profile-container">
      <div class="profile-nav" @click="selectMember">
        <div class="home-icon">
          <v-icon>mdi-home</v-icon>
        </div>
        <div v-for="member in Object.keys(members)" :key="member" class="member-icon" :class="[members[member].cssClass]" :data-name="member"></div>
      </div>
      <div v-if="selectedMember" class="profile-content">
        <div class="profile-picture-box">
          <img class="profile-picture" :src="require(`../assets/images/${selectedMember}.jpg`)">
        </div>
        <div class="profile-info">
          <div class="first-box">
            <div class="info-box name">
              {{members[selectedMember].name}}
              <span style="font-size:2vw">{{members[selectedMember].major}}</span>
            </div>
            <div class="info-box git">
              <a :href="members[selectedMember].gitUrl" target="_blank">
                <v-icon>fa-github</v-icon>
              </a>
            </div>
          </div>
          <div class="info-box second-box">
            {{members[selectedMember].info01}}
            <div class="info01-detail">이것저것 했습니다. </div>
          </div>
          <div class="info-box third-box">
            <div style="padding: 0.5vw 3vw; font-size: 2vw;">기술 스택</div>
            <v-row>
              <v-col cols="6"  v-for="skill in members[selectedMember].skills" :key="skill.name" class="pt-1">
                <v-row>
                  <v-col cols="3" class="d-flex justify-center align-center pa-0">
                    <v-icon class="skill-icon">{{skill.name}}</v-icon>
                  </v-col>
                  <v-col cols="8" class="d-flex justify-center align-center pa-0">
                    <v-progress-linear
                      :color="members[selectedMember].color"
                      height="1.5vw"
                      :value="skill.rate"
                      striped
                    ></v-progress-linear>
                  </v-col>
                </v-row>
                
              </v-col>
            </v-row>
          </div>
        </div>
      </div>
    </div>
  </div> 
</template>

<script>

export default {
  data() {
    return {
      mousePos : { x:0, y:0 },
      selectedMember: '',
      memberIcon: 'member-icon',
      members: {
        jisu: {
          name: '정지수',
          major: '메카트로닉스공학부',
          imgUrl: './images/jisu.jpg',
          gitUrl: 'https://github.com/JISU-JEONG',
          info01: 'Front-end',
          cssClass: 'ji-su',
          color: '#64B5F6',
          skills: [
            { name: 'mdi-language-c',rate: 80 },
            { name: 'mdi-language-cpp',rate: 80 },
            { name: 'mdi-language-python',rate: 90 },
            { name: 'mdi-language-javascript',rate: 60 },
            { name: 'mdi-vuejs',rate: 80 },
          ],
        },
        younghoon: {
          name: '정영훈',
          major: '정보통신공학과',
          imgUrl: './images/younghoon.jpg',
          gitUrl: 'https://github.com/hoonyhoony',
          info01: 'Back-end',
          cssClass: 'young-hoon',
          color: '#BA68C8',
          skills: [
            { name: 'mdi-language-java',rate: 100 },
            { name: 'mdi-language-cpp',rate: 60 },
            { name: 'mdi-language-javascript',rate: 75 },
            { name: 'mdi-aws',rate: 100 },
            { name: 'mdi-vuejs',rate: 30 },
            { name: 'mdi-language-python',rate: 10 },
          ],
        },
        younggil: {
          name: '정영길',
          major: '컴퓨터공학과',
          imgUrl: './images/younggil.jpg',
          gitUrl: 'https://github.com/yg1110',
          info01: 'Back-end',
          cssClass: 'young-gil',
          color: '#FFB74D',
          skills: [
            { name: 'mdi-language-java',rate: 60 },
            { name: 'mdi-language-javascript',rate: 60 },
            { name: 'mdi-aws',rate: 50 },
            { name: 'mdi-vuejs',rate: 80 },
            // { name: 'mdi-language-mysql',rate: 50 }, 아이콘 없는 애들
            // { name: 'mdi-language-spring',rate: 70 },
            // { name: 'mdi-language-mongoDB',rate: 50 },
          ],
        },
        taewoo: {
          name: '김태우',
          major: '수학과',
          imgUrl: './images/taewoo.jpg',
          gitUrl: 'https://github.com/EHwooKim',
          info01: 'Front-end',
        cssClass: 'tae-woo',
          color: '#81C784',
          skills: [
            { name: 'mdi-language-python',rate: 65 },
            { name: 'mdi-language-javascript',rate: 55 },
            { name: 'mdi-vuejs',rate: 75 },
            { name: 'mdi-language-html5',rate: 70 },
            { name: 'mdi-language-css3',rate: 60 },
          ],
        }
      }
    }
  },
  methods: {
    onMouseMove(e) {
      this.mousePos.x = -1 + (e.clientX / window.innerWidth) * 2
      this.mousePos.y = 1 - (e.clientY / window.innerHeight) * 2
      document.querySelector('.room').style.transform = `rotateX(${this.mousePos.y * 8}deg) rotateY(${this.mousePos.x * 8}deg)`
      document.querySelector('.profile-container').style.transform = `rotateX(${this.mousePos.y * 8}deg) rotateY(${this.mousePos.x * 8}deg)`
    },
    selectMember(e) {
      const earthElem = document.querySelector('.earth')
      if (e.target.classList.contains('member-icon')) {
        this.selectedMember = e.target.dataset.name
        earthElem.style.background = this.members[this.selectedMember].color
      } else if (e.target.classList.contains('home-icon') || e.target.parentNode.classList.contains('home-icon')) {
        this.selectedMember = ''
        earthElem.style.background = '#E0E0E0'
        this.$router.push({ path: '/' })
      }
    },
  },
  mounted() {
    window.addEventListener('mousemove', this.onMouseMove)
  },
  beforeDestroy() {
    window.removeEventListener('mousemove', this.onMouseMove)
  },
}
</script>

<style scoped>
  div {
    box-sizing: border-box;
  }
  a {
    text-decoration: none;
  } 
  .earth {
    width: 100vw;
    height: 100vh;
    position: fixed;
    left:0;
    top:0;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #E0E0E0;
    perspective: 1000px;
    transition: 1s;
  }
  .room {
    width: 100vw;
    height: 100vh;
    position: absolute;
    left: 0;
    top: 0;
    transform-style: preserve-3d;
  }
  .go-back {
    position:absolute;
    left: 50vw;
    top: 0;
    font-size: 2rem;
  }
  .wall {
    width: 100vw;
    height: 100vh;
    position: absolute;
    border: 1px solid black;
    padding: 0 2vw;
    left: 0;
    top: 0;
    background: #fff;
    transform: translateZ(-50vw);
    transform-style: preserve-3d;
    font-family: 'Black Han Sans', sans-serif;
  }
  .team-name {
    font-size: 12rem;
  }
  .team-name-detail-fake {
    font-size: 3rem;
    position: absolute;
    padding: 0 8px;
    top: 0;
    left: 0;
    background: #fff;
    z-index:2;
    transform: rotate(2deg);
    border: 0.1px solid black;
    animation: drop 2s 2s forwards;
  }
  @keyframes drop {
    0% {
      opacity: 1;
    } 
    35% {
      opacity: 1;
      transform: 
        rotate(10deg);
    }
    40% {
      opacity: 1;
      transform: 
        rotate(8deg);      
    }
    60% {
      opacity: 0;
    }
    100% {
      opacity: 0;
      transform:
        translateY(150vh)
    }   
  }
  .team-name-detail-real {
    font-size: 3rem;
    padding-left:8px;
  }
  .left {
    left: -50vw;
    width: 100vw;
    transform: rotateY(90deg);
    text-align: center; 
    font-size: 2rem;
  }
  .right {
    left: 50vw;
    width: 100vw;
    transform: rotateY(-90deg);
    font-size: 2rem;
  }
  .mouse-hole {
    display: flex;
    position: absolute;
    left: 10vw;
    bottom: 0;
    width: 5vw;
    height: 7vw;
    background: #000;
    border-radius: 50px 50px 0 0;
  } 
  .profile-container {
    width: 100vw;
    height: 100vh;
    position: absolute;
    left: 0;
    top: 0;
    transform-style: preserve-3d;
  }
  .profile-nav {
    width: 100%;
    height: 10%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .member-icon {
    width: 10vh;
    height: 10vh;
    margin: 0 1vw -1vw 0;
    color: white;
    border-radius: 100px;
    box-shadow: 0 0 3px 2px black;
    background-repeat: none;
    background-size:cover;
    cursor: pointer;
    /* transition: 0.1s; */
  }
  .home-icon {
    width: 10vh;
    height: 10vh;
    margin: 0 1vw -1vw 0;
    color: white;
    border-radius: 100px;
    box-shadow: 0 0 3px 2px black;
    background: white;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    }
  .home-icon i {
    font-size: 3rem;
    color:black;
  }
  /* .member-icon:hover {
    transform: scale(1.1)
  } */
  .ji-su {
    background-image: url('../assets/images/jisu.jpg')
  }
  .young-gil {
    background-image: url('../assets/images/younggil.jpg');
  }
  .young-hoon {
    background-image: url('../assets/images/younghoon.jpg');
  }
  .tae-woo {
    background-image: url('../assets/images/taewoo.jpg');
  }
  .taewoo-text {
    width:15%;
    height:15%;
    background-image: url('../assets/images/taewooText.png');
    background-repeat: none;
    background-size: cover;
  }
  .profile-content {
    width: 100%;
    height: 90%;
    display: flex;
    transform-style: preserve-3d;
  }
  .profile-picture-box {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40%;
    transform: translateZ(-20vw);
  }
  .profile-picture {
    box-shadow: 2px 3px 8px lightgray;
    border-radius: 3%;
  }
  .profile-info {
    width: 60%;
    transform: translateZ(-20vw) rotateY(-15deg);
    /* box-shadow:0 0 10px 10px #000; */
  }
  .first-box {
    display: flex;
    margin-bottom: 2vw;
    height: 26%;
  }
  .second-box {
    height: 26%;
    margin-bottom: 2vw;
    padding: 1vw 3vw;
    font-size: 2.5vw;
  }
  .info01-detail {
    font-size: 2vw;
  }
  .third-box {
    /* margin: 4%; */
    height: 39%;
  }
  .info-box {
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 2px 3px 8px lightgray;
    font-family: 'Black Han Sans', sans-serif;
  }
  .name {
    width: 75%;
    padding: 0 0 0 3vw;
    font-size: 8vw;
  }
  .git {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 25%;
    margin-left:2vw;
    /* height: 100%; */
  }
  .git i {
    font-size: 10vw;
    opacity: 1;
    color: black;
    cursor: pointer;
  }
  .skill-box {
    display: flex;
    box-shadow:0 0 10px 10px #000;
  }
  .skill-icon {
    font-size: 3vw;
    color: black;

  }
  .skill-rate {
    width: 70%;
  }


</style>