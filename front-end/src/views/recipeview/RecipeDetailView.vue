<template>
  <v-app id="inspire">
    <div id="detail-view">
      <recipe class="view-container"/>
      <share :selectedRecipe="selectedRecipe" class="view-container" data-html2canvas-ignore="true" />
      <ingredient class="view-container"/>
      <cooking-step class="view-container"/>
      <tags v-if="selectedRecipe.tag[0]" :selectedRecipe="selectedRecipe" class="view-container"/>

      <!-- 댓글 -->
      <comment-create v-if="isLoggedIn" data-html2canvas-ignore="true" class="view-container"/>
      <comment-list data-html2canvas-ignore="true" class="view-container"/>

      <!-- 플로팅 버튼 -->
      <font-awesome-icon id="read-btn" class="noprint" v-b-modal="'my-modal'" :icon="['fas', 'book-open']" @click="setDialogState(true)"/>
      <font-awesome-icon id="top-btn" class="noprint" @click="scrollToTop" :icon="['fas', 'angle-up']" />

      <!-- 가장 상위에 타이머 오버레이를 둠 -->
      <timeroverlay style="z-index:1050" />
    
      <!-- 읽기모드 -->
        <v-dialog
          v-model="dialogState"
          @click:outside="dialogClose"
          @keydown.esc="dialogClose"
          @keydown.left="dialogPageMove('l')"
          @keydown.right="dialogPageMove('r')"
          height="720"
          width="960"
        >
        <v-card
          elevation="24"
          class="mx-auto"
        >
          <v-card-title>
            <span class="subtitle">Step. {{ page + 1 }}</span>
            <v-spacer></v-spacer>
            <i class="mdi mdi-close" @click="dialogClose" style="cursor: pointer; color: #888; padding: 0px 5px;"></i>
          </v-card-title>

        <v-carousel
          v-model="page"
          :continuous="false"
          :cycle="false"
          :show-arrows="true"
          hide-delimiter-background
          delimiter-icon="mdi-minus"
          height="450"
          ref="recipe-carousel"
        >
          <v-carousel-item
            v-for="cookingstep in selectedRecipe.cookingStep"
            :key="cookingstep.cooking_steps_id"
            class="no-drag"
          >
            <v-sheet
              height="100%"
              tile
            >
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
              <b-img :src="cookingstep.step_image" height="450"></b-img>
              </v-row>
              <v-row
                align="end"
                justify="center">
                <div class="display-3">Step. {{ cookingstep.step }}</div>
              </v-row>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>

          <!-- <b-carousel
            v-model="page"
            ref="recipeCarousel"
            id="carousel-fade"
            class="carousel-style no-drag"
            indicators
            controls
            no-wrap
            :interval="0"
            img-height="320px"
            img-width="480px"
          >
            <b-carousel-slide
              :id="'slide-'+cookingstep.steps"
              v-for="cookingstep in selectedRecipe.cookingStep"
              :key="cookingstep.cooking_steps_id"
              :caption="'Step. '+(String)(cookingstep.steps)"
              :img-src="cookingstep.step_image"
              class="no-drag"
              img-height="320px"
              img-width="480px"
            >
            </b-carousel-slide>
          </b-carousel> -->

          <v-list style="margin: 5px 20px;">
            <timeDescription class="read-mode"
              :description='propdescription' :time='proptime' :number="'sub-des-' + page"/>
          </v-list>
        </v-card>
      </v-dialog>
    </div>
  </v-app>
</template>

<script>
import { mapState, mapActions, mapGetters, mapMutations } from 'vuex'
import recipe from '@/components/viewer/Recipe.vue'
import share from '@/components/viewer/Share.vue'
import ingredient from '@/components/viewer/Ingredient.vue'
import cookingStep from '@/components/viewer/CookingStep.vue'
import tags from '@/components/viewer/Tags.vue'
import commentList from '@/components/viewer/CommentList.vue'
import commentCreate from '@/components/viewer/CommentCreate.vue'
import timeroverlay from '@/components/viewer/TimerOverlay.vue'
import timeDescription from '@/components/viewer/TimeDescription.vue'
// import $ from 'jquery'

