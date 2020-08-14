<template>
    <div>
        <v-list-item-group color="primary">
            <v-list-item
                v-for="(f) in followings"
                :key="f.user_id"
                :inactive="inactive" @click="goFollowerBlog(f)">
                <FollowListItem :follow="f" />
            </v-list-item>
        </v-list-item-group>
    </div>
</template>

<script>
import FollowListItem from './FollowListItem.vue'
import { mapState, mapActions } from 'vuex'

export default {
    name: 'FollowingList',
    data(){
        return{
            inactive: false
        }
    },
    components: {
        FollowListItem,
    },
    computed: {
        ...mapState('accounts', ['authUser']),
        ...mapState('storage', ['followings']),
        ...mapState('myblog',['selecteduserinfo'])
    },
    methods: {
        goFollowerBlog(f){
            this.getUserInfo2(f.user_id)
            // this.SET_USERINFO({//들어가는 블로그의 유저정보
            //     user_id: this.$route.params.user_id,
            // })
           
            // console.log(this.$route.path);
            // this.$router.push({name: 'UserBlogListView', params:{user_id: f.user_id}})
        },
        ...mapActions('myblog',['getUserInfo2']),
        ...mapActions('myblog', ['fetchMyRecipes'])
        // ...mapActions('storage',['getfollowings']),
        // ... mapMutations('myblog', ['SET_USERINFO'])
    },
}
</script>

// <style>

</style>