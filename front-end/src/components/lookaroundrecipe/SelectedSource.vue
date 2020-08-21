<template>
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
  </div>
</template>

<script>
export default {
    name:'SelectedSource',
    props:{
        Sourceinfo: Object,
    },
    data(){
        return {
            isdelete: false,
        }
    },
    methods:{
        clickX(){
            this.isdelete=true
            this.$emit('delete-source',this.Sourceinfo.ingredientdata.name)
        },
    }
}
</script>

<style>
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
</style>