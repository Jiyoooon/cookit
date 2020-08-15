<template>
  <div id="blog-view">
    <b-container fluid>
      <b-row>
        <b-col pr-6 lg="3" md="4" sm="5">
          <profile-card id="profild-card" />
        </b-col>
        <b-col lg="9" md="8" sm="7">
          <div>
          <blog-search-bar id="blog-searchbar" />
          </div>
          <my-recipe-list/>
        </b-col>
      </b-row>
    </b-container>
</div>
</template>

<script>
import blogSearchBar from '@/components/myblog/BlogSearchBar.vue'
import profileCard from '@/components/myblog/ProfileCard.vue'
import myRecipeList from '@/components/myblog/MyRecipeList.vue'
import { mapActions, mapState, mapMutations } from 'vuex'

export default {
    name: 'UserBlogListView',
    data() {
        return {
        }
    },
    components: {
        blogSearchBar,
        profileCard,
        myRecipeList,
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
        // console.log(this.selecteduserinfo)
        this.SET_USERINFO({//들어가는 블로그의 유저정보
            user_id: this.$route.params.user_id,
        },)
        this.getUserInfo(this.$route.params.user_id)
        // console.log('?????????')
        // this.fetchMyRecipes(this.selecteduserinfo) 
    },

}
</script>

<style>

</style>