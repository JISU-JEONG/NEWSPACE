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
      <div class="wall left">생각처럼 안되네 개같은거</div>
      <div class="wall right">
        웹마스터 영길이 캐리한 팀입니다. <br>
        다음 프로젝트 캐리받을 사람 선착순 두명 받습니다. <br>
        그 다음 두명은 커피 하루 한잔 사준다는 전제하에 팀원으로 받아드립니다.
        <div class="mouse-hole"></div>
      </div>
    </div>
    <div class="profile-container">
      <div class="profile-nav" @click="selectMember">
        <div v-for="member in Object.keys(members)" :key="member" class="member-icon" :class="[members[member].cssClass]" :data-name="member"></div>
      </div>
      <div v-if="selectedMember" class="profile-content">
        <div class="profile-picture-box">
          <img class="profile-picture" :src="require(`../assets/images/${selectedMember}.jpg`)">
        </div>
        <div class="profile-info">
          <div class="info-box">{{members[selectedMember].name}}</div>
          <div class="info-box">{{members[selectedMember].info01}}</div>
          <div class="info-box">{{members[selectedMember].info02}}</div>
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
          imgUrl: './images/jisu.jpg',
          gitUrl: '',
          info01: '지수는 지수다',
          info02: '지수는 지수다2',
          cssClass: 'ji-su',
          color: '#64B5F6'
        },
        younghoon: {
          name: '정영훈',
          imgUrl: './images/younghoon.jpg',
          gitUrl: '',
          info01: '영훈이는 영훈이다.',
          info02: '영훈이는 영훈이다2',
          cssClass: 'young-hoon',
          color: '#BA68C8'
        },
        younggil: {
          name: '정영길',
          imgUrl: './images/younggil.jpg',
          gitUrl: '',
          info01: '영길이는 영길이다',
          info02: '영길이는 영길이다2',
          cssClass: 'young-gil',
          color: '#FFB74D'
        },
        taewoo: {
          name: '김태우',
          imgUrl: './images/taewoo.jpg',
          gitUrl: '',
          info01: '태우는 태우다',
          info02: '태우는 태우다2',
          cssClass: 'tae-woo',
          color: '#81C784',
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
      if (e.target.classList.contains('member-icon')) {
        const earthElem = document.querySelector('.earth')

        this.selectedMember = e.target.dataset.name
        earthElem.style.background = this.members[this.selectedMember].color

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
    animation: drop 2s 3s forwards;
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
    transform: translateZ(-10vw);
  }
  .profile-picture {
    box-shadow: 2px 3px 8px lightgray;
    border-radius: 3%;
  }
  .profile-info {
    width: 60%;
    transform: translateZ(-20vw);
    transform-style: preserve-3d;
  }
  .info-box {
    margin: 4%;
    height: 26%;
    padding: 30px;
    font-size: 3rem;
    background: rgba(255, 255, 255, 0.9);
    box-shadow: 2px 3px 8px lightgray;
    transform: rotateY(-15deg);
    font-family: 'Black Han Sans', sans-serif;
  }

</style>