<template>
  <div>
    <div id="app">
  <v-app id="inspire">
    <div>
      <v-alert
        text
        color="info"
      >
        <h3 class="headline">Lorem Ipsum</h3>
        <div>Maecenas nec odio et ante tincidunt tempus. Sed mollis, eros et ultrices tempus, mauris ipsum aliquam libero, non adipiscing dolor urna a orci. Proin viverra, ligula sit amet ultrices semper, ligula arcu tristique sapien, a accumsan nisi mauris ac eros. Curabitur turpis.</div>
  
        <v-divider
          class="my-4 info"
          style="opacity: 0.22"
        ></v-divider>
  
        <v-row
          align="center"
          no-gutters
        >
          <v-col class="grow">Proin magna. Vivamus in erat ut urna cursus vestibulum. Etiam imperdiet imperdiet orci.</v-col>
          <v-spacer></v-spacer>
          <v-col class="shrink">
            <v-btn
              color="info"
              outlined
              @click="RedirectAfterUserUpdate"
            >
              확인
            </v-btn>
          </v-col>
        </v-row>
      </v-alert>
    </div>
  </v-app>
</div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {

    name: 'UserUpdateView',
    methods: {
      ...mapActions('accounts', ['RedirectAfterUserUpdate'])
    }
}
</script>

<style>

</style>