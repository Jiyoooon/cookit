<template>
  <div>
    <b-container fluid>
      <b-form>
      <b-row v-for="(item, index) in ingredients" :key="index" align-v="center">
        <b-col cols="5">
          <b-form-input type="text" list="ingrlist" v-model="item.name" required></b-form-input>
          <b-form-datalist id="ingrlist" :options="ingrQuery"></b-form-datalist>
        </b-col>
        <b-col cols="5">
          <b-form-input type="text" v-model="item.quantity" required></b-form-input>
        </b-col>
        <b-col cols="2">
          <b-icon icon="backspace-fill" variant="warning" style="cursor: pointer" @click="deleteIngredient({index, essential})"></b-icon>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col cols="10">
          <div @click="addIngredient(essential)" style="width: 6em; display: block; margin: 0px auto; align-text: center; cursor: pointer">
            <b-icon class="mr-1" icon="plus-square-fill" variant="warning"></b-icon>추가
          </div>
        </b-col>
        <b-col cols="2"></b-col>
      </b-row>
      </b-form>
    </b-container>
  </div>
</template>

<script>
import { mapMutations, mapActions, mapState } from 'vuex'
export default {
  name: 'Ingredients',
  props: {
    ingredients: Array,
    essential: Number
  },
  computed: {
    ...mapState('editor', ['ingrQuery']),
  },
  methods: {
    ...mapMutations('editor', ['addIngredient', 'deleteIngredient']),
    ...mapActions('editor', ['loadIngredients'])
  },
  created() {
    this.loadIngredients();
  }
}
</script>

<style>
</style>