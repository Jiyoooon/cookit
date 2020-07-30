<template>
  <div>
      <span style="color:white">{{ sourcename }}</span><b-icon  class="delete" @click="clickX" icon="x-circle-fill"></b-icon>
  </div>
</template>

<script>
export default {
    name:'SelectedSource',
    props:{
        sourcename:String,
    },
    data(){
        return {

        }
    },
    methods:{
        clickX(){
            console.log(this.sourcename)
            alert(this.sourcename)
        },
    }
}
</script>

<style>
    .delete{
        cursor:pointer;
        color:#A0A0A0;
        align-self: center;
    }
</style>