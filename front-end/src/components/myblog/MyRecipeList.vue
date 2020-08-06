<template>
<div>
    <b-row>   
        <MyRecipeListItem :recipe="recipe" 
        :key="index" v-for="(recipe, index) in currentPageItems" id="my-recipes" /> 
    </b-row>
    <b-row>
        <b-pagination id="pagination" :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0" />
    </b-row>
</div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MyRecipeListItem from './MyRecipeListItem.vue'

export default {
    name: 'MyRecipeList',
    data() {
        return {
          currentPage: 1,
          perPage: 6,
          paginated_items: {},
          currentPageIndex:0,
          nbPages:0,
          recipes: null,
        }
    },
    components: {
        MyRecipeListItem
    },
    computed: {
        ...mapState('myblog', ['myrecipes']),
        currentPageItems() {
          let lengthAll =this.myrecipes.length;
          for (let i = 0; i < lengthAll; i = i + this.perPage) {
            this.changePaginateditems(this.nbPages, this.myrecipes.slice(i,i+this.perPage))
            this.increasenbpages()
          }
          return this.paginated_items[this.currentPage-1];
        },
        totalRows() {
            return this.myrecipes.length
        }
    },
    methods: {
       ...mapActions('myblog', ['selectedRecipe', 'fetchMyRecipes']),
       ...mapActions('recipes', ['fetchRecipe']),
      changePaginateditems(index, value) {
        this.paginated_items[index] = value
      },
      increasenbpages() {
        this.nbPages++
      },
    },
    created() {
        this.fetchMyRecipes()
    }
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