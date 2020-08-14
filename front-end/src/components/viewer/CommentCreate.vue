<template>
  <div>
      <hr>
      <b-form-input v-model="commentData.description"></b-form-input>
      <div class="inline-block-btn btn-style1" @click="createComment(commentData)">작성</div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
    name: 'CommentCreate',
    data() {
        return {
            commentData: {
                description: null,
                recipe_id: null,
            }
        }
    },
    computed: {
        ...mapState('recipes', ['selectedRecipe', 'comments']),
    },
    methods: {
        ...mapActions('recipes', ['createComment']),
    },
    created() {
        this.commentData.recipe_id = this.selectedRecipe.recipe_id
    },
    watch: {
        comments: {
            deep: true,
            handler() {
                this.commentData.description = ''
            }
        }
    }
}
</script>

<style>

</style>