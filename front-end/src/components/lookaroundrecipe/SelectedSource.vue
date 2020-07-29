<template>
    <div class="chipG">
        <div class="chip rounded-pill selected" v-if="!isdelete && Sourceinfo.state">
            <div style="display:inline; margin:10px;">{{ Sourceinfo.name }}<b-icon style="display:inline" class="delete" @click="clickX" icon="x-circle-fill"></b-icon></div>
        </div>
        <div class="chip rounded-pill excepted" v-if="!isdelete && !Sourceinfo.state">
            <span>{{ Sourceinfo.name }}<b-icon  class="delete" @click="clickX" icon="x-circle-fill"></b-icon></span>
        </div>
    </div>
</template>

<script>
export default {
    name:'SelectedSource',
    props:{
        Sourceinfo:Object,
    },
    data(){
        return {
            isdelete:false,
        }
    },
    methods:{
        clickX(){
            this.isdelete=true
            this.$emit('delete-source',this.Sourceinfo.name)
        },
    }
}
</script>

<style>
    .chipG{
        margin: 0.1%;
    }
    .chip{
        color:white;
        display: inline;
    }
    .selected{
        background-color:#1E88E5;
    }
    .excepted{
        background-color:#FF3D00;
    }
    .delete{
        cursor:pointer;
        color:#A0A0A0;
        align-self: center;
    }
</style>