<template>
  <v-app id="inspire">
    <div class="card">
      <header>
        <!-- 프로필 이미지 -->
        <div class="avatar">
          <img :src="selecteduserinfo.image_url" alt=""/>
        </div>
      </header>

      <!-- 유저 닉네임 & 소개말 -->
      <h3>{{ selecteduserinfo.nickname }}</h3>
      <div class="desc">
        {{ selecteduserinfo.intro }}
      </div>

      <!-- 버튼 -->
      <div  class="block-btn btn-style2 blog-btn" 
            v-if="(this.authUser != null) && (this.authUser.user_id !== this.selecteduserinfo.user_id) && !this.fstate"
            @click="clickfollow">
        팔로우
      </div>
     
      <div  class="block-btn btn-style2 blog-btn" 
            v-if="(this.authUser != null) && (this.authUser.user_id !== this.selecteduserinfo.user_id) && this.fstate"
            @click="clickunfollow">
        언팔로우
      </div>
      <div  class="block-btn btn-style2 blog-btn" 
            v-if="(this.authUser != null) && this.authUser.user_id === this.selecteduserinfo.user_id"
            @click="GoRecipeCreate">
        글쓰기
      </div>

      <!-- 조회수, 팔로워, 팔로잉 -->
      <div class="stats">
        <div class="stat">
          <span class="label">조회수</span>
          <span class="value">{{ selecteduserinfo.hits }}</span>
        </div>
        <div class="stat">
          <span class="label">팔로워</span>
          <v-dialog v-model="follower" scrollable max-width="300px">
            <template v-slot:activator="{ on, attrs }">
              <span class="value" @click="getfollowers(selecteduserinfo.user_id)" v-bind="attrs" v-on="on">{{ userfollowers }}</span>
            </template>
            <v-card>
              <v-card-title>팔로워</v-card-title>
              <v-divider></v-divider>
              <v-card-text style="height: 300px;">
                <followerList></followerList>
              </v-card-text>
              <v-divider></v-divider>
            </v-card>
          </v-dialog>
        </div>
        <div class="stat">
          <span class="label">팔로잉</span>
          <v-dialog v-model="following" scrollable max-width="300px">
            <template v-slot:activator="{ on, attrs }">
              <span class="value" @click="getfollowings(selecteduserinfo.user_id)" v-bind="attrs" v-on="on">{{ userfollowings }}</span>
            </template>
            <v-card>
              <v-card-title>팔로잉</v-card-title>
              <v-divider></v-divider>
              <v-card-text style="height: 300px;">
                <followingList></followingList>
              </v-card-text>
              <v-divider></v-divider>
            </v-card>
          </v-dialog>
        </div>
        <div class="clear"></div>
      </div>

      <!-- SNS -->
      <footer>
        <i v-for="(sns, index) in sns_name_list" :key="index">
          <a v-if="sns_url_list[index]" :href="sns_url_list[index]" target='_blank'>
            <i :class="'fab fa-' + sns + '-square'" :id="sns" class="sns-abled" rel="mask-icon"></i>
          </a>
          <a v-else style="cursor: default">
            <i :class="'fab fa-' + sns + '-square'" :id="sns" class="sns-disabled" rel="mask-icon"></i>
          </a>
        </i>
      </footer>
    </div>
  </v-app>
</template>

<script>
import { mapState,mapActions } from 'vuex'
import followerList from '@/components/myblog/FollowerList.vue'
import followingList from '@/components/myblog/FollowingList.vue'

