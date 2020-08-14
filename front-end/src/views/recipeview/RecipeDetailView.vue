<template>
  <div id="detail-view">
      <button data-html2canvas-ignore="true" @click="makePDF">PDF</button>
      <div><button @click="doPrint">레시피 출력하기</button></div>

      <recipe class="view-container"/>
      <ingredient class="view-container"/>
      <cooking-step class="view-container"/>

        <!-- Floating Button -->
        <v-overlay :value="this.overlay" >
            <div style="cursor:pointer; width:100%; height:100%" @click="overlay = false; console.log('꺼져랏') ">
                <v-btn
                    icon
                    id="button1"
                >
                <v-icon>mdi-close</v-icon>
                </v-btn>
                <div class="timerpos" v-if="timerstate != -1">
                    <timervue @set-timerstate ="setTimerState" :t="timervalue"/>
                </div>
            </div>
        </v-overlay>

        <div id = "button1">
            <v-btn
            @click="setTimerValue('00:04'); (overlay = !overlay);"
            >
            3초
            </v-btn>
            <v-btn
            @click="setTimerValue('00:11'); (overlay = !overlay);"
            >
            10초
            </v-btn>
        </div>

      <!-- <b-button id="button" v-b-modal="'my-modal'" class="noprint">가로보기</b-button> -->
      <font-awesome-icon id="top-btn" class="noprint" @click="scrollToTop" :icon="['fas', 'angle-up']" />

    <!-- 댓글 -->
    <commentCreate v-if="isLoggedIn" data-html2canvas-ignore="true"/>
    <commentList data-html2canvas-ignore="true"/>

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
import { mapState, mapActions, mapGetters } from 'vuex'
import cookingStep from '@/components/viewer/CookingStep.vue'
import ingredient from '@/components/viewer/Ingredient.vue'
import recipe from '@/components/viewer/Recipe.vue'
import commentCreate from '@/components/viewer/CommentCreate.vue'
import commentList from '@/components/viewer/CommentList.vue'
import html2canvas from 'html2canvas'
import jsPDF from 'jspdf' 
import timervue from '@/components/viewer/Timer.vue'

export default {
    name: 'recipeDetailView',
    data(){
        return{
            timervalue: "99:00",
            timerstate:-1,
            overlay:false,
        }
    },
    components: {
        cookingStep,
        ingredient,
        recipe,
        commentCreate,
        commentList,
        timervue,
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
        setTimerValue(t){
            console.log("타이머값을" + "00:00" + "로")
            this.timervalue = t
            this.setTimerState(1)
            console.log(this.timervalue)
        },
        setTimerState(s){
            console.log(s)
            this.timerstate = s
            console.log(this.timerstate)
        },
        gorecipeupdate() {
          console.log('nnnn')
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
        ...mapActions('editor', ['deleteRecipe']),
        makePDF () {
            window.html2canvas = html2canvas //Vue.js 특성상 window 객체에 직접 할당해야한다.
            // let that = this
            let pdf = new jsPDF('p', 'mm', 'a4')
            window.scrollTo(0, 0);
            let canvas = pdf.canvas
            const pageWidth = 210 //캔버스 너비 mm
            const pageHeight = 295 //캔버스 높이 mm
            canvas.width = pageWidth

            let ele = document.querySelector('#recipe')
            let width = ele.offsetWidth // 셀렉트한 요소의 px 너비
            let height = ele.offsetHeight // 셀렉트한 요소의 px 높이
            let imgHeight = pageWidth * height/width // 이미지 높이값 px to mm 변환

            if(!ele){
                console.warn('recipe is not exist.')
                return false
            }
            html2canvas(ele).then(function(canvas) {
                let position = 0
                const imgData = canvas.toDataURL('image/png')

                return new Promise(function() {
                    window.setTimeout(function() {
                        pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight, undefined, 'slow')
                        let heightLeft = imgHeight //페이징 처리를 위해 남은 페이지 높이 세팅.
                        heightLeft -= pageHeight
                        while (heightLeft >= 0) {
                        position = heightLeft - (imgHeight * 0.97)
                        pdf.addPage();
                        pdf.addImage(imgData, 'png', 0, position, pageWidth, imgHeight)
                        heightLeft -= pageHeight
                        }
                        console.log(pdf)
                        pdf.save('cookit-recipe'.toLowerCase() +'.pdf')
                    })
                })
                //Paging 처리
            },

            );	
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
    #button1 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 40vh;
        cursor: pointer;
        z-index: 1;
    }
    #button2 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 30vh;
        cursor: pointer;
    }

#detail-view {
  width: 900px;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

@media (max-width: 1225px) {
  #detail-view {
    width: 100%;
  }
}

.view-container {
  margin-bottom: 2em;
}

#top-btn {
    position: fixed;
    right: 3.5%;
    bottom: 1em;
    background-color: white;
    border-radius: 10%;
    box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
    font-size: 2.8rem;
    width: 1em;
    height: 1em;
    padding: 2px;
    z-index: 10;
    cursor: pointer;
}

@media (max-width: 768px) {
  #top-btn {
    font-size: 2rem;
    right: 2%;
  }
}

@media (max-width: 496px) {
  #top-btn {
    bottom: 0.5em;
  }
}

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