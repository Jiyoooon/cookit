<template>
<div>
  <v-overlay :value="this.overlayState" z-index=1070>
            <div>
                <v-btn
                    icon
                    id="button2"
                    @click="clickX()"
                >
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <timervue :t="timervalue" :key="NaN"/>
            </div>
        </v-overlay>
</div>
</template>

<script>

import timervue from '@/components/viewer/Timer.vue'
import { mapState, mapMutations } from 'vuex'
export default {
    name: 'timerOverlay',
    data(){
        return{
            timervalue:'',
            overlayState:false,
        }
    },
    components:{
        timervue,
    },
    methods: {
        setTimerValue(t){//설정하고 싶은 시간을 mm:ss로 넣어준다.
            this.timervalue = t
        },
        setoverlay(){
            this.overlayState = this.overlay
        },
        clickX(){
            this.SET_TIMER_INIT()
         },
         ...mapMutations('recipes',['SET_OVERLAY','SET_TIMER_INIT'])
    },
    watch: {
        overlay:{
            handler(){
                console.log("overlay핸들러")
                this.setoverlay()
            },
            deep:true,
        },
        timestring:{
            handler(){
                console.log("timestring핸들러")
                if(this.timestring!=''){
                this.setTimerValue(this.timestring)
                this.SET_OVERLAY(true)}
            }
        },
    },
    computed: {
        ...mapState('recipes',['overlay','timestring'])
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