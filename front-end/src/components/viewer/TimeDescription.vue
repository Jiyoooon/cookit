<template>
  <div :id="number">
      <timeroverlay :timervalue= timestring :overlay = overlay @set-timer-overlay="setTimerOverlay"/>
  </div>
</template>

<script>
import $ from "jquery"
import timeroverlay from '@/components/viewer/TimerOverlay.vue'

export default {
    data(){
        return{ 
            formattedDescription:'',
            timestring:'',
            overlay:false,
        }
    },
    props:{
        description:String,
        time:Array,// 시작위치, 끝 위치, 시간
        number: String,
    },
    components:{
        timeroverlay, 
    },
    methods: {
        setString(){
            let start = 0
            let end = this.description.length
            if(this.time.length){
                for(let i = 0; i < this.time.length; i++){
                    this.formattedDescription += this.description.substring(start,this.time[i][0]) + "<span class='timertext' id = '" + this.number + "-" + i + "' >"
                    this.formattedDescription += this.description.substring(this.time[i][0],this.time[i][1]) + "</span>"
                    start = this.time[i][1]
                }
                this.formattedDescription += this.description.substring(start,end)
            }
            else
                this.formattedDescription = this.description
            $('#'+this.number).append(this.formattedDescription)
            for(let i = 0 ; i < this.time.length;i++){
                let self = this
                $('#'+this.number+'-'+i).on('click', function(){
                    self.transTime(self.time[i][2])
                })
            }
        },
        transTime(t){
            let mm,ss
            mm = parseInt(t / 60)
            ss = t%60+1
            this.timestring = mm+":"+ss
            this.setTimerOverlay(true)
        },
        setTimerOverlay(s){
            this.overlay = s
            
        },
    },
    mounted() {
        this.setString();
    },

}
</script>

<style>
.timertext{
    font-weight: bold;
    color: blue;
    cursor:pointer;
}

</style>