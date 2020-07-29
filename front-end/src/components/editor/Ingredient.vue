<template>
  <div>
    <b-container fluid="sm">
      <b-row v-for="(item, index) in items" :key="index">
        <b-col sm="5">
          <b-form-input type="text" v-model="item.name" required></b-form-input>
        </b-col>
        <b-col sm="5">
          <b-form-input type="text" v-model="item.quantity" required></b-form-input>
        </b-col>
        <b-col>
          <b-button id="deleteBtn" @click="deleteItem(index)">X</b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="3"></b-col>
        <b-col sm="4">
          <b-button variant="primary" id="addBtn" @click="addItem" block>추가</b-button>
        </b-col>
        <b-col sm="5"></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'Ingredients',
  data() {
    return {
      items: [ { name: "", quantity: ""}, { name: "", quantity: "" } ],
    }
  },
  methods: {
    addItem() {
      this.items.push({ name: "", quantity: "" });
    },
    deleteItem(index) {
      this.items.splice(index, 1)
    }
  }
}
</script>

<style>

</style>