<template>
  <div>
      <b-col lg-3>
        <img :src="cookingstep.step_image" alt="">
      </b-col>
      <b-col lg-9>
        <span>{{  cookingstep.steps }}. </span>
      <!-- {{ cookingstep.time }} -->
        {{ cookingstep.description }}
      </b-col>

  </div>
</template>

<script>
export default {
    name: 'cookingstepitem,',
    props: {
        cookingstep: Object,
    },

}
</script>

<style>

</style>