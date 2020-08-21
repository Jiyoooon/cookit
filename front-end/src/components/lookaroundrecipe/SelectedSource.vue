<template>
<<<<<<< HEAD
  <div>
      <span style="color:white">{{ sourcename }}</span><b-icon  class="delete" @click="clickX" icon="x-circle-fill"></b-icon>
=======
  <div class="chip-comp">
    <span v-if="!isdelete && Sourceinfo.state">
      <b-button pill variant="primary" size="sm"> {{ Sourceinfo.ingredientdata.name }} 
        <b-icon class="delete" @click="clickX" icon="x"></b-icon>
      </b-button>
    </span>
    <span v-if="!isdelete && !Sourceinfo.state">
      <b-button pill variant="danger" size="sm"> {{ Sourceinfo.ingredientdata.name }} 
        <b-icon class="delete" @click="clickX" icon="x"></b-icon>
      </b-button>
    </span>
>>>>>>> develop
  </div>
</template>

<script>
export default {
    name:'SelectedSource',
    props:{
<<<<<<< HEAD
        sourcename:String,
    },
    data(){
        return {

=======
        Sourceinfo: Object,
    },
    data(){
        return {
            isdelete: false,
>>>>>>> develop
        }
    },
    methods:{
        clickX(){
<<<<<<< HEAD
            console.log(this.sourcename)
            alert(this.sourcename)
=======
            this.isdelete=true
            this.$emit('delete-source',this.Sourceinfo.ingredientdata.name)
>>>>>>> develop
        },
    }
}
</script>

<style>
<<<<<<< HEAD
    .delete{
        cursor:pointer;
        color:#A0A0A0;
        align-self: center;
    }
=======
.chip-comp {
  color: white;
  margin: 0.2em;
}

.delete{
  display: inline;
  cursor:pointer;
  color:white;
  align-self: center;
}
>>>>>>> develop
</style>