<template>
  <b-container>
  <!-- <div style="border:1px solid black"> -->
    <b-row>
            <b-col cols='2' style="margin:auto">
        <div rounded="circle" class="stepcircle rounded-circle text-center lighten-2 align-center justify-center ma-3">
          <span style="margin:auto;font-size:3rem">{{  cookingstep.steps }}</span>
        </div>
      </b-col>

      <b-col cols='7' style="margin:auto">
        <img :src="cookingstep.step_image" alt="" class="stepimage rounded-lg">
      </b-col>

      <b-col cols='3' style="margin:auto;font-size:1.5rem">
        <div class="description">
      <!-- {{ cookingstep.time }} -->
        {{ cookingstep.description }}
        </div>
      </b-col>
    </b-row>
  </b-container>
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
  .triangle-border {
  position:relative;
  padding:15px;
  margin:1em 0 3em;
  border:5px solid #5a8f00;
  color:#333;
  background:#fff;
  /* css3 */
  -webkit-border-radius:10px;
  -moz-border-radius:10px;
  border-radius:10px;
}
.triangle-border.left {
  margin-left:30px;
}
</style>