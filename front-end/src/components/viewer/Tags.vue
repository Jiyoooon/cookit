<template>
  <b-container fluid>
    <b-row class="m-3"><b-col></b-col></b-row>
    <b-row class="m-3"><b-col></b-col></b-row>
    <b-row align-v="center">
      <h6 style="font-weight:620; padding: 0.5em 1em 0 2em"><i class="fas fa-tags" id="tag-icon"/> 태그</h6>
      <v-chip v-for="(tag, index) in selectedRecipe.tag" :key="index" class="ma-1" :label="true" small>#{{tag}}</v-chip>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: 'Tags',
  props: {
    selectedRecipe: Object,
  },
  created() {
    console.log(this.selectedRecipe.tag)
  }
}
</script>

<style>
#tag-icon {
  color: #FFDB8A;
  font-size: 0.8em;
  text-shadow: 1px 1px 1px #222,
    1px -1px 1px #222,
    -1px 1px 1px #222,
    -1px -1px 1px #222;
}
</style>