<template>
  <div>
      <li>{{ follow.nickname }} | ??????</li>
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