<template>
    <div>
        <v-list-item-group color="primary">
            <v-list-item
                v-for="(f) in followers"
                :key="f.user_id"
                :inactive="inactive" @click="goFollowerBlog(f)">
                <FollowListItem :follow="f" />
            </v-list-item>
        </v-list-item-group>
    </div>
</template>

<script>
import FollowListItem from './FollowListItem.vue'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name: 'FollowerList',
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
        ...mapState('storage', ['followers']),
        ...mapState('myblog',['selecteduserinfo'])
    },
    methods: {
        goFollowerBlog(f){
            this.getUserInfo2(f.user_id)
            // this.SET_USERINFO({//들어가는 블로그의 유저정보
            //     user_id: this.$route.params.user_id,
            // })
        },
        ...mapActions('myblog',['getUserInfo2']),
        // ...mapActions('storage',['getfollowers']),
        ... mapMutations('myblog', ['SET_USERINFO'])
    },
    created() {
        // console.log("followerlist created")
        // console.log(this.followers)
    },
    updated(){
        // console.log("followerlist updated");
        // console.log(this.followers)
        
    }
}
</script>

// <style>

</style>