<template>
<b-container fluid>
    <b-row><b-col></b-col></b-row>
    <b-row><b-col></b-col></b-row>
      <b-row align-v="center">
        <b-col class="text-center mr-3 ml-3">
          <h3 style="font-weight:700;">댓글</h3>
        </b-col>
      </b-row>
      <hr style="margin-top: 0.5em">
      <b-row>
          <b-col sm="10">
            <b-form-input v-model="commentData.description" @keydown.enter="createComment(commentData)" />
        </b-col>
          <b-col sm="2">
            <div class="block-btn btn-style1" @click="createComment(commentData)">작성</div>
        </b-col>
      </b-row>
    </b-container>
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