<template>
<!-- <b-col lg-3> -->
  <div>
      <v-card
    :loading="loading"
    class="mx-auto my-12"
    max-width="374"
  >
    <v-img
      height="250"
      :src="selecteduserinfo.image_url"
    ></v-img>

    <v-card-title>{{ selecteduserinfo.nickname }}</v-card-title>

    <v-card-text>
      <!-- <v-row
        align="center"
        class="mx-0"
      > -->
        <div class="grey--text">조회수 : 413</div>
      <!-- </v-row> -->

      <div class="my-4 subtitle-1">
        {{ selecteduserinfo.intro }}
      </div>

    </v-card-text>

    <v-card-actions>
      <v-btn
        color="deep-purple lighten-2"
        text
        v-if="(this.authUser.user_id !== this.selecteduserinfo.user_id) && !this.fstate && (this.authUser != null)"
        @click="follow"
      >
        팔로우
      </v-btn>
      <v-btn
        color="deep-purple lighten-2"
        text
        v-if="(this.authUser.user_id !== this.selecteduserinfo.user_id) && this.fstate && (this.authUser != null)"
        @click="unfollow"
      >
        언팔로우
      </v-btn>
      <v-btn
        color="deep-purple lighten-2"
        text
        @click="GoRecipeCreate"
        v-if="this.authUser.user_id === this.selecteduserinfo.user_id"
      >
        새 글쓰기
      </v-btn>
    </v-card-actions>
    <v-divider class="mx-4"></v-divider>

    <v-card-title>SNS</v-card-title>

    <v-card-text>
      <v-chip-group
        active-class="deep-purple accent-4 white--text"
        column
      >
        <v-chip><i class="fab fa-facebook-square"></i></v-chip>

        <v-chip><i class="fab fa-instagram"></i></v-chip>

        <v-chip><i class="fab fa-twitter"></i></v-chip>

        <v-chip><i class="fab fa-youtube"></i></v-chip>

      </v-chip-group>
    </v-card-text>

  </v-card>
  </div>
  <!-- </b-col> -->
</template>

<script>
import { mapState,mapActions, mapMutations } from 'vuex'

export default {
    name: 'MyPage',
    data(){
      return{
        loading:true,
        fstate:null,
      }
    },
    computed: {
      
      ...mapState('accounts', ['authUser']),
      ...mapState('storage',['followings']),
      ...mapState('myblog',['selecteduserinfo'])
    },
    methods: {
      setfstate(){
        console.log("팔로우테스트")
        console.log(this.followings)
        console.log(this.followings.findIndex(x => x.user_id === this.selecteduserinfo.user_id))
        if(this.followings.findIndex(x => x.user_id === this.selecteduserinfo.user_id)>0)
          this.fstate = true
        else
          this.fstate = false
      },
      follow(){
        this.follow(this.selecteduserinfo.user_id)
        this.rerendering()
      },
      unfollow(){

      },
      rerendering(){
        this.$router.go(0)
      },
      ...mapActions('accounts',['GoRecipeCreate']),
      ...mapMutations('storage',['ADD_FOLLOWINGS',]),
      ...mapActions('storage',['follow'])
    },
    beforeMount() {
      this.setfstate()
    },
}
</script>

<style>

</style>