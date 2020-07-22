<template>
  <div>
<v-card
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
  </v-card>
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
v-card-text {
  font-size: 1.2em;
}
</style>