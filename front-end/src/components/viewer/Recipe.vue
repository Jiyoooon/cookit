<template>
  <b-container fluid>
    <!-- 레시피 제목 -->
    <b-row align-v="center">
      <b-col class="text-center mr-3 ml-3">
        <h3 style="font-weight:700;">{{ selectedRecipe.title }}</h3>
      </b-col>
    </b-row>

    <hr style="margin: 0.5em 0">

    <b-row align-v="center">
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
    <b-row>
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

    <!-- SNS -->
    <b-row>
            <ul style="list-style:none;">
              <!-- facebook -->
              <li>
                <div>
                  <img @click="gofacebook" class="rounded" style="width:2em;" src="https://lh3.googleusercontent.com/proxy/dahn7bjEv7xTEHXJYUuZPw2FQ7_0UeteDU-JlxX8Z6azNskhsBsfVCvi0KNqZfGt-EXEDnO1_0nio4XlETrjOdLweArC5Ofq9UbT1H8jeLGttnpF-qd-AAQwmVflF8PxA7reb9iRwwaighvOzHxigRNugUu-SuJD90_LVBXcXCXaJQ2pAEPyg-xURiV2GDJIOCJABA">
                </div>
              </li>
              <!-- kakao -->
              <li>
                <div @click="shareByKakao" id="kakao-btn">
                  <img src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png" />
                </div>
              </li>
            </ul>
    </b-row>
       <br>
  </b-container>
</template>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
import { mapState, mapActions } from 'vuex'
export default {
    name: 'recipe',
    data(){
        return{
            serving:['', "혼자", "둘이서", "3~4인", "5인 이상"],
            cooking_time:['', "15분 이하", "15~30분", "30분~1시간", "1~2시간", "2시간 이상"],
            level:['', '★☆☆☆☆', '★★☆☆☆', '★★★☆☆', '★★★★☆', '★★★★★'],
            facebookUrl: null,
            curUrl: null
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
      gofacebook(){
        window.open(this.facebookUrl);
      },
      shareByKakao(){
        Kakao.Link.sendDefault({
          // container: '#kakao-link-btn',
          objectType: 'feed',
          content: {
            title: this.selectedRecipe.title, 
            description: this.selectedRecipe.description,
            imageUrl:
              this.selectedRecipe.main_image,
            link: {
              webUrl: this.curUrl,
              androidExecParams: 'test',
            },
          },
          social: {
            likeCount: this.selectedRecipe.likeNum-0,
            commentCount: this.selectedRecipe.comments-0,
            viewCount: this.selectedRecipe.hits-0
          },
          buttons: [
            {
              title: '보러가기',
              link: {
                webUrl: this.curUrl,
              },
            }
          ]
        });
      }
        // ...mapActions('recipes', ['fetchRecipeUser'])
    }, 
    created() { 
        // this.fetchRecipeUser()
        this.facebookUrl = "https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent('http://i3a201.p.ssafy.io/recipe/'+this.selectedRecipe.recipe_id);
        this.curUrl = "http://i3a201.p.ssafy.io/recipe/"+this.selectedRecipe.recipe_id;
    }
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

    .arrow_box {
        font-size: 2vw;
        position: relative;
        background: #c2c2c2;
        border: 4px solid #6e7070;
    }
    .arrow_box:after, .arrow_box:before {
        right: 100%;
        top: 50%;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
    }

    .arrow_box:after {
        border-color: rgba(194, 194, 194, 0);
        border-right-color: #c2c2c2;
        border-width: 15px;
        margin-top: -15px;
    }
    .arrow_box:before {
        border-color: rgba(194, 225, 245, 0);
        border-right-color: #6e7070;
        border-width: 21px;
        margin-top: -21px;
    }
    .triangle-border:before {
  content:"";
  position:absolute;
  bottom:-20px; /* value = - border-top-width - border-bottom-width */
  left:40px; /* controls horizontal position */
  border-width:20px 20px 0;
  border-style:solid;
  border-color:#5a8f00 transparent;
  /* reduce the damage in FF3.0 */
  display:block;
  width:0;
}

/* creates the smaller  triangle */
.triangle-border:after {
  content:"";
  position:absolute;
  bottom:-13px; /* value = - border-top-width - border-bottom-width */
  left:47px; /* value = (:before left) + (:before border-left) - (:after border-left) */
  border-width:13px 13px 0;
  border-style:solid;
  border-color:#fff transparent;
  /* reduce the damage in FF3.0 */
  display:block;
  width:0;
}
.triangle-border.right {
  margin-right:30px;
}
.triangle-border.top:before {
  top:-20px; /* value = - border-top-width - border-bottom-width */
  bottom:auto;
  left:auto;
  right:40px; /* controls horizontal position */
  border-width:0 20px 20px;
}

/* creates the smaller  triangle */
.triangle-border.top:after {
  top:-13px; /* value = - border-top-width - border-bottom-width */
  bottom:auto;
  left:auto;
  right:47px; /* value = (:before right) + (:before border-right) - (:after border-right) */
  border-width:0 13px 13px;
}

/* Variant : left
------------------------------------------ */

/* creates the larger triangle */
.triangle-border.left:before {
  top:10px; /* controls vertical position */
  bottom:auto;
  left:-30px; /* value = - border-left-width - border-right-width */
  border-width:15px 30px 15px 0;
  border-color:transparent #5a8f00;
}

/* creates the smaller  triangle */
.triangle-border.left:after {
  top:16px; /* value = (:before top) + (:before border-top) - (:after border-top) */
  bottom:auto;
  left:-21px; /* value = - border-left-width - border-right-width */
  border-width:9px 21px 9px 0;
  border-color:transparent #fff;
}

</style>