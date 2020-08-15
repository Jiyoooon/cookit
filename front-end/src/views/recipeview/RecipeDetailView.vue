<template>
  <div id="detail-view">
      <recipe class="view-container"/>
      <share :selectedRecipe="selectedRecipe" class="view-container" data-html2canvas-ignore="true" />
      <ingredient class="view-container"/>
      <cooking-step class="view-container"/>

        <!-- 타이머 -->
        <timeroverlay/>

      <font-awesome-icon id="read-btn" class="noprint" v-b-modal="'my-modal'" :icon="['fas', 'book-open']" />
      <font-awesome-icon id="top-btn" class="noprint" @click="scrollToTop" :icon="['fas', 'angle-up']" />

    <!-- 댓글 -->
    <commentCreate v-if="isLoggedIn" data-html2canvas-ignore="true" class="view-container"/>
    <commentList data-html2canvas-ignore="true" class="view-container"/>

    <!-- The modal -->
    <b-modal size="xl" id="my-modal" title="쿠킹스텝" @hide="stopSpeaking">
        <template v-slot:modal-title>
            쿠킹스탭 <b-button @click="doSpeech" id="speechButton">음성인식 시작</b-button>
        </template>
        <b-carousel
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
            v-for="cookingstep in selectedRecipe.cookingStep"
            :key="cookingstep.cooking_steps_id"
            :caption="(String)(cookingstep.steps)"
            :img-src="cookingstep.step_image"
            style="height:70vh"
            text="ddd">
            <h1>{{ cookingstep.description }}</h1>
        </b-carousel-slide>
            </b-carousel>
        </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions, mapGetters } from 'vuex'
import recipe from '@/components/viewer/Recipe.vue'
import share from '@/components/viewer/Share.vue'
import ingredient from '@/components/viewer/Ingredient.vue'
import cookingStep from '@/components/viewer/CookingStep.vue'
import commentList from '@/components/viewer/CommentList.vue'
import commentCreate from '@/components/viewer/CommentCreate.vue'

export default {
    name: 'recipeDetailView',
    
    components: {
        recipe,
        ingredient,
        cookingStep,
        share,
        commentList,
        commentCreate,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe']),
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
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        gorecipeupdate() {
          console.log('nnnn')
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
        ...mapActions('editor', ['deleteRecipe']),
        startSpeaking() {
            console.log("음성인식 start");
            this.isSpeaking = true;
            this.recognition.start();
            document.getElementById("speechButton").textContent="음석인식 종료";
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
                let next = ['다음', '앞으로', '넥스트'];
                let prev = ['이전', '뒤로'];
                let timer = ['타이머'];

                var self = this;

                next.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        self.$refs.recipeCarousel.next();
                    }
                })
                prev.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        self.$refs.recipeCarousel.prev();
                    }
                })
                timer.forEach(function (item) {
                    if(text.indexOf(item) != -1){
                        console.log("타이머 작동");
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
    width: 33px;
    height: 33px;
  }
  #read-btn {
    font-size: 2rem;
    width: 33px;
    height: 33px;
    bottom: 2.2em;
  }
}

@media (max-width: 496px) {
  #top-btn {
    bottom: 1.2em;
  }
  #read-btn {
    bottom: 2.5em;
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
</style>