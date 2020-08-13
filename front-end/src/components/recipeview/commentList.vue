<template>
  <div>
      <ul >
          <commentListItem v-for="comment in comments" :key="comment.comment_id" :comment="comment"/>
      </ul>
  </div>
</template>

<script>
// import commentUpdate from './commentUpdate.vue'
import commentListItem from './commentListItem.vue'

import { mapState } from 'vuex'

export default {
    name: 'commentList',
    data() {
        return {
            // recipecomments: null,
            // TF: false,
        }
    },
    components: {
        // commentUpdate,
        commentListItem,
    },
    computed: {
        ...mapState('recipes', ['comments', 'selectedRecipe']),
        // ...mapState('accounts', ['authUser']),
    },
    methods: {
    },
    watch: {
        comments: {
            deep: true,
            handler() {
                // this.$router.push({ name: 'SelectedRecipe', params: this.selectedRecipe.recipe_id })
                // this.recipecomments = this.comments
                // console.log('!!!!!!!!!!!!')
            }
        }
    }
}
</script>

<style>

</style>