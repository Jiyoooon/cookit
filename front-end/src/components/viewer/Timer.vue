<template >
    <div>
        <div class="timer">
            <div class="timer--clock">
                <div class="minutes-group clock-display-grp">
                    <div class="first number-grp">
                        <div class="number-grp-wrp">
                        <div class="num num-0"><p>0</p></div>
                        <div class="num num-1"><p>1</p></div>
                        <div class="num num-2"><p>2</p></div>
                        <div class="num num-3"><p>3</p></div>
                        <div class="num num-4"><p>4</p></div>
                        <div class="num num-5"><p>5</p></div>
                        <div class="num num-6"><p>6</p></div>
                        <div class="num num-7"><p>7</p></div>
                        <div class="num num-8"><p>8</p></div>
                        <div class="num num-9"><p>9</p></div>
                        </div>
                    </div>
                    <div class="second number-grp">
                        <div class="number-grp-wrp">
                        <div class="num num-0"><p>0</p></div>
                        <div class="num num-1"><p>1</p></div>
                        <div class="num num-2"><p>2</p></div>
                        <div class="num num-3"><p>3</p></div>
                        <div class="num num-4"><p>4</p></div>
                        <div class="num num-5"><p>5</p></div>
                        <div class="num num-6"><p>6</p></div>
                        <div class="num num-7"><p>7</p></div>
                        <div class="num num-8"><p>8</p></div>
                        <div class="num num-9"><p>9</p></div>
                        </div>
                    </div>
                </div>
                <div class="clock-separator"><p>:</p></div>
                <div class="seconds-group clock-display-grp">
                    <div class="first number-grp">
                        <div class="number-grp-wrp">
                        <div class="num num-0"><p>0</p></div>
                        <div class="num num-1"><p>1</p></div>
                        <div class="num num-2"><p>2</p></div>
                        <div class="num num-3"><p>3</p></div>
                        <div class="num num-4"><p>4</p></div>
                        <div class="num num-5"><p>5</p></div>
                        <div class="num num-6"><p>6</p></div>
                        <div class="num num-7"><p>7</p></div>
                        <div class="num num-8"><p>8</p></div>
                        <div class="num num-9"><p>9</p></div>
                        </div>
                    </div>
                    <div class="second number-grp">
                        <div class="number-grp-wrp">
                        <div class="num num-0"><p>0</p></div>
                        <div class="num num-1"><p>1</p></div>
                        <div class="num num-2"><p>2</p></div>
                        <div class="num num-3"><p>3</p></div>
                        <div class="num num-4"><p>4</p></div>
                        <div class="num num-5"><p>5</p></div>
                        <div class="num num-6"><p>6</p></div>
                        <div class="num num-7"><p>7</p></div>
                        <div class="num num-8"><p>8</p></div>
                        <div class="num num-9"><p>9</p></div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <div class="reload">
                <span><strong> END</strong></span>
            </div>
    </div>
