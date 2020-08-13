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
        <div class="grey--text">조회수 : {{ this.selecteduserinfo.hits }}</div>
        <div class="grey--text"><span>팔로워 : {{this.userfollowers}}</span><span>팔로잉 : {{this.userfollowings}}</span></div>
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
        @click="clickfollow"
      >
        팔로우
      </v-btn>
      <v-btn
        color="deep-purple lighten-2"
        text
        v-if="(this.authUser.user_id !== this.selecteduserinfo.user_id) && this.fstate && (this.authUser != null)"
        @click="clickunfollow"
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
        <v-chip><a :href="authUser.sns_list[0].sns_url" target=_blank><i class="fab fa-facebook-square"></i></a></v-chip>

        <v-chip v-if="authUser.sns_list[1].sns_url"><a :href="authUser.sns_list[1].sns_url" target=_blank><i class="fab fa-instagram"></i></a></v-chip>

        <v-chip v-if="authUser.sns_list[2].sns_url"><a :href="authUser.sns_list[2].sns_url" target=_blank><i class="fab fa-twitter"></i></a></v-chip>

        <v-chip v-if="authUser.sns_list[3].sns_url"><a :href="authUser.sns_list[3].sns_url" target=_blank><i class="fab fa-youtube"></i></a></v-chip>

      </v-chip-group>
    </v-card-text>

  </v-card>
  </div>
  <!-- </b-col> -->
</template>

<script>
import { mapState,mapActions} from 'vuex'

export default {
    name: 'MyPage',
    data(){
      return{
        loading:true,
        fstate:null,
        userfollowers:null,
        userfollowings:null,
      }
    },
    computed: {
      
      ...mapState('accounts', ['authUser']),
      ...mapState('storage',['myfollowings','followings','followers']),
      ...mapState('myblog',['selecteduserinfo'])
    },
    methods: {
      setfstate(){
        if(this.myfollowings.findIndex(x => x.user_id === this.selecteduserinfo.user_id)<0)
          this.fstate = false
        else
          this.fstate = true
      },
      setfollowers(){
        this.userfollowers = this.followers.length
      },
      setfollowings(){
        this.userfollowings = this.followings.length
      },
      clickfollow(){
        this.follow(this.selecteduserinfo.user_id)
      },
      clickunfollow(){
        this.unfollow(this.selecteduserinfo.user_id)
      },
      rerendering(){
        this.$router.go(0)
      },
      ...mapActions('accounts',['GoRecipeCreate','hituser']),
      ...mapActions('storage',['follow','unfollow','getfollowings','getfollowers']),
    },
    watch: {
      myfollowings:{
        handler(){
          this.setfstate()
        }
      },
      followers:{
        handler(){
          this.setfollowers()
        }
      },
      followings:{
        handler(){
          this.setfollowings()
        }
      }
    },
    created() {
      if(this.selecteduserinfo.user_id !== this.authUser.user_id)
        this.hituser(this.selecteduserinfo.user_id)
    },
    updated() {
      this.setfstate()
      this.getfollowings(this.selecteduserinfo.user_id)
      this.getfollowers(this.selecteduserinfo.user_id)
    },
}
</script>

<style>

</style>