<template>
  <div id="detail-view">
      <recipe class="view-container"/>
      <ingredient class="view-container"/>
      <cooking-step class="view-container"/>
      <!-- <div  id= "button">
            <b-icon icon="book" v-b-modal="'my-modal'" scale="1" v-b-tooltip.hover title="가로보기"></b-icon>
      </div>
      <div @click="scrollToTop" id= "button-bottom">
            <b-icon icon="arrow-up-circle" scale="1" v-b-tooltip.hover title="가장위로" ></b-icon>
    </div> -->
      <!-- <b-button id="button" v-b-modal="'my-modal'">가로보기</b-button> -->
      <font-awesome-icon id="top-btn" @click="scrollToTop" :icon="['fas', 'angle-up']" />
    <commentCreate />
    <commentList />
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
import { mapState, mapActions } from 'vuex'
import cookingStep from '@/components/viewer/CookingStep.vue'
import ingredient from '@/components/viewer/Ingredient.vue'
import recipe from '@/components/viewer/Recipe.vue'
import commentCreate from '@/components/viewer/CommentCreate.vue'
import commentList from '@/components/viewer/CommentList.vue'

export default {
    name: 'recipeDetailView',
    components: {
        cookingStep,
        ingredient,
        recipe,
        commentCreate,
        commentList,
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe']),
    },
    methods: {
        scrollToTop(){
            window.scroll({top:0,left:0,behavior:'smooth'})//==scroll(0,0)과 같다 => 0,0위치로 이동하는 메소드
        },
        ...mapActions('recipes', ['fetchRecipe', 'fetchRecipeUser', 'fetchComments']),
    },
    created() {
        this.fetchRecipe(this.$route.params.recipe_id),
        this.fetchRecipeUser()
        this.fetchComments()
    },
}
</script>

<style>

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
</style>