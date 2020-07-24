<template>
  <div class="col-lg-6">
      <b-card
    :title="recipe.title"
    img-src="https://picsum.photos/600/300/?image=25"
    img-alt="Image"
    img-top
    tag="article"
    style="max-width: 20rem;"
    class="mb-2"
  >
    <b-card-text>
      {{ recipe.content }}
    </b-card-text>

  </b-card>
  </div>
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