<template>
<div id="list">
  <b-contanier>
      <b-row>
        <SearchBar id="searchbar" />
      </b-row>
      <b-row>
        <b-col lg="3">
            <MyPage id="mypage" />
        </b-col>
        <b-col lg="9">
            <MyRecipeList />
        </b-col>
      </b-row>
  </b-contanier>
</div>
</template>

<script>
import SearchBar from '../../components/myblog/SerachBar.vue'
import MyPage from '../../components/myblog/MyPage.vue'
import MyRecipeList from '../../components/myblog/MyRecipeList.vue'
import { mapActions, mapState, mapMutations } from 'vuex'
//import recipeVue from '../../components/recipeview/recipe.vue'

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
    },
    computed: {
        ...mapState('myblog', ['myrecipes','selectedRecipe', 'selecteduserinfo']),
        ...mapState('accounts', ['authUser'])
    },
    methods: {
        ...mapActions('myblog', ['fetchMyRecipes', 'getUserInfo']),
        ...mapMutations('myblog', ['SET_USERINFO']),
        ...mapMutations('lookaround',['setRecipequeryUserId']),
    },
    updated() {
        this.recipelen = this.selectedRecipe.length
    },
    created() {
        this.SET_USERINFO(this.authUser)
        this.getUserInfo(this.selecteduserinfo.user_id)
        this.fetchMyRecipes()
    }
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