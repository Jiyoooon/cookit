<template>
  <div>
    <b-container fluid="sm">
      <b-row v-for="(item, index) in ingredients" :key="index" align-v="center">
        <b-col sm="5">
          <b-form-input type="text" v-model="item.name" required></b-form-input>
        </b-col>
        <b-col sm="5">
          <b-form-input type="text" v-model="item.quantity" required></b-form-input>
        </b-col>
        <b-col>
          <b-icon icon="backspace-fill" variant="warning" style="cursor: pointer" @click="deleteIngredient({index, essential})"></b-icon>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col sm="11">
          <div @click="addIngredient(essential)" style="width: 6em; display: block; margin: 0px auto; align-text: center; cursor: pointer">
            <b-icon class="mr-1" icon="plus-square-fill" variant="warning"></b-icon>추가
          </div>
        </b-col>
        <b-col sm="1"></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
  name: 'Ingredients',
  props: {
    ingredients: Array,
    essential: Number
  },
  methods: {
    ...mapMutations('editor', ['addIngredient', 'deleteIngredient', 'SET_UPDATETF']),
  },
  watch: {
    ingredients: {
      deep: true,
      handler() {
        this.SET_UPDATETF(true)
        console.log('fjlsdfjlsdjlfjaklsd')
      }
    }
  }
}
</script>

<style>
</style>