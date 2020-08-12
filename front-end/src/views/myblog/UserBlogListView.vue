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
import { mapActions, mapState } from 'vuex'

export default {
    name: 'UserBlogListView',
    data() {
        return {
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
    },
    created() {
        this.getUserInfo(this.selecteduserinfo.user_id)
        this.fetchMyRecipes() 
    },

}
</script>

<style>

</style>