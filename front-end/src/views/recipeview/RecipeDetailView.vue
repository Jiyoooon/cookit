<template>
  <div id="recipe">
      <!-- <div v-if="authUser" data-html2canvas-ignore="true"> -->
        <div v-if="authUser && checkdeleteauth" data-html2canvas-ignore="true">
        <button @click="deleteRecipe(selectedRecipe.recipe_id)">삭제삭제</button>
        </div>
        <div v-if="authUser && checkdeleteauth" data-html2canvas-ignore="true">
        <button @click="gorecipeupdate">고고</button>
        </div>
      <!-- </div> -->
      <button data-html2canvas-ignore="true" @click="makePDF">PDF</button>
      <recipe />
      <hr>
      <ingredient />
      <hr>
      <cookingstep />
      <div id="button" data-html2canvas-ignore="true">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
      </div>
      <div @click="scrollToTop" id= "button-bottom" data-html2canvas-ignore="true">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로" ></b-icon>
    </div>
      <!-- <b-button id="button" v-b-modal="'my-modal'">가로보기</b-button> -->
    <commentCreate v-if="isLoggedIn" data-html2canvas-ignore="true"/>
    <commentList data-html2canvas-ignore="true"/>
        <!-- The modal -->
        <b-modal size="xl" id="my-modal" title="쿠킹스텝">
            <b-carousel
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
import cookingstep from '../../components/recipeview/cookingstep.vue'
import ingredient from '../../components/recipeview/ingredient.vue'
import recipe from '../../components/recipeview/recipe.vue'
import commentCreate from '../../components/recipeview/commentCreate.vue'
import commentList from '../../components/recipeview/commentList.vue'
import html2canvas from 'html2canvas'
import jsPDF from 'jspdf' 

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
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
        ...mapActions('editor', ['deleteRecipe']),
        gorecipeupdate() {
          console.log('nnnn')
          if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
              this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
          }
        },
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
    },
    created() {
        this.fetchRecipe(this.$route.params.recipe_id),
        this.fetchRecipeUser()
        this.fetchComments()
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
</style>