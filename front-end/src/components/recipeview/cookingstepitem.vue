<template>
  <div>
    <b-row>
      <b-col cols='7'>
        <img :src="cookingstep.step_image" alt="" class="stepimage rounded-lg">
      </b-col>
      <b-col cols='2' style="margin:auto">
        <div rounded="circle" class="stepcircle rounded-circle text-center lighten-2 align-center justify-center ma-3">
          <span style="margin:auto;font-size:3rem">{{  cookingstep.steps }}</span>
        </div>
      </b-col>
      <b-col cols='3' style="margin:auto;font-size:1.5rem">
        <div class="description">
      <!-- {{ cookingstep.time }} -->
        {{ cookingstep.description }}
        </div>
      </b-col>
    </b-row>
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
  .stepcircle{
    background-color:grey;
    margin:auto;
    width: 64px;
    height: 64px;
    line-height: 1.1;
  }
  .description{
    margin: auto;
    align-content: center;
    align-self: center;
    line-height: 1.5;
  }
  .stepimage{
    display: block;
    max-width: 70%;
    max-height: 100%;
  }
</style>