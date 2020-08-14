<template>
  <div>
    <v-list-item-avatar>
    <v-img :src="follow.image_url"></v-img>
    </v-list-item-avatar>
    <v-list-item-content>
    <v-list-item-title v-html="follow.nickname"></v-list-item-title>
    </v-list-item-content>
  </div>
</template>

<script>
export default {
    name: 'FollowListItem',
    props: {
        follow: Object,
    }
}
</script>

<style>

</style>