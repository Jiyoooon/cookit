<template>
  <div>
      <ul >
          <comment-list-item v-for="comment in comments" :key="comment.comment_id" :comment="comment"/>
      </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import commentListItem from '@/components/viewer/CommentListItem.vue'

export default {
    name: 'CommentList',
    data() {
        return {
        }
    },
    components: {
        commentListItem,
    },
    computed: {
        ...mapState('recipes', ['comments', 'selectedRecipe']),
    },
    methods: {
    },
    watch: {
        comments: {
            deep: true,
        }
    },
    created() {
        console.log(this.comments);
    }
}
</script>

<style>

</style>