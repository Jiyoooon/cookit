<template>
<div class="container">
        <!-- Post-->
        <div class="post-module">
            <!-- Thumbnail-->
            <div class="thumbnail">
                <div @click="gouserblog">
                  <b-img class="user_profile" :src='recipe.recipe_user_profileImage' style="cursor:pointer;" rounded="circle" thumbnail alt="Circle image"></b-img>
                </div>
                  <b-img :v-if="recipe.main_image" class="main_image" @click="fetchRecipe(recipe.recipe_id)" :src='recipe.main_image' style="cursor:pointer" center/>
              </div>
            <!-- Post Content-->
            <div class="post-content">
              <div @click="fetchRecipe(recipe.recipe_id)" style="cursor:pointer">
                <h6 class="title">{{ recipe.title }}</h6>
                <p class="description">{{ recipe.description }}</p>
              </div>
                <div class="post-meta">
                  <!-- <span class="comments">
                    <font-awesome-icon :icon="['fas', 'comment']" /><a href="#"> 39 comments</a>
                  </span> -->
                  <span class="like">
                    <v-btn v-if="isLoggedIn" @click="likefunction(recipe.recipe_id)" icon>
                      <v-icon v-show="isliked">mdi-heart</v-icon>
                      <v-icon v-show="!isliked">mdi-heart-outline</v-icon>
                    </v-btn>
                    <v-icon v-else v-show="!isliked">mdi-heart-outline</v-icon>{{ recipe.likeNum }}  
                  </span>
                  <span class="download">
                    <v-btn icon>
                      <v-icon>mdi-download</v-icon>
                    </v-btn>
                  </span>
                </div>
            </div>
        </div>
</div>
</template>

<script>
import { mapActions, mapState, mapMutations, mapGetters } from 'vuex'
import '@/plugins/fontawesome'

export default {
  name: 'recipeCard',
  data() {
      return {
        isliked: null,
        likenumber: null,
      }
    },
    props:{
      recipe: Object,
    },
    computed: {
      ...mapState('accounts', ['authUser']),
      ...mapGetters('accounts', ['isLoggedIn']),
      isLike() {
        if (this.recipe.like.includes(this.authUser.user_id)) return true
        else return false
      },
    },
    methods:{
        ...mapActions('recipes', ['fetchRecipe', 'recipeLike']),
         likefunction(recipe_id) {
          this.recipeLike(recipe_id)
          if (this.isliked && this.isLoggedIn) {
            this.recipe.likeNum--
            this.isliked = !this.isliked
          } else if (!this.isliked && this.isLoggedIn) {
            this.recipe.likeNum++
            this.isliked = !this.isliked
          }
        },
        gouserblog(){
          this.getUserInfo(this.recipe.recipe_user)
          if (this.authUser.user_id == this.recipe.recipe_user)
            this.$router.push({ name: 'MyBlogListView'})
          else
            this.$router.push({ name: 'UserBlogListView', params: { user_id: this.recipe.recipe_user } })
        },
        ...mapActions('myblog',['getUserInfo']),
        ...mapMutations('myblog', ['SET_USERINFO'])
    },
    created() {
      this.isliked = this.isLike
    }
}

</script>

<style scoped>
.post-module {
  box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 1;
  display: block;
  background: black;
  width: 100%;
  height: 100%;
}

.post-module:hover {
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.3);
}

.post-module .thumbnail .main_image {
  display: block;
  margin: 0 auto;
  transition: all .2s ease-in-out;
  position: relative;
  width: 100%;
  height: 100%;
}

.post-module:hover .thumbnail .main_image {
  -webkit-transform: scale(1.1);
  -moz-transform: scale(1.1);
  transform: scale(1.1);
  opacity: 0.6;
}

.post-module:hover .post-content .title {
  color: #53AAA1;
}
.post-module .post-content .description {
  overflow: hidden;
  max-height: 0px;
  color: gray;
  font-size: 14px;
  line-height: 1.8em;
  transition: all .2s ease-in-out;
}

.post-module:hover .post-content .description {
  display: block !important;
  opacity: 1 !important;
  max-height: 70px;
  margin-bottom: 20px;
  overflow: hidden;
  white-space: pre-wrap;
  text-overflow: ellipsis;
}

.post-module .thumbnail {
  height: 70%;
  overflow: hidden;
}

.post-module .thumbnail .user_profile {
  display: block;
  position: absolute;
  top: 15px;
  right: 15px;
  z-index: 1;
  width: 50px;
  height: 50px;
  text-align: center;
}

.post-module .thumbnail img {
  display: auto;
  width: 100%;
}

.post-module .post-content {
  position: absolute;
  bottom: 0;
  background: white;
  width: 100%;
  padding: 20px 15px 12px 15px;
}

.post-module .post-content .title {
  margin-bottom: 15px;
  max-height: 40px;
  color: black;
  font-weight: 700;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  transition: all .2s ease-in-out;
}

.post-module .post-content .post-meta {
  color: gray;
}

.post-module .post-content .post-meta a {
  color: lightgray;
  text-decoration: none;
}

.container {
  width: 100%;
  height: 340px;
  margin: 0 auto;
}

.container:before,
.container:after {
  content: '';
  display: block;
  clear: both;
}

</style>