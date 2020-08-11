<template>
  <v-card
    max-width="344"
    min-height="344"
    class="mx-auto"
    outlined
  >

    <v-list-item-title 
      class="headline"
      @click="fetchRecipe(recipe.recipe_id)"
      style="cursor:pointer; padding: 3%; padding-bottom: 0;"
    ><h5><strong>{{ recipe.title }}</strong></h5><hr></v-list-item-title>
    <v-img
        :src='recipe.main_image'
        height="194"
        @click.native="fetchRecipe(recipe.recipe_id)"
        style="cursor:pointer"
    ></v-img>
    <v-divider class="mx-4"></v-divider>

    <!-- <v-card-text class="maintext">
        <span>{{ recipe.description }}</span>
    </v-card-text> -->
    <v-card-actions>
      <v-btn @click="likefunction(recipe.recipe_id)" icon>
        <v-icon v-show="isliked">mdi-heart</v-icon>
        <v-icon v-show="!isliked">mdi-heart-outline</v-icon>
      </v-btn>
      {{ recipe.likeNum }}
      <v-btn icon>
        <v-icon>mdi-download</v-icon>
      </v-btn>
      <v-list-item>
      <v-spacer></v-spacer>
      <v-list-item-avatar  style="cursor:pointer" @click="gouserblog"> <img 
        :src='recipe.recipe_user_profileImage'></v-list-item-avatar>
      <!-- <v-list-item-content>
        <v-list-item-subtitle ><span style="cursor:pointer">by {{ recipe.recipe_user_name}}</span></v-list-item-subtitle>
      </v-list-item-content> -->
    </v-list-item>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'
export default {
    name:'RecipeCard',
    data() {
      return {
        isliked: null,
        likenumber: null,
      }
    },
    props:{
      recipe:Object,
    },
    computed: {
      ...mapState('accounts', ['authUser']),
      isLike() {
        if (this.recipe.like.includes(this.authUser.user_id)) {
          return true
        } else {
          return false
        }
      }
    },
    methods:{
        ...mapActions('recipes', ['fetchRecipe', 'recipeLike']),
         likefunction(recipe_id) {
          this.recipeLike(recipe_id)
          if (this.isliked) {
            this.recipe.likeNum--
            // this.likenumber--
            this.isliked = !this.isliked
          } else {
            this.recipe.likeNum++
            // this.likenumber++
            this.isliked = !this.isliked
          }
        },
        gouserblog(){
          this.getUserInfo(this.recipe.recipe_user)
          // console.log('!!!!!!!!!!!!!!!!')
          // console.log(this.recipe.recipe_user)
          if (this.authUser.user_id == this.recipe.recipe_user) {
            this.$router.push({ name: 'MyBlogListView'})
          } else {
            this.$router.push({ name: 'UserBlogListView', params: { user_id: this.recipe.recipe_user } })
          }
        },
        ...mapActions('myblog',['getUserInfo']),
        ...mapMutations('myblog', ['SET_USERINFO'])
    },
    created() {
      this.isliked = this.isLike
      // this.likenumber = this.recipe.likeNum
    }
}
</script>

<style>
    .headline > h5{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .maintext > span{
        height: 64px;
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical; 
    }
    .v-sheet.v-card {
    border-radius: 7px !important;
    border-color: rgb(235, 199, 133) !important;
    }
    .v-image {
      border-radius: 3px !important;
    }
</style>