<template>
    <div id="myblog-view">
        <b-container fluid>
            <b-row>
                <b-col lg="3" md="6">
                    <profile-card id="profild-card" />
                </b-col>
                <b-col lg="9" md="6">
                    <div class="sort-list">
          <div v-for="(item, index) in sort" @click="ordering(index + 1)" 
          :class="(index+1==order)?'sort-item selected':'sort-item default'" :key="String(item)">
          {{ item }}</div>
        </div>
        <hr id="divider">
        <div>
        <blog-search-bar id="blog-searchbar" />
      </div>
                    <my-recipe-list v-if="currentshow==1" />
                    <like-recipe-list v-if="currentshow==2"/>
                    <search-recipe-list v-if="currentshow==3" />
                </b-col>
            </b-row>
        </b-container>
      <!-- <div class="sort-list">
          <div v-for="(item, index) in sort" @click="ordering(index + 1)" 
          :class="(index+1==order)?'sort-item selected':'sort-item default'" :key="String(item)">
          {{ item }}</div>
        </div>
        <hr id="divider">
      <div>
        <search-bar id="searchbar" />
      </div>
  <b-contanier>
      <b-row>
        <b-col lg="3">
            <profile-card id="profild-card" />
        </b-col>
        <b-col lg="9">
            <my-recipe-list v-if="currentshow==1" />
            <like-recipe-list v-if="currentshow==2"/>
            <search-recipe-list v-if="currentshow==3" />
        </b-col>
      </b-row>
  </b-contanier> -->
</div>
</template>

<script>
import blogSearchBar from '@/components/myblog/BlogSearchBar.vue'
import profileCard from '@/components/myblog/ProfileCard.vue'
// import myPage from '@/components/myblog/MyPage.vue'
import myRecipeList from '@/components/myblog/MyRecipeList.vue'
import likeRecipeList from '@/components/myblog/LikeRecipeList.vue'
import { mapActions, mapState, mapMutations } from 'vuex'
// import recipeVue from '@/components/recipeview/recipe.vue'

export default {
    name: 'MyBlogListView',
    data() {
        return {
            recipelen:null,
            sort: ['내가 쓴 글', '좋아요'],
            order: 1
        }
    },
    components: {
        blogSearchBar,
        profileCard,
        // myPage,
        myRecipeList,
        likeRecipeList,
    },
    computed: {
        ...mapState('myblog', ['myrecipes','selectedRecipe', 'selecteduserinfo', 'likerecipes', 'currentshow', 'searchrecipes']),
        ...mapState('accounts', ['authUser'])
    },
    methods: {
        ...mapActions('myblog', ['fetchMyRecipes', 'getUserInfo', 'fetchLikeRecipes']),
        ...mapActions('accounts', ['fetchFollowers', 'fetchFollowings']),
        ...mapMutations('myblog', ['SET_USERINFO', 'SET_RECIPES', 'SET_LIKERECIPES', 'SET_CURRENTSHOW']),
        ...mapMutations('lookaround',['setRecipequeryUserId']),
        ordering(value) {
            this.order = value
            if (value == 1) this.showMyRecipes()
            else this.showLikeRecipes()
        },
        showMyRecipes() {
            this.SET_CURRENTSHOW(1)
            this.fetchMyRecipes(this.authUser)
            // this.SET_LIKERECIPES(null)
        },
        showLikeRecipes() {
            this.SET_CURRENTSHOW(2)
            this.fetchLikeRecipes()
            // this.$router.push({ name: 'LikeRecipesListView' })
            // this.SET_RECIPES(null)
        },
        showFollowers() {
            this.SET_CURRENTSHOW(3)
            this.fetchFollowers()
        },
        showFollowings() {
            this.SET_CURRENTSHOW(4)
            this.fetchFollowings()
        }
    },
    updated() {
        this.recipelen = this.selectedRecipe.length
    },
    created() {
        this.SET_USERINFO(this.authUser)
        this.getUserInfo(this.authUser.user_id)
        // console.log('!!!!내블로그!!!!')
        // console.log(this.selecteduserinfo)
        // this.fetchMyRecipes()
    },
    // watch: {
    //     myrecipes: {
    //         deep: true,
    //         handler() {
                
    //         }
    //     }
    // },
}
</script>

<style>

#myblog-view {
  width: 90%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

@media (max-width: 496px) {
  #myblog-view {
    width: 100%
  }
}

#searchbar {
        width: 50vw;
        margin-left: auto;
        margin-right: auto;
        margin-top: 10px;
    }
    #profile-card {
        float:left;
        width: 20vw;
        margin-left: 20px;
        margin-right: 20px;
    }


</style>