<template>
  <b-container fluid>
    <b-row align-v="center">
      <!-- 유저 아이콘, 별명, 작성일 -->
      <b-col cols="8" class="text-left ml-3 mr-n3">
        <b-img :src="comment.comment_user_profileImage" rounded="circle" thumbnail id="user_profile" @click="gouserblog" style="cursor:pointer"/>
        <div style="display: inline-block; position: absolute; padding: 2px 10px 0px 10px; margin-bottom: 0px; width: 100%;">
          <strong>{{ comment.comment_user_name }}</strong>
          <span style="font-size: 0.8em; color: lightgray; margin-left: 10px;">{{ comment.create_date }}</span>
          <div v-if="!(selectedcomment.id == comment.comment_id)" style="margin-top: 8px;">{{ comment.description }}</div>
          <!-- <commentUpdate v-if="selectedcomment.id == comment.comment_id" :comment="comment"/> -->
          <div v-if="(selectedcomment.id == comment.comment_id)">
            <b-form-input v-model="commentData.description" @keydown.enter="updateComment(commentData)"></b-form-input>
          </div>
        </div>
      </b-col>
        
      <!-- 수정 & 삭제 버튼 -->
      <b-col v-if="(authUser) && (authUser.user_id == comment.comment_user_id) && !(selectedcomment.id == comment.comment_id)" cols="4" class="text-right mr-3 ml-n3">
        <div v-if="!(selectedcomment.id == comment.comment_id)" class="text-btn" @click="SET_SELECTEDCOMMENT(comment.comment_id)"> 수정 </div>
        <div v-if="!(selectedcomment.id == comment.comment_id)" class="text-btn" @click="deleteComment(comment.comment_id)"> 삭제 </div>
      </b-col>

      <b-col v-if="(selectedcomment.id == comment.comment_id)" cols="4" class="text-right mr-3 ml-n3">
        <div class="text-btn" @click="updateComment(commentData)"> 완료 </div>
        <div class="text-btn" @click="SET_SELECTEDCOMMENT(null)"> 취소 </div>
      </b-col>
    </b-row>
    <b-row>
      
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'CommentListItem',
    props: {
        comment: Object,
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
    computed: {
        ...mapState('accounts', ['authUser']),
        ...mapState('recipes', ['selectedcomment'])
    },
    methods: {
        gouserblog(){
          // this.getUserInfo(this.comment.comment_user_id)
          if (this.authUser != null && this.authUser.user_id == this.comment.comment_user_id)
            this.$router.push({ name: 'MyBlogListView'})
          else
            this.$router.push({ name: 'UserBlogListView', params: { user_id: this.comment.comment_user_id } })
        },
        ...mapActions('myblog',['getUserInfo']),
        ...mapActions('recipes', ['fetchComments', 'deleteComment', 'updateComment']),
        ...mapMutations('recipes', ['SET_SELECTEDCOMMENT']),
    },
    created() {
      this.commentData.description = this.comment.description
      this.commentData.recipe_id = this.comment.recipe_id
      this.commentData.comment_id = this.comment.comment_id
    }
}
</script>

<style>
#user_profile {
  display: inline;
  height: 50px;
  width: 50px;
  margin: 5px 0px;
}

.text-btn {
  display: inline-block;
  cursor: pointer;
  color: gray;
  font-size: 0.8em;
  margin-left: 0.5em;
}

</style>