<template>
  <div>
      {{ selectedrecipe }}
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'RecipeDetail',
    computed: {
        ...mapState('myblog', ['selectedrecipe'])
    }

}
</script>

<style>

</style>