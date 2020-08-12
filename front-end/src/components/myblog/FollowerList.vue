<template>
    <div>
        <ul>
            <FollowListItem v-for="follower in followers" :key="follower.user_id" :follow="follower" />
        </ul>
    </div>
</template>

<script>
import FollowListItem from './FollowListItem.vue'
import { mapState } from 'vuex'

export default {
    name: 'FollowerList',
    components: {
        FollowListItem,
    },
    computed: {
        ...mapState('accounts', ['followers'])
    }
}
</script>

// <style>

</style>