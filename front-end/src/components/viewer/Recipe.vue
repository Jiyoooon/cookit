<template>
  <b-container>
      <b-row>
      <b-col>
      <img :src="selectedRecipe.main_image" alt="mainimage" style="max-width:80vw;max-height:80vh;">
      <h2 style="text-align:center">{{ selectedRecipe.title }}</h2>
      </b-col>
      </b-row>
      <strong>
      <b-row style="text-align:center">
          <b-col>
            인원 : {{ this.serving[selectedRecipe.servings] }}
          </b-col>
          <b-col>
            난이도 : {{ this.level[selectedRecipe.level] }}
          </b-col>
          <b-col>
            조리시간 : {{ this.cooking_time[selectedRecipe.cooking_time] }}
          </b-col>
      </b-row>
      </strong>
      <hr>
      <b-row>
          <b-col lg-3 style="text-align:center">
            <b-img :src="recipeUser.image_url" rounded="circle" alt="Circle image" id="userimg"></b-img>
            {{ selectedRecipe.recipe_user_name }}<br>

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
          </b-col>
          <b-col lg-9>
            <p class="triangle-border left" style="font-size:1.2rem">"{{ selectedRecipe.description }}"</p>
          </b-col>
      </b-row>
      
       <!-- {{ recipeUser }} -->
       <br>
  </b-container>
</template>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
import { mapState } from 'vuex'
export default {
    name: 'recipe',
    data(){
        return{
            serving:['',"혼자","둘이서","3~4인","5인이상"],
            cooking_time:['',"15분이하","15~30분","30분~1시간","1~2시간","2시간이상"],
            level:['','★☆☆☆☆','★★☆☆☆','★★★☆☆','★★★★☆','★★★★★'],
            facebookUrl: null,
            curUrl: null
        }
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe', 'recipeUser']),
    },
    methods: {
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
    #userimg {
        height: 20vw;
        width: 20vw;
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