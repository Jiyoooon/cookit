<template>
  <li>
      <span v-if="!(selectedcomment.id == comment.comment_id)">{{ comment.description }} | 작성자 : {{ comment.comment_user_name }}</span>
      <commentUpdate v-if="selectedcomment.id == comment.comment_id" :comment="comment"/>
      <span v-if="(authUser) && (authUser.user_id == comment.comment_user_id)">
          <b-button v-if="!(selectedcomment.id == comment.comment_id)" @click="deleteComment(comment.comment_id)">삭제</b-button>
          <b-button v-if="!(selectedcomment.id == comment.comment_id)" @click="SET_SELECTEDCOMMENT(comment.comment_id)">수정</b-button>
      </span>
  </li>
</template>

<script>
import commentUpdate from './CommentUpdate.vue'

import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'CommentListItem',
    props: {
        comment: Object,
    },
    components: {
        commentUpdate
    },
    computed: {
        ...mapState('accounts', ['authUser']),
        ...mapState('recipes', ['selectedcomment'])
    },
    methods: {
        ...mapActions('recipes', ['fetchComments', 'deleteComment']),
        ...mapMutations('recipes', ['SET_SELECTEDCOMMENT'])
    }
}
</script>

<style>

</style>