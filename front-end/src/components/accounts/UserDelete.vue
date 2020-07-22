<template>
  <div>
      <v-card
    class="mx-auto"
    max-width="500"
    shaped="true"
  >
    <v-card-text>
      <p class=" text--primary font-weight-regular">
        탈퇴되었습니다.
      </p>
    </v-card-text>
    <v-card-actions>
      <v-btn
        text
        color="deep-purple accent-4"
        @click="RedirectAfterUserUpdate"
      >
        확인
      </v-btn>
    </v-card-actions>
  </v-card>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'UserDelete',
    methods: {
        ...mapActions('accounts', ['RedirectAfterUserDelete'])
    },
}
</script>

<style>

</style>