<template>
<div>
  <v-overlay :value="this.overlay" >
            <div>
                <v-btn
                    icon
                    id="button2"
                    @click="overlay = !overlay"
                >
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <timervue :t="timervalue" :key="NaN"/>
            </div>
        </v-overlay>

        <div id = "button1">
            <v-btn
            @click="setTimerValue('00:04'); (overlay = !overlay);"
            >
            3초
            </v-btn>
            <v-btn
            @click="setTimerValue('00:11'); (overlay = !overlay);"
            >
            10초
            </v-btn>
        </div>
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
    components:{
        timervue,
    },
    methods: {
        setTimerValue(t){//설정하고 싶은 시간을 mm:ss로 넣어준다.
            this.timervalue = t
        },
    },
}
</script>

<style>
    /* 테스트용 버튼 css */
    button#button1 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        bottom: 40vh;
        cursor: pointer;
        z-index: 1;
    }
    #button2 {
        font-size: 4rem;
        box-sizing: content-box;
        position: fixed;
        right: 5vw;
        top: 5vh;
        cursor: pointer;
    }
</style>