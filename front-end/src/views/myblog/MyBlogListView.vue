<template>
  <div id="blog-view">
    <b-container fluid>
      <b-row>
        <b-col pr-6 lg="3" md="4" sm="6">
          <profile-card id="profild-card" />
        </b-col>
        <b-col lg="9" md="8" sm="6">
          <div class="select-list">
            <div v-for="(item, index) in sort" @click="ordering(index + 1)" 
            :class="(index+1==order)?'select-item selected':'select-item default'" :key="String(item)">
            {{ item }}</div>
          </div>
          <hr id="divider">
          <div>
          <blog-search-bar id="blog-searchbar" :is_changed="currentshow"/>
          </div>
          <my-recipe-list v-if="currentshow==1" />
          <like-recipe-list v-if="currentshow==2"/>
        </b-col>
      </b-row>
    </b-container>
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
            // recipelen:null,
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
    // updated() {
    //     this.recipelen = this.selectedRecipe.length
    // },
    created() {
        this.SET_USERINFO(this.authUser)
        this.getUserInfo(this.authUser.user_id)
        this.fetchMyRecipes(this.selecteduserinfo)
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

#blog-view {
  width: 95%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}

@media (max-width: 768px) {
  #blog-view {
    width: 100%
  }
}

.select-list {
  display: block;
  text-align: right;
  margin-bottom: -0.5em;
  margin-top: 1em;
}

.select-item {
  display: inline;
  padding-right: 18px;
  font-size: 0.9em;
  cursor: pointer;
}

.select-item.selected {
  font-weight: 700;
  color: #53AAA1;
}

#blog-searchbar {
  margin: 1em 10%;
}

@media (max-width: 960px) {
  #blog-searchbar {
    margin: 1em 0;
  }
}

</style>