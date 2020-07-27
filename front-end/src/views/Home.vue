<template>
  <div>
      <h1>Welcome to Home</h1>
      {{ isLoggedIn }}
      {{ authUser }}
      <!-- <div class="overflow-auto">
    <div>
      <h6>Goto first button number</h6>
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        first-number
        last-number
      ></b-pagination>
    </div>
    </div> -->
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'Home',
    computed: {
        ...mapGetters('accounts', ['isLoggedIn']),
        ...mapState('accounts', ['authUser'])
    }
}
</script>

<style>

</style>