</template>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/2.0.2/TweenMax.min.js"></script>
<script>
import { TweenMax, TweenLite, Expo} from "gsap/all"
import $ from "jquery"
TweenLite.defaultEase = Expo.easeOut;
export default {
    name:'Timer',
    data(){
        return {
            cssFlag:true, // 타이머 종료시 단 한번만 css적용시키기 위함
            state:true,
            self : this,
            reloadBtn : $(".reload"),
            timerEl : $(".timer"),
            minutesGroupEl : null,
            secondsGroupEl : null,
            minutesGroup : {
                firstNum: null,
                secondNum: null
            },
            secondsGroup : {
                firstNum: null,
                secondNum: null
            },

            time : {
                min: 0,
                sec: 0,
            },
            timeNumbers:0,
        }
    },
    props:{
        t : String,
    },
    methods:{
        updateTimer() {

            var timestr;
            var date = new Date();

            date.setHours(0);
            date.setMinutes(this.time.min);
            date.setSeconds(this.time.sec);
            
            var newDate = new Date(date.valueOf() - 1000);
            var temp = newDate.toTimeString().split(" ");
            var tempsplit = temp[0].split(':');

            this.time.min = tempsplit[1];
            this.time.sec = tempsplit[2];

            timestr = this.time.min + this.time.sec;
            this.timeNumbers = timestr.split('');
            this.updateTimerDisplay(this.timeNumbers);
            if(timestr === '0000')
                this.countdownFinished();

            if(timestr != '0000')
                setTimeout(this.updateTimer, 1000);
        },
        updateTimerDisplay(arr) {
            this.animateNum(this.minutesGroup.firstNum, arr[0]);
            this.animateNum(this.minutesGroup.secondNum, arr[1]);
            this.animateNum(this.secondsGroup.firstNum, arr[2]);
            this.animateNum(this.secondsGroup.secondNum, arr[3]);
        },

        animateNum (group, arrayValue) {
            TweenMax.killTweensOf(group.find('.number-grp-wrp'));
            TweenMax.to(group.find('.number-grp-wrp'), 1, {
                y: - group.find('.num-' + arrayValue)[0].offsetTop
            });

        },
        countdownFinished() {
            //console.log("종료")
            // 카운트다운이 종료되면 실행되는 함수
            // css변화를 여기에 주면됨
            if(this.state == true){
                setTimeout(() => {
                    TweenMax.set(this.reloadBtn, { scale: 0.8, display: 'block' });
                    TweenMax.to(this.timerEl, 1, { opacity: 0.2 });
                    TweenMax.to(this.reloadBtn, 0.5, { scale: 1, opacity: 1 }); 
                    var audio = new Audio(require('@/assets/alarmsound.mp3')).play();
                }, 1000)
            }
        },
        initTimer(){
            this.reloadBtn = $(".reload")
            this.timerEl = $('.timer')
            this.minutesGroupEl = $('.timer').find('.minutes-group')
            this.secondsGroupEl = $('.timer').find('.seconds-group')
            this.minutesGroup.firstNum = $('.timer').find('.minutes-group').find('.first')
            
            this.minutesGroup.secondNum = $('.timer').find('.minutes-group').find('.second')

            this.secondsGroup.firstNum = $('.timer').find('.seconds-group').find('.first')
            this.secondsGroup.secondNum = $('.timer').find('.seconds-group').find('.second')

            this.time.min =this.t.split(':')[0]
            this.time.sec =this.t.split(':')[1]
            setTimeout(this.updateTimer, 1000)
        },
        blinkTimer(state) {
            var item = $('.timer--clock .clock-display-grp .number-grp .number-grp-wrp .num p')
            var item2 = $('.timer--clock .clock-separator')

            if(state == 2){// n초 이하일때
                var inter = setInterval(() => {
                    item.css('color', 'white')
                    item2.css('color', 'white')
                    setTimeout(() => {
                        item.css('color', 'red')
                        item2.css('color', 'red')
                    }, 500)
                    if(this.time.sec == 2){
                        clearInterval(inter)
                    }
                }, 1000)
            }
            else if(state == 1){// 타이머 종료시
                item.css('color','white')
                item2.css('color','white')
                
            }
        },
    },
    watch: {
        time:{
            deep:true,
            handler(){
                if(this.time.min == 0 && this.time.sec < 6 && this.cssFlag){
                    this.cssFlag = false
                    this.blinkTimer(2)
                }
                else if(this.time.sec == 0){
                    this.blinkTimer(1)
                }
            }
        },
    },
    mounted() { 
        this.initTimer(this.t); 
    },
    beforeDestroy() {
        this.state = false
        this.time.min="00"
        this.time.sec="01"
        this.countdownFinished()
    },
}
</script>

<style scoped>
    .timer {
        width: 550px;
        height: 248px;
        display: block;
        position: absolute;
        top: 50%;
        left: 50%;
        margin-left: -275px;
        margin-top: -124px;
        color: black;
    }
    .timer * {
        cursor: default;
    }
    .timer--clock {
        width: 100%;
        position: relative;
        padding-left: 6px;
        margin-top: 22px;
        overflow: hidden;
    }
    .timer--clock .clock-display-grp {
        width: 100%;
        position: relative;
    }
    .timer--clock .clock-display-grp .number-grp {
        width: auto;
        display: block;
        height: 156px;
        float: left;
        overflow: hidden;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp {
        width: 100%;
        position: relative;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp .num {
        width: 100%;
        position: relative;
        height: 156px;
    }
    .timer--clock .clock-display-grp .number-grp .number-grp-wrp .num p {
        width: auto;
        display: table;
        font-size: 205px;
        line-height: 160px;
        font-weight: bold;
        color: white;
    }
    .timer--clock .clock-separator {
        width: auto;
        float: left;
        display: block;
        height: 156px;
        color: white;
    }
    .timer--clock .clock-separator p {
        width: auto;
        display: table;
        font-size: 205px;
        line-height: 130px;
        font-weight: bold;
    }

    @media (max-width: 496px) {
      .timer {
        margin-left: -10.6em;
        margin-top: -10em;
        height: 180px;
      }
      .timer--clock .clock-display-grp .number-grp .number-grp-wrp .num p {
        font-size: 8em;
        line-height: 120px;
      }
      .timer--clock .clock-separator p {
        font-size: 8em;
        line-height: 110px;
      }
      .reload {
        position: absolute;
        top: 0 !important;
        left: 50%;
        font-size: 3em;
      }
    }

    .timer h4 {
        width: 100%;
        font-size: 10px;
        letter-spacing: 6px;
        text-align: center;
        padding-top: 25px;
        float: left;
    }
    .reload {
        text-align: center;
        width: 125px;
        height: 14px;
        position: absolute;
        top: 10vh;
        left: 50%;
        margin-left: -62.5px;
        opacity: 0;
        display: none;
        z-index: 9999;
        color: black;
    }
    .reload:hover svg path {
        fill: #666;
    }
    .reload:hover p {
        color: #666;
    }
    .reload svg {
        width: 12px;
        height: 12px;
        float: left;
        margin-right: 10px;
    }
    .reload p {
        color: #2b2b2b;
        font-size: 12px;
        float: left;
        line-height: 11px;
    }

    .testbtn{
        z-index: 0;
    }
</style>