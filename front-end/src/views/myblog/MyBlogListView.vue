<template>
<div id="list">
      <div class="sort-list">
          <div v-for="(item, index) in sort" @click="ordering(index + 1)" 
          :class="(index+1==order)?'sort-item selected':'sort-item default'" :key="String(item)">
          {{ item }}</div>
        </div>
        <hr id="divider">
      <div>
        <SearchBar id="searchbar" />
      </div>
  <b-contanier>
      <b-row>
        <b-col lg="3">
            <MyPage id="mypage" />
        </b-col>
        <b-col lg="9">
            <MyRecipeList v-if="currentshow==1" />
            <LikeRecipeList v-if="currentshow==2"/>
            <SearchRecipeList v-if="currentshow==3" />
        </b-col>
        <!-- <b-col v-if="currentshow==2" lg="9">
            <LikeRecipeList />
        </b-col> -->
      </b-row>
  </b-contanier>
</div>
</template>

<script>
import SearchBar from '../../components/myblog/SerachBar.vue'
import MyPage from '../../components/myblog/MyPage.vue'
import MyRecipeList from '../../components/myblog/MyRecipeList.vue'
import LikeRecipeList from '../../components/myblog/LikeRecipeList.vue'
import { mapActions, mapState, mapMutations } from 'vuex'
// import recipeVue from '../../components/recipeview/recipe.vue'

export default {
    name: 'MyBlogListView',
    data() {
        return {
            recipelen:null,
            sort: ['내포스트', '좋아요글'],
            order: 1
        }
    },
    components: {
        SearchBar,
        MyPage,
        MyRecipeList,
        LikeRecipeList,
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
    #searchbar {
        width: 50vw;
        margin-left: auto;
        margin-right: auto;
        margin-top: 10px;
    }
    #mypage {
        float:left;
        width: 20vw;
        margin-left: 20px;
        margin-right: 20px;
    }

#list {
  width: 90%;
  display: block;
  margin: 0px auto;
  background-color: #fff;
  padding: 5em 1em 8em 1em;
}
</style>