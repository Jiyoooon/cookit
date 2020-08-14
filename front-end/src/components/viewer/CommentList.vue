<template>
  <div>
      <ul >
          <commentListItem v-for="comment in comments" :key="comment.comment_id" :comment="comment"/>
      </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import CommentListItem from './CommentListItem.vue'

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
        CommentListItem,
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
    },
    created() {
        console.log('!!!!')
        console.log(this.comments)
    }
}
</script>

<style>

</style>