<template>
  <div>
    <router-view :key="$route.fullPath"/>
      <v-container fluid grid-list-md >
        <v-layout row wrap >
          <v-flex sm12 md6 lg4 xl3 v-for="recipe in paginated_items[currentPage-1]" :key="recipe.recipe_id" style="margin:auto%" >
            <my-recipe-list-item :recipe="recipe"></my-recipe-list-item>
          </v-flex>
        </v-layout>
      </v-container>
    <b-row>
      <b-pagination id="pagination" :total-rows="totalRows()" :per-page="perPage" v-model="currentPage" class="my-0" />
    </b-row>
    <div :key="flag"></div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import myRecipeListItem from '@/components/myblog/MyRecipeListItem.vue'
import $ from 'jquery'
$(window).on('beforeunload', function() {
  // sessionStorage.removeItem('category');
  // sessionStorage.removeItem('searching');
  // sessionStorage.removeItem('selecting');
  // sessionStorage.removeItem('ordering');
  sessionStorage.clear();
})
export default {
    name: 'MyRecipeList',
    props:{

    },
    data() {
        return {
          // flag: false,
          currentPage: 1,
          perPage: 6,
          paginated_items: [],
          currentPageIndex:0,
          nbPages:0,
        }
    },
    components: {
        myRecipeListItem
    },
    computed: {
        ...mapState('myblog', ['myrecipes', 'selecteduserinfo', 'flag']),
    },
    methods: {
       ...mapActions('myblog', ['selectedRecipe', 'fetchMyRecipes']),
       ...mapActions('storage.js', ['getfollowings', 'getfollowers']),
       ...mapActions('recipes', ['fetchRecipe']),
       ...mapMutations('myblog',['SET_FLAG']),
      changePaginateditems(index, value) {
        this.paginated_items[index] = value
      },
      increasenbpages() {
        this.nbPages++
      },
      fortest(){
        if(this.flag ===false){
          this.$router.go(0)
          this.flag = true
        }
      },
      currentPageItems(){
          let lengthAll =this.myrecipes.length;
          for (let i = 0; i < lengthAll; i = i + this.perPage) {
            this.changePaginateditems(this.nbPages, this.myrecipes.slice(i,i+this.perPage))
            this.increasenbpages()
          }
          // console.log(this.paginated_items[this.currentPage-1])
          // return this.paginated_items[this.currentPage-1];
        },
        totalRows() {
            return this.myrecipes.length
        }
    },
    watch: {
      myrecipes: {
        deep: true,
        handler() {
          this.paginated_items = []
          let lengthAll =this.myrecipes.length;
          for (let i = 0; i < lengthAll; i = i + this.perPage) {
            this.paginated_items.push(this.myrecipes.slice(i,i+this.perPage))
            this.nbPages++
          }
          // console.log("myrecipes 목록 새로 가져옴 => 페이징 처리함")
          // console.log(this.myrecipes)
          // this.$router.go(this.$router.currentRoute)
          // this.$router.go(0)
          // location.reload()
          // console.log(this.myrecipes[0].recipe_user_name)
        }
      },
      // flag: {
      //   deep:true,
      //   handler() {
      //     console.log(this.flag)
      //     console.log(this.myrecipes[0].recipe_user_name)
      //   }
      // },
      paginated_items: {
        deep:true,
        handler() {
          // this.currentPageItems();
          // this.totalRows();
        }
      },
    },
}
</script>

<style>

#pagination {
  margin-left: auto;
  margin-right: auto;
}

.page-item.active .page-link {
  z-index: 3;
  color: #fff;
  background-color: grey !important;
  border-color: lightgrey !important;
}
</style>