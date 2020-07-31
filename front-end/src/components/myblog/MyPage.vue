<template>
  <div>
      <v-card
    :loading="loading"
    class="mx-auto my-12"
    max-width="374"
  >
    <v-img
      height="250"
      :src="authUser.image_url"
    ></v-img>

    <v-card-title>{{ authUser.nickname }}</v-card-title>

    <v-card-text>
      <v-row
        align="center"
        class="mx-0"
      >
        <v-rating
          :value="4.5"
          color="amber"
          dense
          half-increments
          readonly
          size="14"
        ></v-rating>

        <div class="grey--text ml-4">4.5 (413)</div>
      </v-row>

      <div class="my-4 subtitle-1">
        $ • Italian, Cafe
      </div>

      <div>{{ authUser.intro }}</div>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>

    <v-card-title>SNS</v-card-title>

    <v-card-text>
      <v-chip-group
        v-model="selection"
        active-class="deep-purple accent-4 white--text"
        column
      >
        <v-chip>페이스북</v-chip>

        <v-chip>인스타</v-chip>

        <v-chip>트위터</v-chip>

      </v-chip-group>
    </v-card-text>

    <v-card-actions>
      <v-btn
        color="deep-purple lighten-2"
        text
        @click="reserve"
      >
        Reserve
      </v-btn>
    </v-card-actions>
  </v-card>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'MyPage',
    computed: {
      ...mapState('accounts', ['authUser'])
    },
}
</script>

<style>

</style>