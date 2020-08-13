<template>
<div id="list">
    <!-- <b-button @click="showMyRecipes">내포스팅</b-button>
    <b-button @click="showLikeRecipes">좋아요글</b-button> -->
    <b-tabs content-class="mt-3" align="center">
        <b-tab @click="showMyRecipes" title="Post" active></b-tab>
        <b-tab @click="showLikeRecipes" title="Likes"></b-tab>
        <b-tab @click="showFollowers" title="Followers"></b-tab>
        <b-tab @click="showFollowings" title="Followings"></b-tab>
    </b-tabs>
    <!-- <b-button @click="showMyRecipes">Post</b-button>
    <b-button @click="showLikeRecipes">Likes</b-button>
    <b-button @click="showFollowers">Followers</b-button>
    <b-button @click="showFollowings">Followings</b-button> -->
<b-tabs content-class="mt-3" align="center">
  <b-contanier>
      <b-row v-if="currentshow < 3">
        <SearchBar id="searchbar" />
      </b-row>
      <b-row>
        <b-col lg="3">
            <MyPage id="mypage" />
        </b-col>
        <b-col lg="9">
            <MyRecipeList v-if="currentshow==1" />
            <LikeRecipeList v-if="currentshow==2"/>
            <FollowerList v-if="currentshow==3" />
            <FollowingList v-if="currentshow==4" />
        </b-col>
        <!-- <b-col v-if="currentshow==2" lg="9">
            <LikeRecipeList />
        </b-col> -->
      </b-row>
  </b-contanier>
</b-tabs>
</div>
</template>

<script>
import SearchBar from '../../components/myblog/SerachBar.vue'
import MyPage from '../../components/myblog/MyPage.vue'
import MyRecipeList from '../../components/myblog/MyRecipeList.vue'
import LikeRecipeList from '../../components/myblog/LikeRecipeList.vue'
import FollowerList from '../../components/myblog/FollowerList.vue'
import FollowingList from '../../components/myblog/FollowingList.vue'
import { mapActions, mapState, mapMutations } from 'vuex'
// import recipeVue from '../../components/recipeview/recipe.vue'

export default {
    name: 'MyBlogListView',
    data() {
        return {
            recipelen:null,
        }
    },
    components: {
        SearchBar,
        MyPage,
        MyRecipeList,
        LikeRecipeList,
        FollowerList,
        FollowingList,
    },
    computed: {
        ...mapState('myblog', ['myrecipes','selectedRecipe', 'selecteduserinfo', 'likerecipes', 'currentshow']),
        ...mapState('accounts', ['authUser'])
    },
    methods: {
        ...mapActions('myblog', ['fetchMyRecipes', 'getUserInfo', 'fetchLikeRecipes']),
        ...mapActions('accounts', ['fetchFollowers', 'fetchFollowings']),
        ...mapMutations('myblog', ['SET_USERINFO', 'SET_RECIPES', 'SET_LIKERECIPES', 'SET_CURRENTSHOW']),
        ...mapMutations('lookaround',['setRecipequeryUserId']),
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
        console.log('!!!!')
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