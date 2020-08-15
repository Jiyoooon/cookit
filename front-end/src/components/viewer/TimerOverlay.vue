<template>
<div>
  <v-overlay :value="this.overlay1" >
            <div>
                <v-btn
                    icon
                    id="button2"
                    @click="clickX"
                >
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <timervue :t="timervalue1" :key="NaN"/>
            </div>
        </v-overlay>
</div>
</template>

<script>

import timervue from '@/components/viewer/Timer.vue'
export default {
    name: 'timerOverlay',
    data(){
        return{
            timervalue: "44:44",
            overlay:false,
        }
    },
    props:{
        timervalue1:String,
        overlay1:Boolean,
    },
    components:{
        timervue,
    },
    methods: {
        setTimerValue(t){//설정하고 싶은 시간을 mm:ss로 넣어준다.
            this.timervalue = t
        },
        clickX(){
            this.overlay1 = false
            this.$emit('set-timer-overlay',false)
            //this.$emit('delete-source',this.Sourceinfo.ingredientdata.name)
        }
    },
}
</script>

<style>
    #button2 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        top: 5vh;
        cursor: pointer;
    }
</style>