export default {
    name: 'recipeDetailView',
    data(){
        return{
          dialogState: false,  // 읽기모드가 열리면 true가 되고 false로 바꾸면 모달이 닫힘
          isSpeaking: false,
          modalState: false,
          timenum: 0,
          page: 1,
          proptime: [],
          propdescription: '',
        }
    },
    components: {
        recipe,
        ingredient,
        cookingStep,
        share,
        tags,
        commentList,
        commentCreate,
        timeroverlay,
        timeDescription,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe','overlay']),
        ...mapState('accounts', ['authUser']),
        ...mapGetters('accounts', ['isLoggedIn']),
        checkdeleteauth() {
            if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
                return true
            } else {
                return false
            }
        },
    },
    methods: {
      dialogPageMove(dir){
            if(dir== 'l'){ // 왼쪽이동
              (this.page > 0 )? this.page-- : this.page
            }
            else if(dir == 'r'){ // 오른쪽 이동
              (this.page < this.selectedRecipe.cookingStep.length-1 )? this.page++ : this.page
            }
      },
      dialogClose() {
        this.page = 1;
        this.dialogState = false;
            this.SET_TIMER_INIT()
        this.doSpeech();
      },
      setDialogState(state){
          if(state == true){
            if(this.overlay == true){
              this.setTimerOverlay = false
              }
          }
          this.page = 0
          this.dialogState = state

          this.doSpeech(); // 읽기모드에서 음성인식 자동시작

        // 모달 창이 열릴 때 스크롤 없애기 & 부모창 스크롤 및 터치 방지
        // $('html, body').css({'overflow': 'hidden', 'height': '100%'}); // 모달팝업 중 html,body의 scroll을 hidden시킴
        // $('#my-modal').on('scroll touchmove mousewheel', function(event) { // 터치와 마우스휠 스크롤 방지
        //   event.preventDefault();
        //   event.stopPropagation();
        //   return false;
        // });
      },
        
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        gorecipeupdate() {
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments','startTimer']),
        ...mapActions('editor', ['deleteRecipe']),
        ...mapMutations('recipes',['SET_TIMER_INIT']),
        startSpeaking() {
            console.log("음성인식 start");
            this.isSpeaking = true;
            this.recognition.start();
            // document.getElementById("speechButton").textContent="음성인식 종료";
        },
        stopSpeaking(){
            console.log("음성인식 stop");
            this.isSpeaking = false;
            this.recognition.stop();
            // document.getElementById("speechButton").textContent="음성인식 시작";

            
          // 모달창 닫힐 때 부모창 상태 원상태로 돌리기
          // $('html, body').css({'overflow': 'inherit', 'height': '100%'}); // scroll hidden 해제
          // $('#my-modal').off('scroll touchmove mousewheel', () => {return true;}); // 터치 및 마우스휠 스크롤 가능
        },
        doSpeech(){
            if(!this.isSpeaking){
                this.startSpeaking();
            } else {
                this.stopSpeaking();
            }
        },
        setTimerOverlay(s){
            this.overlay = s
        },
    },
    mounted() {
      console.log("detail-mounted")
      console.log(this.selectedRecipe)
    },
    created() {
      console.log("detail-created")
      console.log(this.selectedRecipe)

        this.fetchRecipe(this.$route.params.recipe_id),
        this.fetchRecipeUser()
        this.fetchComments()
        
        if (!('webkitSpeechRecognition' in window)) {
            document.getElementById("speechButton").style.display = "none";
        } else {
            this.recognition = new window.webkitSpeechRecognition;

            this.recognition.continuous = false;
            this.recognition.lang = 'ko-KR';
            this.recognition.interimResults = false;

            this.recognition.onresult = (event) => {
                var text = event.results[event.resultIndex][0].transcript;
                console.log(text);
                let next = ['다음', '담', '당', '탐', '정', '형', '황', '방', '항', '앞으로', '아크로', '넥스트'];
                let prev = ['이전', '이정', '뒤로', '위로', '귀로', '디로', '기록'];
                let timer = ['타이머', '타임', '차이머'];
                let timerclose = ['종료', '닫기' , '중지','그만'];

                var self = this;

                next.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay==false){
                            (self.page < self.selectedRecipe.cookingStep.length-1 )? self.page++ : self.page
                        }
                        else{
                            // 타이머 다음
                            let maxtime = self.selectedRecipe.cookingStep[self.page].time.length-1
                            self.timenum++
                            self.timenum = (self.timenum < maxtime)? self.timenum : maxtime
                            self.startTimer(self.selectedRecipe.cookingStep[self.page].time[self.timenum][2])
                        }
                    }
                })

                prev.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay==false){
                            (self.page > 0 )? self.page-- : self.page
                        }
                        else{
                            //console.log("타이머이전")
                            let mintime = 0
                            self.timenum--
                            self.timenum = (self.timenum > mintime)? self.timenum : mintime
                            self.startTimer(self.selectedRecipe.cookingStep[self.page].time[self.timenum][2])
                        }
                    }
                })
                timer.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        //console.log("타이머 작동");
                        if(self.selectedRecipe.cookingStep[self.page].time.length){
                            self.timenum = 0
                            self.startTimer(self.selectedRecipe.cookingStep[self.page].time[0][2])
                        }
                        // self.setTimerOverlay(true)
                    }
                })
                timerclose.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay == false){
                            self.dialogClose()
                        }
                        else{
                            self.SET_TIMER_INIT()
                        }
                    }
                })
            };

            this.recognition.onerror = (event) => {
                console.log(`Error occurred in recognition: ${event.error}`);
            };

            this.recognition.onend = () => {
                if(this.isSpeaking){
                    this.recognition.start();
                }
            }
        }
        this.SET_TIMER_INIT()
    },
    watch: {
      page:{
        handler(){
          if(!this.selectedRecipe.cookingStep[this.page]) return;
          this.proptime = this.selectedRecipe.cookingStep[this.page].time
          this.propdescription = this.selectedRecipe.cookingStep[this.page].description
        }
      }
    },
    beforeDestroy() {
        this.stopSpeaking()
    },
}
</script>

