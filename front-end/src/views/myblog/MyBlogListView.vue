<template>
  <div>
      <SearchBar id="searchbar" />
      <MyPage id="mypage" />
      <MyRecipeList />
  </div>
</template>

<script>
import SearchBar from '../../components/myblog/SerachBar.vue'
import MyPage from '../../components/myblog/MyPage.vue'
import MyRecipeList from '../../components/myblog/MyRecipeList.vue'

export default {
    name: 'MyBlogListView',
    components: {
        SearchBar,
        MyPage,
        MyRecipeList,
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
</style>