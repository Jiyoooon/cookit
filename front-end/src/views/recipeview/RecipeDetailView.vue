<template>
  <div id="detail-view">
      <recipe class="view-container"/>
      <share :selectedRecipe="selectedRecipe" class="view-container" data-html2canvas-ignore="true" />
      <ingredient class="view-container"/>
      <cooking-step class="view-container"/>

        <!-- 버튼 -->
        
      <font-awesome-icon id="read-btn" class="noprint" v-b-modal="'my-modal'" :icon="['fas', 'book-open']" @click="setModalState(true)"/>
      <font-awesome-icon id="top-btn" class="noprint" @click="scrollToTop" :icon="['fas', 'angle-up']" />

    <!-- 댓글 -->
    <commentCreate v-if="isLoggedIn" data-html2canvas-ignore="true" class="view-container"/>
    <commentList data-html2canvas-ignore="true" class="view-container"/>

    <!-- 가장 상위에 타이머 오버레이를 둠 -->
    <timeroverlay style="z-index:1050" />

    
    <!-- The modal -->
    <b-modal size="xl" id="my-modal" title="쿠킹스텝" @hide="stopSpeaking">
        <template v-slot:modal-title>
            쿠킹스탭 <b-button @click="doSpeech" id="speechButton">음성인식 시작</b-button>
        </template>
        <b-carousel
            v-model="slide"
            ref="recipeCarousel"
            id="carousel-fade"
            style="text-shadow: 1px 1px 2px #000"
            fade
            indicators
            controls
            :interval="0"
            
            img-width=100
            img-height=100
            >
        <b-carousel-slide
            :id="'slide-'+cookingstep.steps"
            v-for="cookingstep in selectedRecipe.cookingStep"
            :key="cookingstep.cooking_steps_id"
            :caption="(String)(cookingstep.steps)"
            :img-src="cookingstep.step_image"
            style="height:70vh"
            >
            <!-- modal-des클래스에 스타일 적용시키면 될듯 -->
            <timeDescription class="modal-des" :description='cookingstep.description' :time='cookingstep.time' :number="'sub-des-' + cookingstep.steps"/>
        </b-carousel-slide>
            </b-carousel>
        </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters, mapMutations } from 'vuex'
import recipe from '@/components/viewer/Recipe.vue'
import share from '@/components/viewer/Share.vue'
import ingredient from '@/components/viewer/Ingredient.vue'
import cookingStep from '@/components/viewer/CookingStep.vue'
import commentList from '@/components/viewer/CommentList.vue'
import commentCreate from '@/components/viewer/CommentCreate.vue'
import timeroverlay from '@/components/viewer/TimerOverlay.vue'
import timeDescription from '@/components/viewer/TimeDescription.vue'
export default {
    name: 'recipeDetailView',
    data(){
        return{
            slide:0,
            isSpeaking:false,
            modalState:false,
            timenum:0,
            modalShow:true,
        }
    },
    components: {
        recipe,
        ingredient,
        cookingStep,
        share,
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
        setModalState(state){
            if(state == true){
                if(this.overlay == true){
                    this.setTimerOverlay = false
                }
            }
            this.modalState = state
        },
        
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        gorecipeupdate() {
          console.log('nnnn')
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
            document.getElementById("speechButton").textContent="음성인식 종료";
        },
        stopSpeaking(){
            console.log("음성인식 stop");
            this.isSpeaking = false;
            this.recognition.stop();
            document.getElementById("speechButton").textContent="음성인식 시작";
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
    created() {
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
                let next = ['다음','형','황','방','항'
                            , '앞으로'
                            , '넥스트'];
                let prev = ['이전'
                            , '뒤로','위로','귀로','디로'
                            ,];
                let timer = ['타이머'];
                let timerclose = ['종료','닫기'];

                var self = this;

                next.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay==false){
                            self.$refs.recipeCarousel.next();
                        }
                        else{
                            let maxtime = self.selectedRecipe.cookingStep[self.slide].time.length-1
                            self.timenum++
                            self.timenum = (self.timenum < maxtime)? self.timenum : maxtime
                            self.startTimer(self.selectedRecipe.cookingStep[self.slide].time[self.timenum][2])
                        }
                    }
                })
                prev.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay==false){
                            self.$refs.recipeCarousel.prev();
                        }
                        else{
                            //console.log("타이머이전")
                            let mintime = 0
                            self.timenum--
                            self.timenum = (self.timenum > mintime)? self.timenum : mintime
                            self.startTimer(self.selectedRecipe.cookingStep[self.slide].time[self.timenum][2])
                        }
                    }
                })
                timer.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        //console.log("타이머 작동");
                        if(self.selectedRecipe.cookingStep[self.slide].time.length){
                            self.timenum = 0
                            self.startTimer(self.selectedRecipe.cookingStep[self.slide].time[0][2])
                        }
                        // self.setTimerOverlay(true)
                    }
                })
                timerclose.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        if(self.overlay == true){
                            self.SET_TIMER_INIT()
                        }
                        else{
                            console.log("타이머 동작중이아님")
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
    /* 모달에있는 글에 적용할 css */
    .modal-des{

    }
</style>