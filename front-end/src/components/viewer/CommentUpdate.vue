<template>
  <div>
      <b-form-input v-model="commentData.description"></b-form-input>
      <b-button @click="updateComment(commentData)">수정하기</b-button>
      <b-button @click="SET_SELECTEDCOMMENT(null)">취소</b-button>
  </div>
</template>

<script>
import { mapMutations, mapActions } from 'vuex'

export default {
    name: 'commentUpdate',
    props: {
        comment: Object
    },
    data() {
        return {
            commentData: {
                description: null,
                recipe_id: null,
                comment_id: null,
            }
        }
    },
    created() {
        this.commentData.description = this.comment.description
        this.commentData.recipe_id = this.comment.recipe_id
        this.commentData.comment_id = this.comment.comment_id
    },
    methods: {
        ...mapMutations('recipes', ['SET_SELECTEDCOMMENT']),
        ...mapActions('recipes', ['updateComment'])
    }

}
</script>

<style>

</style>