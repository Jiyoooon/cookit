<template>
  <b-container style="margin-bottom: 10px;">
    <!-- Step + 숫자 -->
    <b-row>
      <b-col class="text-left mr-3 ml-3">
        <h4 style="font-weight:650; color:#53AAA1">
          <font-awesome-icon :icon="['fas', 'caret-right']" style="color: #53AAA1; margin-right: 0.8em;"/>
           Step. {{ cookingstep.steps }} 
          <font-awesome-icon :icon="['fas', 'caret-left']" style="color: #53AAA1; margin-left: 0.8em;"/>
        </h4>
      </b-col>
    </b-row>

    <b-row>
      <b-col sm="7">
        <timeDescription :description='cookingstep.description' :time='cookingstep.time' :number='cookingstep.steps'/>

      </b-col>
      <b-col v-if="cookingstep.step_image" sm="5">
        <b-img :src="cookingstep.step_image" class="rounded-lg" style="max-width: 100%; max-height:380px;"/>
      </b-col>
    </b-row>
    <b-row v-if="cookingstep.tip">
      <b-col class="text-left">
        💡 {{ cookingstep.tip }}
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import timeDescription from '@/components/viewer/TimeDescription.vue'
export default {
    name: 'CookingStepItem,',
    props: {
        cookingstep: Object,
    },
    components:{
      timeDescription,
    }

}
</script>

<style>
</style>