export default {
    name: 'ProfileCard',
    components:{
      followerList,
      followingList
    },
    data(){
      return{
        loading:true,
        fstate:null,
        userfollowers:null,
        userfollowings:null,
        follower: false,
        following: false,
        sns_name_list: [ 'youtube', 'instagram', 'twitter', 'facebook' ],
        sns_url_list: [ '', '', '', '' ],
      }
    },
    computed: {
      ...mapState('accounts', ['authUser']),
      ...mapState('storage',['myfollowings','followings','followers','myfollowers']),
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
        console.log(this.myfollowings)
      },
      clickunfollow(){
        this.unfollow(this.selecteduserinfo.user_id)
      },
      rerendering(){
        this.$router.go(0)
      },
      getfollowersagain(){
        console.log("팔로워 다시가져오기");
        this.getfollowers(this.selecteduserinfo.user_id);
        console.log(this.followers);
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
    updated(){
      console.log("profileCard updated!!")
      this.setfstate()
    },
    created() {
      if(this.authUser == null || this.selecteduserinfo.user_id !== this.authUser.user_id)
        this.hituser(this.selecteduserinfo.user_id)
      
      this.getfollowings(this.selecteduserinfo.user_id)
      this.getfollowers(this.selecteduserinfo.user_id)
      console.log("팔로잉팔로워들!")
      console.log(this.followers);
      console.log(this.followings)

      // SNS url 만들기
      // 0: 유튜브 1: 인스타그램 2: 트위터 3: 페이스북
      for (let item of this.selecteduserinfo.sns_list) {
        if (item.sns_name == 'youtube') this.sns_url_list[0] = item.sns_url;
        else if (item.sns_name == 'instagram') this.sns_url_list[1] = item.sns_url;
        else if (item.sns_name == 'twitter') this.sns_url_list[2] = item.sns_url;
        else if (item.sns_name == 'facebook') this.sns_url_list[3] = item.sns_url;
      }
    },
}
</script>

<style>
/*** VARS ***/
/*** GENERAL STYLES ***/
* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.v-application--wrap {
  min-height: 0px !important;
}

.card {
	width: 100%;
  max-width: 320px;
  min-width: 258px;
	background: #fff;
	box-shadow: 0 10px 7px -5px rgba(0, 0, 0, .4);
  margin-bottom: 2em;
}

 .card header {
	position: relative;
	width: 100%;
	height: 60px;
	background-color: #53AAA1;
}

.card header::before, .card header::after {
	content: '';
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: inherit;
}

.card header::before {
  background-color: #53AAA1;
	-webkit-transform: skewY(-8deg);
	-moz-transform: skewY(-8deg);
	-ms-transform: skewY(-8deg);
	-o-transform: skewY(-8deg);
	transform: skewY(-8deg);
	-webkit-transform-origin: 100% 100%;
	-moz-transform-origin: 100% 100%;
	-ms-transform-origin: 100% 100%;
	-o-transform-origin: 100% 100%;
	transform-origin: 100% 100%;
}

.card header::after {
  background-color: #53AAA1;
	-webkit-transform: skewY(8deg);
	-moz-transform: skewY(8deg);
	-ms-transform: skewY(8deg);
	-o-transform: skewY(8deg);
	transform: skewY(8deg);
	-webkit-transform-origin: 0 100%;
	-moz-transform-origin: 0 100%;
	-ms-transform-origin: 0 100%;
	-o-transform-origin: 0 100%;
	transform-origin: 0 100%;
}

.card header .avatar {
  position: absolute;
	left: 50%;
	top: 30px;
	margin-left: -50px;
	z-index: 5;
	width: 100px;
	height: 100px;
	border-radius: 50%;
	overflow: hidden;
	background: #ccc;
	border: 3px solid #fff;
}

.card header .avatar img {
	position: absolute;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-moz-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	-o-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
	width: 100px;
	height: auto;
}

.card h3 {
	position: relative;
	margin: 80px 0 30px;
  font-weight: 650;
	text-align: center;
}

.card h3::after {
	content: '';
	position: absolute;
	bottom: -15px;
	left: 50%;
	margin-left: -15px;
	width: 30px;
	height: 1px;
	background: #000;
}

.card .desc {
	padding: 0 1rem 2rem;
	text-align: center;
	line-height: 1.5;
	color: #777;
}

.blog-btn {
  margin: 0 3em 2.5em 3em;
}

.card .stats {
	width: 100%;
	max-width: 100%;
	margin: 0 auto 2rem;
}

.card .stats div {
	display: block;
	width: 33.333333%;
	float: left;
	text-align: center;
	color: #53AAA1;
}

.stat {
  box-sizing: border-box;
  width: calc(100% / 3);
  float: left;
  text-align: center;
  border-left: 1px solid lightgray;
}

.stat .label{
  display: block;
  text-transform: uppercase;
  font-weight: 400;
  font-size: 12px;
  letter-spacing: 1px;
  color: #95989A;
}

.stat .value{
  display: block;
  font-weight: 700;
  font-size:20px;
  margin-top: 5px;
}

.card footer {
	position: relative;
	padding: 1.2rem;
  height: 70px;
	background-color: #eee;
	text-align: center;
}

.card footer a {
	padding: 0 0.85rem;
  margin-top: 1em;
  height: 100%;
  font-size: 1.5em;
	color: black;
	-webkit-transition: color 0.4s;
	-moz-transition: color 0.4s;
	-ms-transition: color 0.4s;
	-o-transition: color 0.4s;
	transition: color 0.4s;
}

.card footer i {
  z-index: 5;
}

.card footer #facebook {
	color: #395794;
}

.card footer #youtube {
	color: #c4302b;
}

.card footer #instagram {
	color: #ED5078;
}

.card footer #twitter {
	color: #00acee;
}

.card footer .sns-abled:hover {
  background-color: #fff;
}

.card footer .sns-disabled {
  opacity: 0.25 !important;
  pointer-events: none;
}

.card footer::before {
	content: '';
	position: absolute;
	top: -27px;
	left: 50%;
	margin-left: -15px;
	border: 15px solid transparent;
	border-bottom-color: #eee;
}

/*** RESPONSIVE ***/
@media only screen and (max-width: 810px) {
	.card {
		float: none;
		margin-left: auto;
		margin-right: auto;
	}
}
</style>