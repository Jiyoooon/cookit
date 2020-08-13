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
        ... mapMutations('myblog', ['SET_USERINFO'])
    },
    created() {
        console.log(this.selecteduserinfo)
        this.SET_USERINFO({//들어가는 블로그의 유저정보
            email: null,
            hits: null,
            image_name: null,
            image_url: null,
            nickname: null,
            password: null,
            start_page: null,
            user_id: this.$route.params.user_id,
        },)
        this.getUserInfo(this.$route.params.user_id)
        console.log('?????????')
        // this.fetchMyRecipes(this.selecteduserinfo) 
    },

}
</script>

<style>

</style>