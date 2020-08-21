<template>
  <div :id="number">

  </div>
</template>

<script>
import $ from "jquery"
import { mapState,mapMutations, mapActions } from 'vuex'
export default {
    data(){
        return{ 
            formattedDescription:'',
            timeString:'',
            overlayState:false,
        }
    },
    props:{
        description:String,
        time:Array,// 시작위치, 끝 위치, 시간
        number: String,
    },
    methods: {
        setString(){
            let start = 0
            let end = this.description.length
            if(this.time.length){
                this.formattedDescription=''
                for(let i = 0; i < this.time.length; i++){
                    this.formattedDescription += this.description.substring(start,this.time[i][0]) + "<span class='timertext' id = '" + this.number + "-" + i + "' >"
                    this.formattedDescription += this.description.substring(this.time[i][0],this.time[i][1]) + "</span>"
                    start = this.time[i][1]
                }
                this.formattedDescription += this.description.substring(start,end)
            }
            else
                this.formattedDescription = this.description
            $('#'+this.number).html(this.formattedDescription)
            for(let i = 0 ; i < this.time.length;i++){
                let self = this
                $('#'+this.number+'-'+i).on('click', function(){
                    self.startTimer(self.time[i][2])
                })
            }
        },
        setOverlayState(s){
            this.overlayState = s
        },
         ...mapMutations('recipes',['SET_OVERLAY']),
         ...mapActions('recipes',['startTimer'])
    },
    watch:{
        description:{
            handler(){
                this.setString()
            }
        },
        timestring:{
            handler(){
                //console.log("timestring")
                this.setOverlayState(true)
            }
        },
        overlayState:{
            handler(){
                this.SET_OVERLAY(this.overlayState)
            }
        }
    },
    computed: {
        ...mapState('recipes',['timestring'])
    },
    updated() {
        this.setString();
        this.setOverlayState(false)
    },
    mounted() {
        this.setString();
        this.setOverlayState(false)
    },

}
</script>

<style>
.timertext{
    font-weight: bold;
    color: #FFC79A;
    cursor: pointer;
}
</style>