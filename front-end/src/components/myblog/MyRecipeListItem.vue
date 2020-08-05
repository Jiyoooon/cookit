<template>
  <b-col lg="4">
      <b-card
    :title="recipe.title"
    :img-src="recipe.main_image"
    img-alt="Image"
    img-top
    tag="article"
    style="max-width: 20rem;"
    class="mb-2"
  >
    <b-card-text>
      {{ recipe.description }}
    </b-card-text>

  </b-card>
  </b-col>
</template>

<script>
export default {
    name: 'MyRecipeListItem',
    props: {
        recipe: Object,
    },
}
</script>

<style>

</style>