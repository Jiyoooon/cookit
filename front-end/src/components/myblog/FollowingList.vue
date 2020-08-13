<template>
    <div>
        <ul>
            <FollowListItem v-for="follower in followings" :key="follower.user_id" :follow="follower" />
        </ul>
    </div>
</template>

<script>
import FollowListItem from './FollowListItem.vue'
import { mapState } from 'vuex'

export default {
    name: 'FollowingList',
    components: {
        FollowListItem,
    },
    computed: {
        ...mapState('accounts', ['followings'])
    }
}
</script>

// <style>

</style>