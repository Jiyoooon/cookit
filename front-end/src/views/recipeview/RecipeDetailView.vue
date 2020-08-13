<template>
  <div>
      <div v-if="authUser">
        <div v-if="checkdeleteauth">
        <button @click="deleteRecipe(selectedRecipe.recipe_id)">삭제삭제</button>
        </div>
        <div v-if="checkdeleteauth">
        <button @click="gorecipeupdate">고고</button>
        </div>
        <div>
            <button @click="doPrint">레시피 출력하기</button>
        </div>
      </div>
      <recipe />
      <hr>
      <ingredient />
      <hr>
      <cookingstep />
      <div  id= "button" class="noprint">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
      </div>
      <div @click="scrollToTop" id= "button-bottom" class="noprint">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로" ></b-icon>
    </div>
      <!-- <b-button id="button" v-b-modal="'my-modal'">가로보기</b-button> -->
    <commentCreate />
    <commentList />
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
            
            text="ddd"
            ><h1>{{ cookingstep.description }}</h1></b-carousel-slide>
            </b-carousel>
        </b-modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import cookingstep from '../../components/recipeview/cookingstep.vue'
import ingredient from '../../components/recipeview/ingredient.vue'
import recipe from '../../components/recipeview/recipe.vue'
import commentCreate from '../../components/recipeview/commentCreate.vue'
import commentList from '../../components/recipeview/commentList.vue'

export default {
    name: 'recipeDetailView',
    components: {
        cookingstep,
        ingredient,
        recipe,
        commentCreate,
        commentList,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe']),
        ...mapState('accounts', ['authUser']),
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
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
        ...mapActions('editor', ['deleteRecipe']),
        gorecipeupdate() {
          console.log('nnnn')
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
        doPrint(){
           window.print();
        },
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

<style>
    #button {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 20vh;
        cursor: pointer;
    }
    #button-bottom{
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 10vh;
        cursor: pointer;
    }
    @media print  {
        .noprint {
            display: none;
        }  
    }
</style>