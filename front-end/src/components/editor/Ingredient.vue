<template>
  <div>
    <b-container fluid>
      <b-row v-for="(item, index) in ingredients" :key="index">
        <b-col cols="5">
          <b-form-input type="text" list="ingrlist" v-model="item.name" placeholder="재료명"
          @blur="autoComplete(index)" @keyup.enter="$event.target.blur()" aria-describedby="ingr-feedback" :state="item.valid"></b-form-input>
          <b-form-datalist id="ingrlist" :options="ingrQuery"></b-form-datalist>
          <b-form-invalid-feedback id="ingr-feedback">
            재료를 선택해주세요.
          </b-form-invalid-feedback>
        </b-col>
        <b-col cols="5">
          <b-form-input type="text" v-model="item.quantity" placeholder="무게 / 수량"></b-form-input>
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
  data() {
    return {
      ingrValid: null
    }
  },
  created() {
    this.loadIngredients();
  },
  computed: {
    ...mapState('editor', ['ingrQuery']),
  },
  methods: {
    ...mapMutations('editor', ['addIngredient', 'deleteIngredient', 'SET_UPDATETF']),
    ...mapActions('editor', ['loadIngredients']),
    autoComplete(index) {
      var currVal = this.ingredients[index].name;
      if(!currVal) {
        this.ingredients[index].valid = null;
        return;
      }
      var id = 0;
      var valid = false;
      for (; id < this.ingrQuery.length; id++) {
        if(this.ingrQuery[id].includes(currVal)) {
          valid = true;
          break;
        }
      }
      if(valid) {
        this.ingredients[index].name = this.ingrQuery[id];
        this.ingredients[index].valid = null;
      }
      else {
        this.ingredients[index].name = "";
        this.ingredients[index].valid = false;
      }
    }
  },
  watch: {
    ingredients: {
      deep: true,
      handler() {
        this.SET_UPDATETF(true)
      }
    }
  }
}
</script>

<style>
</style>