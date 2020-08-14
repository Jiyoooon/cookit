<template>
  <b-container fluid>
    <!-- 레시피 제목 -->
    <b-row align-v="center">
      <b-col class="text-center mr-3 ml-3">
        <h3 style="font-weight:700;">{{ selectedRecipe.title }}</h3>
      </b-col>
    </b-row>

    <hr style="margin: 0.5em 0">

    <b-row align-v="center" data-html2canvas-ignore="true">
      <!-- 유저 아이콘, 별명, 작성일 -->
      <b-col cols="8" class="text-left ml-3 mr-n3">
        <b-img :src="recipeUser.image_url" rounded="circle" thumbnail id="user_profile"/>
        <div style="display: inline; position: absolute; padding: 5px 10px">
          <h5>{{ selectedRecipe.recipe_user_name }}</h5>
          <div style="font-size: 0.8em; color: lightgray;">{{ selectedRecipe.create_date }}</div>
        </div>
      </b-col>
        
      <!-- 수정 & 삭제 버튼 -->
      <b-col v-if="authUser&&checkdeleteauth" cols="4" class="text-right mr-3 ml-n3">
        <div class="inline-block-btn btn-style1" style="font-size:0.8em; margin: 0.5em;"
        @click="gorecipeupdate"> 수정 </div>
        <div class="inline-block-btn btn-style4" style="font-size:0.8em; margin: 0.5em;"
        @click="deleteRecipe(selectedRecipe.recipe_id)"> 삭제 </div>
      </b-col>
    </b-row>

    <b-row><b-col></b-col></b-row>

    <!-- 레시피 메인 사진 -->
    <b-row data-html2canvas-ignore="true">
      <b-col>
        <b-img :src="selectedRecipe.main_image"
        style="max-width: 90%; max-height: 80vh;"/>
      </b-col>
    </b-row>

    <b-row class="m-1"><b-col></b-col></b-row>

    <!-- 레시피 소개말 -->
      <b-row>
        <b-col cols="1"></b-col>
        <b-col cols="10" class="text-center">
          <div v-if="selectedRecipe.description" style="text-align: center;">
            <div style="display:flex;"><font-awesome-icon :icon="['fas', 'quote-left']" style="color: gray; float: left;"/></div>
            <div style="padding: 0.5em;">{{ selectedRecipe.description }}</div>
            <div><font-awesome-icon :icon="['fas', 'quote-right']" style="color: gray; float: right;"/></div>
          </div>
        </b-col>
      </b-row>

      <b-row class="m-1"><b-col></b-col></b-row>

    <!-- 레시피 정보 -->
    <b-row>
      <b-col cols="1"></b-col>
      <b-col cols="10" class="info-title">
        <div>
          <div id="serving">인원</div>
          <div id="serving-item">{{ this.serving[selectedRecipe.servings] }}</div>
        </div>
        <div>
          <div id="time">소요시간</div>
          <div id="time-item">{{ this.cooking_time[selectedRecipe.cooking_time] }}</div>
        </div>
        <div>
          <div id="level">난이도</div>
          <div id="level-item">{{ this.level[selectedRecipe.level] }}</div>
        </div>
      </b-col>
      <b-col cols="1"></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
    name: 'recipe',
    data(){
        return{
            serving:['', "혼자", "둘이서", "3~4인", "5인 이상"],
            cooking_time:['', "15분 이하", "15~30분", "30분~1시간", "1~2시간", "2시간 이상"],
            level:['', '★☆☆☆☆', '★★☆☆☆', '★★★☆☆', '★★★★☆', '★★★★★'],
        }
    },
    computed: {
      ...mapState('recipes', ['selectedRecipe', 'recipeUser']),
      ...mapState('accounts', ['authUser']),
      checkdeleteauth() {
        if (this.authUser.user_id == this.selectedRecipe.recipe_user) return true
        else return false
        },
    },
    methods: {
      ...mapActions('editor', ['deleteRecipe']),
      gorecipeupdate() {
        if (this.authUser.user_id == this.selectedRecipe.recipe_user) {
          this.$router.push({ name: 'RecipeUpdateView', params: { recipe_id: this.selectedRecipe.recipe_id }})
        }
      },
      // ...mapActions('recipes', ['fetchRecipeUser'])
    },
}
</script>

<style>
#user_profile {
  display: inline;
  height: 60px;
  width: 60px;
}

.info-title, .info-item {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.info-title #serving,
.info-title #time,
.info-title #level {
  justify-content: center;
  align-items: center;
  position: relative;
  display: flex;
  font-weight: 700;
}

.info-title #serving-item,
.info-title #time-item,
.info-title #level-item {
  position: relative;
  display: flex;
  text-align: center;
  margin-top: 1em;
}

.info-title #serving::after,
.info-title #time::after,
.info-title #level::after {
  content: "";
  position: absolute;
  left: 50%;
  bottom: -10%;
  transform: translateX(-50%);
  width: 120%;
  height: 5px;
  background: #53AAA1;
}
</style>