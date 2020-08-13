<template>
<div>
    <router-view :key="$route.fullPath"/>
    <b-row> 
        <MyRecipeListItem :recipe="recipe" 
        :key="recipe.recipe_id" v-for="recipe in paginated_items[currentPage-1]" id="my-recipes" /> 
    </b-row>
    <b-row>
        <b-pagination id="pagination" :total-rows="totalRows()" :per-page="perPage" v-model="currentPage" class="my-0" />
    </b-row>
    <div :key="flag"></div>
</div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import MyRecipeListItem from './MyRecipeListItem.vue'

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
        MyRecipeListItem
    },
    computed: {
        ...mapState('myblog', ['myrecipes', 'selecteduserinfo', 'flag']),
    },
    methods: {
       ...mapActions('myblog', ['selectedRecipe', 'fetchMyRecipes']),
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
      currentPageItems() {//문제의 원인은 얘다/
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
          this.currentPageItems();
          this.totalRows();
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
          this.currentPageItems();
          this.totalRows();
        }
      }
    },
    mounted() {
      // this.SET_FLAG(false)
      // console.log(this.flag)
    },
    updated() {
      // this.currentPageItems();
      // this.totalRows();
      // for(var i = 0; i < this.nbPages; i++) console.log(this.paginated_items[i]);
        // setTimeout(() => {
        //     this.fortest()
        // },1000);
    },
    created() {
      this.fetchMyRecipes(this.selecteduserinfo)
      // this.SET_FLAG(false)
      // console.log(this.flag)
    },
}
</script>

<style>
     #my-recipes:hover {
        cursor: pointer;
        color: orange;
    }
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