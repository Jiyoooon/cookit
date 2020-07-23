<template>
  <div>
<!-- <v-card
    class="mx-auto"
    max-width="500"
    shaped="true"
  >
    <v-card-text>
      <p class=" text--primary font-weight-regular">
        수정되었습니다.
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
  </v-card> -->

  <b-card bg-variant="dark" text-variant="white" title="User Info Updated!">
  <b-card-text>
    수정되었습니다.
  </b-card-text>
  <b-button @click="RedirectAfterUserUpdate" variant="info">확인</b-button>
  </b-card>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {

    name: 'UserUpdate',
    methods: {
      ...mapActions('accounts', ['RedirectAfterUserUpdate'])
    }
}
</script>

<style>

</style>