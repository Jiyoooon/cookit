<template>
<!-- <b-col lg-3> -->
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
      <!-- <v-row
        align="center"
        class="mx-0"
      > -->
        <div class="grey--text">조회수 : 413</div>
      <!-- </v-row> -->

      <div class="my-4 subtitle-1">
        {{ authUser.intro }}
      </div>

    </v-card-text>

    <v-card-actions>
      <v-btn
        color="deep-purple lighten-2"
        text
        @click="reserve"
      >
        이웃추가
      </v-btn>
      <v-btn
        color="deep-purple lighten-2"
        text
        @click="reserve"
      >
        새 글쓰기
      </v-btn>
    </v-card-actions>
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

  </v-card>
  </div>
  <!-- </b-col> -->
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