<style scoped>
#detail-view {
  width: 90%;
  min-height: 200vh;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 4em 1em 8em 1em;
}

@media (max-width: 496px) {
  #detail-view {
    width: 100%
  }
}

.view-container {
  width: 900px;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  margin-bottom: 2em;
}

@media (max-width: 1225px) {
  .view-container {
    width: 100%;
  }
}

#read-btn {
    position: fixed;
    bottom: 2.5em;
    right: 3.5%;
    background-color: white;
    border-radius: 10%;
    box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
    font-size: 2.5rem;
    width: 45px;
    height: 45px;
    padding: 2px;
    z-index: 10;
    cursor: pointer;
}

#top-btn {
    position: fixed;
    bottom: 1em;
    right: 3.5%;
    background-color: white;
    border-radius: 10%;
    box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
    font-size: 2.8rem;
    width: 45px;
    height: 45px;
    padding: 2px;
    z-index: 10;
    cursor: pointer;
    font-size: 2.8rem;
}

@media (max-width: 768px) {
  #top-btn {
    font-size: 2rem;
    width: 34px;
    height: 34px;
  }
  #read-btn {
    font-size: 2rem;
    width: 34px;
    height: 34px;
    bottom: 2.3em;
  }
}

@media (max-width: 496px) {
  #top-btn {
    bottom: 1.4em;
  }
  #read-btn {
    bottom: 2.8em;
  }
}

.timerpos {
  background-color: #eee;
  z-index: 1;
  position: absolute;
  width: 100%;
  height: 100%;
}

@media print  {
  .noprint {
    display: none;
  }  
}

.no-drag {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

/* 모달에있는 글에 적용할 css */
.read-mode {
  font-size: 1.1em;
  margin: 0.5em 0.2em 0.8em 0.2em;
}

</style>