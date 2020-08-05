<template>
  <b-contanier>
      <b-row>
        <SearchBar id="searchbar" />
      </b-row>
      <b-row>
        <b-col lg="3" md="3" sm="12">
            <MyPage id="mypage" />
        </b-col>
        <b-col lg="9" sm="12">
            <MyRecipeList />
        </b-col>
      </b-row>
  </b-contanier>
</template>

<script>
import SearchBar from '../../components/myblog/SerachBar.vue'
import MyPage from '../../components/myblog/MyPage.vue'
import MyRecipeList from '../../components/myblog/MyRecipeList.vue'
import { mapActions, mapState } from 'vuex'

export default {
    name: 'MyBlogListView',
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
        ...mapState('myblog', ['myrecipes'])
    },
    methods: {
        ...mapActions('myblog', ['fetchMyRecipes']),
    },
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
</style>