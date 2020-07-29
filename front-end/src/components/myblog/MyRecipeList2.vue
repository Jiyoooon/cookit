<template>
  <b-container>
      <b-row>   
        <MyRecipeListItem @click="selectedRecipe(recipe.id)" :recipe="recipe" 
        :key="index" v-for="(recipe, index) in currentPageItems" id="my-recipes" /> 
      </b-row>
      <b-row>
          <!-- <b-col lg="12"> -->
              <b-pagination id="pagination" :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0" />
          <!-- </b-col> -->
      </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MyRecipeListItem from './MyRecipeListItem.vue'

export default {
    name: 'MyRecipeList2',
    data() {
        return {
          currentPage: 1,
          perPage: 6,
          paginated_items: {},
          currentPageIndex:0,
          nbPages:0
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
       ...mapActions('myblog', ['fetchMyRecipes', 'selectedRecipe']),
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
</style>